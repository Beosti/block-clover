package com.yuanno.blockclover.events;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import com.yuanno.blockclover.spells.fire.FireballSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ServerChatEvent {

    @SubscribeEvent
    public static void onServerChat(net.minecraftforge.event.ServerChatEvent event)
    {
        if (event.getMessage().equals("fireball"))
        {
            PlayerEntity player = event.getPlayer();
            ISpellData spellData = SpellDataCapability.get(player);
            spellData.addUnlockedSpell(FireballSpell.INSTANCE);
            PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
        }
        if (event.getMessage().equals("grimoire"))
        {
            PlayerEntity player = event.getPlayer();
            IEntityStats entityStats = EntityStatsCapability.get(player);
            entityStats.getMagicData().setHasGrimoire(true);
            PacketHandler.sendTo(new SSyncEntityStatsDataPacket(player.getId(), entityStats), player);
        }
    }
}
