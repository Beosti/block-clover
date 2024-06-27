package com.yuanno.blockclover.client.screens.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.client.util.RenderHelper;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.data.spell.SpellDatabase;
import com.yuanno.blockclover.util.BCHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class CombatModeOverlay {

    private final ResourceLocation combatResource = new ResourceLocation(Main.MODID + ":textures/gui/combat_widget.png");
    private final int texture_width = 23, texture_height = 23;
    Color iconColor = Beapi.hexToRGB("#FFFFFF");

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent event)
    {
        PlayerEntity player = Minecraft.getInstance().player;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        ISpellData spellData = SpellDataCapability.get(player);
        if (!entityStats.getCombatData().getCombatMode())
            return;
        if (event.getType() == RenderGameOverlayEvent.ElementType.HOTBAR)
        {
            event.setCanceled(true);
            MatrixStack matrixStack = event.getMatrixStack();
            matrixStack.pushPose();
            for (int i = 0; i < 9; i++)
            {
                RenderHelper.drawIcon(combatResource, 141 + (i * 21), 238, 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, iconColor.getGreen() /255.0f,  iconColor.getBlue() /255.0f);
                if (spellData.getEquippedSpells().size() >= i + 1 && !spellData.getEquippedSpells().isEmpty() && spellData.getEquippedSpells().get(i) != null)
                {
                    RenderHelper.drawIcon(BCHelper.getResourceLocationSpell(spellData.getEquippedSpells().get(i)), 141 + (i * 21), 238, 0, 16, 16, 16, 16, 0, 0, 16, 16, iconColor.getRed() / 255.0f, iconColor.getGreen() /255.0f,  iconColor.getBlue() /255.0f);
                }
            }
            matrixStack.popPose();
        }
    }
}
