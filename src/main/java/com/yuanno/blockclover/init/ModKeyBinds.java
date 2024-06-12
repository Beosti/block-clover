package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.client.screens.menu.PlayerOverviewScreen;
import com.yuanno.blockclover.client.screens.menu.PlayerSpellScreen;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncEntityStatsPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

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

    public static void init()
    {
        infocard = new KeyBinding("keys.blockclover.info_card", GLFW_KEY_P, "category.blockclover.gui");
        ClientRegistry.registerKeyBinding(infocard);
        spellscreen = new KeyBinding("keys.blockclover.spell_screen", GLFW_KEY_Y, "category.blockclover.gui");
        ClientRegistry.registerKeyBinding(spellscreen);
        combatMode = new KeyBinding("keys.blockclover.combat_mode", GLFW.GLFW_KEY_R, "category.blockclover.combat_mode");
        ClientRegistry.registerKeyBinding(combatMode);
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
    }
}
