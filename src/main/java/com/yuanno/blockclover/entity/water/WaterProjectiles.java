package com.yuanno.blockclover.entity.water;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.client.renderers.AbilityProjectileRenderer;
import com.yuanno.blockclover.entity.lightning.LightningchargeProjectile;
import com.yuanno.blockclover.models.CubeModel;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class WaterProjectiles {

    public static final RegistryObject<EntityType<WaterballProjectile>> WATERBALL = Beapi.registerEntityType("Waterball",
            () -> Beapi.createEntityType(WaterballProjectile::new)
                    .sized(1f, 1f)
                    .build(Main.MODID + ":waterball"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(WATERBALL.get(), new AbilityProjectileRenderer.Factory(new CubeModel())
                .setScale(1, 1, 1)
                .setColor("#0f5e9c"));
    }
}
