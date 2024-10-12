package com.yuanno.blockclover.client.renderers;

import com.google.common.base.Strings;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.client.IDynamicRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.BipedRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.IRenderFactory;

@OnlyIn(Dist.CLIENT)
public class HumanoidRenderer<T extends MobEntity, M extends BipedModel<T>> extends BipedRenderer<T, M>
{
	protected ResourceLocation texture;
	protected float[] scale;

	public HumanoidRenderer(EntityRendererManager manager, M model)
	{
		this(manager, model, null);
	}

	public HumanoidRenderer(EntityRendererManager manager, M model, String tex)
	{
		this(manager, model, new float[] { 1, 1, 1 }, tex);
	}

	public HumanoidRenderer(EntityRendererManager manager, M model, float[] scale, String tex)
	{
		super(manager, model, (float) (0.5F * Math.pow(scale[0], 1.5)));
		this.scale = scale;
		this.texture = new ResourceLocation(Main.MODID, "textures/entities/" + tex + ".png");
		this.addLayer(new BipedArmorLayer<>(this, new BipedModel(0.5F), new BipedModel(1.0F)));
	}

	@Override
	public void scale(T entity, MatrixStack matrixStack, float partialTickTime)
	{
		matrixStack.scale(this.scale[0], this.scale[1], this.scale[2]);
	}

	@Override
	public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight)
	{
		super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);

	}

	@Override
	protected void setupRotations(T entityLiving, MatrixStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks)
	{
		super.setupRotations(entityLiving, matrixStack, ageInTicks, rotationYaw, partialTicks);
	}

	@Override
	public ResourceLocation getTextureLocation(T entity)
	{
		if ((this.texture == null && entity instanceof IDynamicRenderer) || this.texture.equals(new ResourceLocation(Main.MODID + ":textures/entities/null.png")))
		{
			String textureName = ((IDynamicRenderer) entity).getMobTexture();
			if(Strings.isNullOrEmpty(textureName))
				textureName = ((IDynamicRenderer) entity).getDefaultTexture();
			return new ResourceLocation(Main.MODID, "textures/entities/npc/" + textureName + ".png");
			
		}
		else
			return this.texture;
	}

	public static class Factory implements IRenderFactory
	{
		protected BipedModel model;
		private float[] scale;
		private String texture;

		public Factory(BipedModel model, float scale)
		{
			this(model, scale, scale, scale, null);
		}

		public Factory(BipedModel model, float scale, String texture)
		{
			this(model, scale, scale, scale, texture);
		}

		public Factory(BipedModel model, float scaleX, float scaleY, float scaleZ)
		{
			this(model, scaleX, scaleY, scaleZ, null);
		}

		public Factory(BipedModel model, float scaleX, float scaleY, float scaleZ, String texture)
		{
			this.model = model;
			this.texture = texture;

			this.scale = new float[] { scaleX, scaleY, scaleZ };
		}

		@Override
		public EntityRenderer createRenderFor(EntityRendererManager manager)
		{
			return new HumanoidRenderer(manager, this.model, this.scale, this.texture);
		}
	}
}
