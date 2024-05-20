package com.yuanno.blockclover.events;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.util.MiscData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

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
        if (EntityStatsCapability.get(player).getMiscData() == null) // when no misc data; sets blank slate
            EntityStatsCapability.get(player).setMiscData(new MiscData()); // called server side -> server side data is updated
    }
}
