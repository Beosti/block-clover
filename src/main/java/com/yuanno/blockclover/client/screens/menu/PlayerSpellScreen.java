package com.yuanno.blockclover.client.screens.menu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Main hub of information of the player;
 * Link to other information
 */
@OnlyIn(Dist.CLIENT)
public class PlayerSpellScreen extends Screen {

    private final PlayerEntity player;
    private final IEntityStats entityStats;
    private final ISpellData spellData;
    protected PlayerSpellScreen() {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
        this.spellData = SpellDataCapability.get(player);
    }

    /**
     * Called everytime the screen opens (once);
     * Initialize buttons here
     */
    @Override
    public void init()
    {
        System.out.println(spellData.getUnlockedSpells().get(0).getName());
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
        for (int i = 0; i < spellData.getUnlockedSpells().size(); i++)
        {
            String originalResourceLocation = spellData.getUnlockedSpells().get(i).getName();
            String formatedResourceLocation = originalResourceLocation.replaceAll(" ", "_").toLowerCase();
            ResourceLocation resourceLocation = new ResourceLocation(Main.MODID, "textures/ability/" + formatedResourceLocation + ".png");
            //System.out.println(resourceLocation);
            Beapi.drawIcon(resourceLocation, 0, 0, 0, 16, 16, 16, 16, 0, 0, 16, 16, 0, 0, 0);
        }
        super.render(matrixStack, mouseX, mouseY, f);
    }

    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new PlayerSpellScreen());
    }
}
