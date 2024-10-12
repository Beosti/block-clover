package com.yuanno.blockclover.client.renderers;

import com.yuanno.blockclover.entity.water.WaterSubstituteEntity;
import com.yuanno.blockclover.models.HumanoidModel;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class WaterSubstituteRenderer extends HumanoidRenderer<WaterSubstituteEntity, BipedModel<WaterSubstituteEntity>> {
    
    public WaterSubstituteRenderer(EntityRendererManager manager)
    {
        super(manager, new HumanoidModel());
    }

    @Override
    public ResourceLocation getTextureLocation(WaterSubstituteEntity entity)
    {
        if(entity.getOwnerUUID() == null) {
            return DefaultPlayerSkin.getDefaultSkin(entity.getUUID());
        }
        PlayerEntity player = entity.level.getPlayerByUUID(entity.getOwnerUUID());
        if(player != null)
            return ((AbstractClientPlayerEntity) player).getSkinTextureLocation();
        else
            return DefaultPlayerSkin.getDefaultSkin(entity.getOwnerUUID());
    }

    public static class Factory implements IRenderFactory<WaterSubstituteEntity>
    {
        public Factory() {}

        @Override
        public EntityRenderer<? super WaterSubstituteEntity> createRenderFor(EntityRendererManager manager)
        {
            return new WaterSubstituteRenderer(manager);
        }
    }
}
