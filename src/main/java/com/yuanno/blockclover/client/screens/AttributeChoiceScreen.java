package com.yuanno.blockclover.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.util.TexturedIconButton;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.entity.MagicData;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.init.ModValues;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncEntityStatsPacket;
import com.yuanno.blockclover.networking.client.CSyncSpellDataPacket;
import com.yuanno.blockclover.spells.FireballSpell;
import com.yuanno.blockclover.util.BCHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Main hub of information of the player;
 * Link to other information
 */
@OnlyIn(Dist.CLIENT)
public class AttributeChoiceScreen extends Screen {

    private final PlayerEntity player;
    private final IEntityStats entityStats;
    public ArrayList<String> chosenAttributes = new ArrayList<>();
    private ArrayList<TexturedIconButton> texturedIconButtons = new ArrayList<>();
    private MagicData magicData;
    protected AttributeChoiceScreen() {
        super(new StringTextComponent(""));
        this.player = Minecraft.getInstance().player;
        this.entityStats = EntityStatsCapability.get(player);
        this.magicData = this.entityStats.getMagicData();
        if (magicData.getChosenAttributes() != null)
            this.chosenAttributes = magicData.getChosenAttributes();
    }

    private ArrayList<String> setChosenAttributes(ArrayList<String> allAttributes)
    {
        Collections.shuffle(allAttributes);
        ArrayList<String> returnList = new ArrayList<>();
        returnList.add(allAttributes.get(0));
        returnList.add(allAttributes.get(1));
        returnList.add(allAttributes.get(2));
        return returnList;
    }

    /**
     * Called everytime the screen opens (once);
     * Initialize buttons here
     */
    @Override
    public void init()
    {
        if (this.chosenAttributes.isEmpty())
            this.chosenAttributes = setChosenAttributes(ModValues.attributes);
        int posX = 10;
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            TexturedIconButton button = new TexturedIconButton(
                    new ResourceLocation(Main.MODID, "textures/gui/icons/attributes/" + chosenAttributes.get(i).toLowerCase() + ".png"),
                    posX, 80, 64, 64,
                    new TranslationTextComponent(""),
                    btn -> {
                        magicData.setAttribute(chosenAttributes.get(finalI));
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
                BCHelper.renderAttributeTooltip(matrixStack, this.chosenAttributes.get(i), mouseX, mouseY, this);
        }
        this.renderBackground(matrixStack);

        super.render(matrixStack, mouseX, mouseY, f);
    }

    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new AttributeChoiceScreen());
    }

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        this.magicData.setChosenAttributes(this.chosenAttributes);
        PacketHandler.sendToServer(new CSyncEntityStatsPacket(this.entityStats));
        ISpellData spellData = SpellDataCapability.get(player);
        if (magicData.getAttribute().equals(ModValues.FIRE))
            spellData.addUnlockedSpell(FireballSpell.INSTANCE);
        PacketHandler.sendToServer(new CSyncSpellDataPacket(spellData));
    }
}
