package com.yuanno.blockclover.events;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import com.yuanno.blockclover.spells.FireBallSpell;
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
            spellData.addUnlockedSpell(FireBallSpell.INSTANCE);
            System.out.println(spellData.getUnlockedSpells());
            System.out.println("GAVE SPELL");
            PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
        }
    }
}
