package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.screens.menu.PlayerOverviewScreen;
import com.yuanno.blockclover.client.screens.menu.PlayerSpellScreen;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncEntityStatsPacket;
import com.yuanno.blockclover.networking.client.CSyncKeyPressedPacket;
import com.yuanno.blockclover.networking.client.CSyncSpellDataPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Y;

/**
 * Class that handles all the keys pressed;
 * Key pressed (-> event) -> something happens
 *
 * inspired by mineminenomi @Wynd
 */
@Mod.EventBusSubscriber(modid = Main.MODID, value = Dist.CLIENT)
public class ModKeyBinds {
    public static KeyBinding infocard, spellscreen, combatMode;
    public static KeyBinding combatKey1, combatKey2, combatKey3, combatKey4, combatKey5, combatKey6, combatKey7, combatKey8, combatKey9;
    private static KeyBinding[] keyBindsCombatbar;
    public static void init()
    {
        infocard = new KeyBinding("keys.blockclover.info_card", GLFW_KEY_P, "category.blockclover.gui");
        ClientRegistry.registerKeyBinding(infocard);
        spellscreen = new KeyBinding("keys.blockclover.spell_screen", GLFW_KEY_Y, "category.blockclover.gui");
        ClientRegistry.registerKeyBinding(spellscreen);
        combatMode = new KeyBinding("keys.blockclover.combat_mode", GLFW.GLFW_KEY_R, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatMode);
        combatKey1 = new KeyBinding("keys.blockclover.combat_key_1", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey1);
        combatKey2 = new KeyBinding("keys.blockclover.combat_key_2", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey2);
        combatKey3 = new KeyBinding("keys.blockclover.combat_key_3", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey3);
        combatKey4 = new KeyBinding("keys.blockclover.combat_key_4", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey4);
        combatKey5 = new KeyBinding("keys.blockclover.combat_key_5", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey5);
        combatKey6 = new KeyBinding("keys.blockclover.combat_key_6", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey6);
        combatKey7 = new KeyBinding("keys.blockclover.combat_key_7", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey7);
        combatKey8 = new KeyBinding("keys.blockclover.combat_key_8", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey8);
        combatKey9 = new KeyBinding("keys.blockclover.combat_key_9", GLFW.GLFW_KEY_1, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatKey9);

        keyBindsCombatbar = new KeyBinding[]
                {
                        combatKey1, combatKey2, combatKey3, combatKey4, combatKey5, combatKey6, combatKey7, combatKey8, combatKey9
                };
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null)
            return;

        checkKeyBindings(player);
    }

    private static void checkKeyBindings(PlayerEntity player)
    {
        if (infocard.isDown())
        {
            if (Minecraft.getInstance().screen != null)
                return;

            PlayerOverviewScreen.open();
        }
        if (spellscreen.isDown())
        {
            if (Minecraft.getInstance().screen != null)
                return;
            PlayerSpellScreen.open();
        }
        if (combatMode.isDown())
        {
            if (Minecraft.getInstance().screen != null)
                return;

            IEntityStats entityStats = EntityStatsCapability.get(player);
            if (entityStats.getCombatData().getCombatMode())
                entityStats.getCombatData().setCombatMode(false);
            else
                entityStats.getCombatData().setCombatMode(true);
            PacketHandler.sendToServer(new CSyncEntityStatsPacket(entityStats));
        }
        int j = keyBindsCombatbar.length;

        for (int i = 0; i < j; i++)
        {
            if (keyBindsCombatbar[i].isDown())
            {
                ISpellData spellData = SpellDataCapability.get(player);
                Spell spellUsed = spellData.getEquippedSpells().get(i);
                if (spellUsed != null)
                {
                    if (spellUsed.getState().equals(Spell.STATE.READY))
                    {
                        PacketHandler.sendToServer(new CSyncKeyPressedPacket(i));
                        spellUsed.setState(Spell.STATE.COOLDOWN);
                        spellUsed.setCurrentCooldown(spellUsed.getMaxCooldown() * 20);
                        spellUsed.alterSpellExperience(1);
                        spellData.setPreviousSpellUsed(spellUsed);
                        if (spellUsed.getSpellMaxExperience() <= spellUsed.getSpellExperience())
                        {
                            spellUsed.alterSpellLevel(1);
                            spellUsed.setSpellExperience(0);
                            spellUsed.alterSpellMaxExperience(10);
                        }
                        PacketHandler.sendToServer(new CSyncSpellDataPacket(spellData));
                    }
                }
                IEntityStats entityStats = EntityStatsCapability.get(player);
                if (!entityStats.getCombatData().getCombatMode()) {
                    player.inventory.selected = i;
                }
            }
        }
    }
}
