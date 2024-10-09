package com.yuanno.blockclover.client.screens.spell;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.SpellComponent;
import com.yuanno.blockclover.client.util.RenderHelper;
import com.yuanno.blockclover.client.util.TexturedIconButton;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncSpellDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import com.yuanno.blockclover.util.BCHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
    private ArrayList<TexturedIconButton> texturedIconButtons = new ArrayList<>();
    private int amount;
    protected SpellChoiceScreen(ArrayList<Spell> spells) {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
        this.spellData = SpellDataCapability.get(player);
        this.spells = spells;
    }
    protected SpellChoiceScreen(ArrayList<Spell> spells, int amount) {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
        this.spellData = SpellDataCapability.get(player);
        this.spells = spells;
        this.amount = amount;
    }

    /**
     * Called everytime the screen opens (once);
     * Initialize buttons here
     */
    @Override
    public void init()
    {
        int posX = 10;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            TexturedIconButton button = new TexturedIconButton(
                    new ResourceLocation(Main.MODID, "textures/spell/" + spells.get(i).getIDName().toLowerCase() + ".png"),
                    posX, 80, 64, 64,
                    new TranslationTextComponent(""),
                    btn -> {
                        spellData.addUnlockedSpell(spells.get(finalI));
                        if (this.amount != 0)
                        {
                            this.amount--;
                            init();
                        }
                        else
                            this.onClose();
                    }
            );
            texturedIconButtons.add(button);
            this.addButton(button);
            posX += 175;
        }
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

        for (int i = 0; i < texturedIconButtons.size(); i++)
        {
            if (texturedIconButtons.get(i).isHovered())
                BCHelper.renderSpellTooltip(matrixStack, this.spells.get(i), mouseX, mouseY, this, false);
        }
        this.renderBackground(matrixStack);

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
    public static void open(ArrayList<Spell> spells, int amount)
    {
        Minecraft.getInstance().setScreen(new SpellChoiceScreen(spells));
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        PacketHandler.sendToServer(new CSyncSpellDataPacket(spellData));
    }
}
