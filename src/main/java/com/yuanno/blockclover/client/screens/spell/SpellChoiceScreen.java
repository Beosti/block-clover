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
    TexturedIconButton texturedIconButton;
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
        TexturedIconButton texturedIconButtonSpell1 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/spell/" + spells.get(0).getIDName().toLowerCase().toString() + ".png"), 25, 50, 64, 64, new TranslationTextComponent(""), button -> {
            spellData.addUnlockedSpell(spells.get(0));
            this.onClose();
        });
        TexturedIconButton texturedIconButtonSpell2 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/spell/" + spells.get(1).getIDName().toLowerCase().toString() + ".png"), 100, 50, 64, 64, new TranslationTextComponent(""), button -> {
            spellData.addUnlockedSpell(spells.get(1));
            this.onClose();
        });
        TexturedIconButton texturedIconButtonSpell3 = new TexturedIconButton(new ResourceLocation(Main.MODID, "textures/spell/" + spells.get(2).getIDName().toLowerCase().toString() + ".png"), 150, 50, 64, 64, new TranslationTextComponent(""), button ->
        {
            spellData.addUnlockedSpell(spells.get(2));
            this.onClose();
        }, ((button, matrixStack, mouseX, mouseY) ->
        {
            System.out.println("something");
            if (button.isHovered()) {
                renderSpellTooltip(matrixStack, spells.get(2), mouseX, mouseY);
                System.out.println("hovered");
            }
        }));
        texturedIconButton = texturedIconButtonSpell3;
        this.addButton(texturedIconButtonSpell1);
        this.addButton(texturedIconButtonSpell2);
        this.addButton(texturedIconButtonSpell3);
    }
    int backgroundColorStart = Beapi.hexToRGB("#000000").getRGB();
    int backgroundColorEnd = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundStart = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundEnd = Beapi.hexToRGB("#000000").getRGB();

    private void renderSpellTooltip(MatrixStack matrixStack, Spell spell, int mouseX, int mouseY)
    {
        String name = String.valueOf(spell.getName());
        String description = String.valueOf(spell.getDescription());
        int maxCooldown = spell.getMaxCooldown();
        StringBuilder longString = new StringBuilder("Name: " + name + "\n" + "Description: " + description + "\n" +
                "Cooldown: " + maxCooldown);
        for (int i = 0; i < spell.getSpellComponents().size(); i++)
        {
            SpellComponent spellComponent = spell.getSpellComponents().get(i);
            HashMap<Integer, String> upgradeMap = spellComponent.getUpgradeMap();
            for (Map.Entry<Integer, String> entry : upgradeMap.entrySet())
            {
                int keyLevel = entry.getKey();
                String valueName = entry.getValue();
                if (keyLevel != 0) {
                    longString.append("\n" + "Unlock: " + valueName + " at level " + keyLevel);
                    break;
                }
            }
        }
        RenderHelper.drawAbilityTooltip(spell, matrixStack, Arrays.asList(new StringTextComponent(longString.toString())), mouseX, mouseY, this.width, this.height, 210, backgroundColorStart, backgroundColorEnd, backgroundStart, backgroundEnd, this.getMinecraft().font);

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

        if (texturedIconButton.isHovered())
        {
            renderSpellTooltip(matrixStack, this.spells.get(2), mouseX, mouseY);
        }
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

    @Override
    public void onClose() {
        this.minecraft.popGuiLayer();
        PacketHandler.sendToServer(new CSyncSpellDataPacket(spellData));
    }
}
