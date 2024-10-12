package com.yuanno.blockclover.models;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.util.HandSide;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class HumanoidModel<T extends CreatureEntity> extends BipedModel<T> {

    public HumanoidArmPose armsPose = HumanoidArmPose.EMPTY;

    public HumanoidModel()
    {
        super(0, 0, 64, 64);
        this.hat.visible = true;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha)
    {
        super.renderToBuffer(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (Minecraft.getInstance().isPaused())
            return;

    }

    @Override
    public void translateToHand(HandSide side, MatrixStack matrixStack)
    {
        super.translateToHand(side, matrixStack);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }

    @OnlyIn(Dist.CLIENT)
    public static enum HumanoidArmPose
    {
        EMPTY
    }

}
