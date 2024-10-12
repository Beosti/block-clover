package com.yuanno.blockclover.events;

import com.yuanno.blockclover.entity.water.WaterAttributeEntities;
import com.yuanno.blockclover.entity.water.WaterSubstituteEntity;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event)
    {
        event.put(WaterAttributeEntities.WATER_SUBSTITUTE.get(), WaterSubstituteEntity.setCustomAttributes().build());
    }
}
