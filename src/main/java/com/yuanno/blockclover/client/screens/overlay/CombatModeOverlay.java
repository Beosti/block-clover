package com.yuanno.blockclover.client.screens.overlay;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
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
import net.minecraft.util.text.TextFormatting;
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
                if (spellData.getEquippedSpells().get(i) != null && spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.COOLDOWN)) {
                    RenderHelper.drawIcon(combatResource, 141 + (i * 21), 238, 0, 16, 16, 256, 256, 23, 0, 24, 23, 1, 0, 0);
                }
                else
                    RenderHelper.drawIcon(combatResource, 141 + (i * 21), 238, 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, iconColor.getGreen() /255.0f,  iconColor.getBlue() /255.0f);
                if (spellData.getEquippedSpells().get(i) != null)
                {
                    RenderHelper.drawIcon(BCHelper.getResourceLocationSpell(spellData.getEquippedSpells().get(i)), 141 + (i * 21), 238, 0, 16, 16, 16, 16, 0, 0, 16, 16, iconColor.getRed() / 255.0f, iconColor.getGreen() /255.0f,  iconColor.getBlue() /255.0f);
                }
                if (spellData.getEquippedSpells().get(i) != null && spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.COOLDOWN))
                    drawTimer(matrixStack, spellData.getEquippedSpells().get(i).getCurrentCooldown(), 141 + (i * 21), 238);

            }
            matrixStack.popPose();
        }
    }

    private void drawTimer(MatrixStack matrixStack, int timer, int iconX, int iconY) {
        matrixStack.pushPose();
        matrixStack.scale(0.5f, 0.5f, 0.5f);
        matrixStack.translate(1, 1, 1000); // Move the text to a higher z-level
        int stringWidth = Minecraft.getInstance().font.width(String.valueOf(timer));
        int posX = (int) ((iconX - (stringWidth / 5)) / 0.5f);
        Beapi.drawStringWithBorder(Minecraft.getInstance().font, matrixStack, TextFormatting.WHITE + "" + timer, posX, (int) (iconY / 0.5), 0);
        matrixStack.popPose();
    }
}
