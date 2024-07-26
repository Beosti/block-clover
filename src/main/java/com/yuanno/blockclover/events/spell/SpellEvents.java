package com.yuanno.blockclover.events.spell;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.UtilSpell;
import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncSpellDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SpellEvents {

    @SubscribeEvent
    public static void onSpellUse(SpellUseEvent event)
    {
        if (!(event.getSpellUser() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSpellUser();
        ISpellData spellData = SpellDataCapability.get(player);
        Spell usedSpell = event.getSpell();

        for (int i = 0; i < usedSpell.getSpellComponents().size(); i++)
        {
            if (usedSpell.getSpellComponents().get(i) instanceof ProjectileSpellComponent)
                ((ProjectileSpellComponent) usedSpell.getSpellComponents().get(i)).shootProjectileSpellComponent(player, usedSpell);
            if (spellData.getPreviousSpellUsed() != null &&  usedSpell.getSpellComponents().get(i) instanceof ComboSpellComponent && spellData.getPreviousSpellUsed().equals(((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).getSpellToCombo()))
                ((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).combo.comboDoing(player);
            if (usedSpell.getSpellComponents().get(i) instanceof ContinuousSpellComponent && usedSpell.getState().equals(Spell.STATE.CONTINUOUS))
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).startContinuitySpell.startContinuity(player);
            if (usedSpell.getSpellComponents().get(i) instanceof ContinuousSpellComponent && usedSpell.getState().equals(Spell.STATE.COOLDOWN)) {
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).endContinuitySpell.endContinuity(player);
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).setCurrentDuration(((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).getMaxDuration());
            }
        }
    }

    @SubscribeEvent
    public static void onCurrentContinuousSpell(TickEvent.PlayerTickEvent event)
    {
        if (!(event.player.tickCount % 20 == 0))
            return;
        ISpellData spellData = SpellDataCapability.get(event.player);
        for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
        {
            if (spellData.getEquippedSpells().get(i) == null)
                continue;
            if (!(spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.CONTINUOUS) || spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.PASSIVE)))
                continue;
            if (UtilSpell.hasComponent(spellData.getEquippedSpells().get(i), ContinuousSpellComponent.class))
            {
                ContinuousSpellComponent continuousSpellComponent = (ContinuousSpellComponent) UtilSpell.getComponent(spellData.getEquippedSpells().get(i), ContinuousSpellComponent.class);
                continuousSpellComponent.duringContinuitySpell.duringContinuity(event.player);
                if (continuousSpellComponent.getCurrentDuration() == -1)
                    continue;
                else if (continuousSpellComponent.getCurrentDuration() > 0)
                    continuousSpellComponent.alterCurrentDuration(-1);
                else if (continuousSpellComponent.getCurrentDuration() == 0)
                {
                    UtilSpell.usedSpell(spellData.getEquippedSpells().get(i));
                    SpellUseEvent spellUseEvent = new SpellUseEvent(event.player, spellData.getEquippedSpells().get(i));
                    MinecraftForge.EVENT_BUS.post(spellUseEvent);
                    PacketHandler.sendTo(new SSyncSpellDataPacket(event.player.getId(), spellData), event.player);
                }
            }
        }
    }
}
