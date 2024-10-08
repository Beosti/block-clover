package com.yuanno.blockclover.entity.earth;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.client.renderers.AbilityProjectileRenderer;
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
public class EarthProjectiles {

    public static final RegistryObject<EntityType<EarthshotProjectile>> EARTHSHOT = Beapi.registerEntityType("Earthshot",
            () -> Beapi.createEntityType(EarthshotProjectile::new)
                    .sized(1f,1f)
                    .build(Main.MODID + ":earthshot"));

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerEntityRenderers(FMLClientSetupEvent event)
    {
        RenderingRegistry.registerEntityRenderingHandler(EARTHSHOT.get(), new AbilityProjectileRenderer.Factory(new CubeModel())
                .setScale(1, 1, 1)
                .setColor("#9b7653"));
    }
}
