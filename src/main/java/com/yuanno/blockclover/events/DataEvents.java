package com.yuanno.blockclover.events;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.entity.MiscData;
import com.yuanno.blockclover.init.ModValues;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DataEvents {

    /**
     * Data set when joining the world
     * @param event
     */
    @SubscribeEvent
    public static void registerDataEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide)
            return;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        if (entityStats.getMiscData() == null) // when no misc data; sets blank slate
        {
            entityStats.setMiscData(new MiscData()); // called server side -> server side data is updated
            handleMiscData(entityStats.getMiscData());
        }
        PacketHandler.sendTo(new SSyncEntityStatsDataPacket(player.getId(), entityStats), player);
    }

    // handles all the misc data when joining the world for a first time -> race, rank, title
    static void handleMiscData(MiscData miscData)
    {
        Random random = new Random();
        int raceInteger = random.nextInt(10) + 1;
        String race = "";
        if (raceInteger <= 4)
            race = ModValues.HUMAN;
        else if (raceInteger <= 6)
            race = ModValues.ELF;
        else if (raceInteger <= 8)
            race = ModValues.DWARF;
        else
            race = ModValues.HYBRID;
        miscData.setRace(race);
    }
}
