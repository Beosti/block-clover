package com.yuanno.blockclover.client.screens.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.client.util.RenderHelper;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

@OnlyIn(Dist.CLIENT)
public class CombatModeOverlay {

    private final ResourceLocation combatResource = new ResourceLocation(Main.MODID + ":textures/gui/combat_widget.png");
    private final int texture_width = 23, texture_height = 23;

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        if (!entityStats.getCombatData().getCombatMode())
            return;
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            event.setCanceled(true);
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.pushPose();
            for (int i = 0; i < 9; i++)
            {
                RenderHelper.drawIcon(combatResource, 141 + (i * 21), 238, 0, 16, 16, 256, 256, 23, 0, 24, 23, 0, 0, 0);
            }
            matrixStack.popPose();
        }
    }
}
