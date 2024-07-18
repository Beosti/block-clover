package com.yuanno.blockclover.events.spell;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class CooldownEvent {

    @SubscribeEvent
    public static void onSpellCooldown(TickEvent.PlayerTickEvent event)
    {
        PlayerEntity player = event.player;
        ISpellData spellData = SpellDataCapability.get(player);
        for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
        {
            Spell currentSpell = spellData.getEquippedSpells().get(i);
            if (currentSpell == null)
                continue;
            if (currentSpell.getState().equals(Spell.STATE.COOLDOWN))
            {
                if (currentSpell.getCurrentCooldown() > 0)
                    currentSpell.alterCurrentCooldown(-1);
                else
                    currentSpell.setState(Spell.STATE.READY);
                PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
            }
        }
    }
}
