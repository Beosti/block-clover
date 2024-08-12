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
public class ManaBarOverLay {
    private final ResourceLocation emptyBarResource = new ResourceLocation(Main.MODID + ":textures/gui/manabar/empty_bar.png");
    private final ResourceLocation filledBarResource = new ResourceLocation(Main.MODID + ":textures/gui/manabar/fire_bar.png");

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        if (!(event.getType() == RenderGameOverlayEvent.ElementType.TEXT))
            return;
        float currentMana = entityStats.getMagicData().getCurrentMana();
        int maxMana = entityStats.getMagicData().getMaxMana();
        double numberMana = currentMana/maxMana;
        double currentHeight = 256 * numberMana;
        RenderHelper.drawIcon(emptyBarResource, 0, 0, 0, 256, 256, 256, 256, 0, 0, 256, 256, 1, 1, 1);

        int extra = 0;
        if (numberMana > 0.5 && numberMana < 0.7)
            extra = 1;
        else if (numberMana > 0.3 && numberMana < 0.5)
            extra = 2;
        else if (numberMana > 0 && numberMana < 0.3)
            extra = 3;
        RenderHelper.drawIcon(filledBarResource, 0, (int) (256 - Math.floor(currentHeight)) - extra, 0, 256, (int) currentHeight, 256, 256, 0, 0, 256, 256, 1, 1, 1);
    }
}
