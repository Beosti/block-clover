package com.yuanno.blockclover.client.screens.spell;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.util.TexturedIconButton;
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
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;

/**
 * Main hub of information of the player;
 * Link to other information
 */
@OnlyIn(Dist.CLIENT)
public class SpellChoiceScreen extends Screen {

    private final PlayerEntity player;
    private final IEntityStats entityStats;
    private final ISpellData spellData;
    private ArrayList<Spell> spells = new ArrayList<>();
    protected SpellChoiceScreen(ArrayList<Spell> spells) {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
        this.spellData = SpellDataCapability.get(player);
        this.spells = spells;
    }

    /**
     * Called everytime the screen opens (once);
     * Initialize buttons here
     */
    @Override
    public void init()
    {
        TexturedIconButton texturedIconButtonSpell1 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/ability/" + spells.get(0).getName().toLowerCase().toString() + ".png"), -50, -50, 32, 32, new TranslationTextComponent(""), button -> {
            System.out.println("chose this spell: " + spells.get(0).getName());
        });
        TexturedIconButton texturedIconButtonSpell2 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/ability/" + spells.get(1).getName().toLowerCase().toString() + ".png"), 0, 0, 32, 32, new TranslationTextComponent(""), button -> {
            System.out.println("chose this spell: " + spells.get(1).getName());
        });
        TexturedIconButton texturedIconButtonSpell3 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/ability/" + spells.get(2).getName().toLowerCase().toString() + ".png"), 50, 50, 32, 32, new TranslationTextComponent(""), button -> {
            System.out.println("chose this spell: " + spells.get(2).getName());
        });
        this.addButton(texturedIconButtonSpell1);
        this.addButton(texturedIconButtonSpell2);
        this.addButton(texturedIconButtonSpell3);
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
        //drawString(matrixStack, this.font, TextFormatting.BOLD + "Race: " + TextFormatting.RESET + entityStats.getMiscData().getRace(), posX, posY, -1);
        //drawString(matrixStack, this.font, TextFormatting.BOLD + "Level: " + TextFormatting.RESET + entityStats.getMagicData().getLevel(), posX, posY + 20, -1);
        //drawString(matrixStack, this.font, TextFormatting.BOLD + "Experience: " + TextFormatting.RESET + entityStats.getMagicData().getExperience() + "/" + entityStats.getMagicData().getMaxExperience(), posX, posY + 35, -1);

        super.render(matrixStack, mouseX, mouseY, f);
    }

    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    public static void open(ArrayList<Spell> spells)
    {
        Minecraft.getInstance().setScreen(new SpellChoiceScreen(spells));
    }
}
