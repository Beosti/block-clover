package com.yuanno.blockclover.events.level;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import com.yuanno.blockclover.spells.FiredupSpell;
import com.yuanno.blockclover.spells.FirewaveSpell;
import com.yuanno.blockclover.spells.FirebatSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class LevelEvents {

    @SubscribeEvent
    public static void onLevelUp(LevelUpEvent event)
    {
        PlayerEntity player = event.getPlayer();
        System.out.println(player.getDisplayName() + " leveled up!");
        ArrayList<Spell> spells = new ArrayList<>();
        spells.add(FirebatSpell.INSTANCE);
        spells.add(FirewaveSpell.INSTANCE);
        spells.add(FiredupSpell.INSTANCE);
        PacketHandler.sendTo(new SOpenSpellChoiceScreenPacket(spells), player);
    }
}
