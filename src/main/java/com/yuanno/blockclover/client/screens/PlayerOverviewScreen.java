package com.yuanno.blockclover.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Main hub of information of the player;
 * Link to other information
 */
@OnlyIn(Dist.CLIENT)
public class PlayerOverviewScreen extends Screen {

    private final PlayerEntity player;
    private final IEntityStats entityStats;
    protected PlayerOverviewScreen() {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
    }

    /**
     * Called everytime the screen opens (once);
     * Initialize buttons here
     */
    @Override
    public void init()
    {

    }

    /**
     * Called every frame to draw something
     * @param matrixStack
     * @param mouseX
     * @param mouseY
     * @param f
     */
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float f)
    {
        int posX = (this.width - 256) / 2;
        int posY = (this.height - 256) / 2;

        this.renderBackground(matrixStack);
        // join the world -> update data server side -> no update client side -> retrieve info data side -> crash
        drawString(matrixStack, this.font, TextFormatting.BOLD + "Race: " + TextFormatting.RESET + entityStats.getMiscData().getRace(), posX, posY, -1);
        super.render(matrixStack, mouseX, mouseY, f);
    }

    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new PlayerOverviewScreen());
    }
}
