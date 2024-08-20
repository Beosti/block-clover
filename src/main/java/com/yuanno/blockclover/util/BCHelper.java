package com.yuanno.blockclover.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.util.RenderHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class BCHelper {

    static int backgroundColorStart = Beapi.hexToRGB("#000000").getRGB();
    static int backgroundColorEnd = Beapi.hexToRGB("#36454F").getRGB();
    static int backgroundStart = Beapi.hexToRGB("#36454F").getRGB();
    static int backgroundEnd = Beapi.hexToRGB("#000000").getRGB();


    public static ResourceLocation getResourceLocationSpell(Spell spell)
    {
        String originalResourceLocation = spell.getIDName();
        String formatedResourceLocation = originalResourceLocation.replaceAll(" ", "_").toLowerCase();
        ResourceLocation resourceLocation = new ResourceLocation(Main.MODID, "textures/spell/" + formatedResourceLocation + ".png");
        return resourceLocation;
    }

    public static void renderSpellTooltip(MatrixStack matrixStack, Spell spell, int mouseX, int mouseY, Screen screen)
    {
        String name = String.valueOf(spell.getName());
        String description = String.valueOf(spell.getDescription());
        int maxCooldown = spell.getMaxCooldown();
        TranslationTextComponent spellName = spell.getName();
        TranslationTextComponent spellDescription = spell.getDescription();
        //StringBuilder longString = new StringBuilder("Name: " + name + "\n" + "Description: " + description + "\n" +
        //        "Cooldown: " + maxCooldown +
        //        "\n" + "Level: " +spell.getSpellLevel() +
        //        "\n" + spell.getSpellExperience() + "/" + spell.getSpellMaxExperience());
        /*
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

         */
        List<ITextProperties> textLines = new ArrayList<>();
        textLines.add(spellName);
        textLines.add(spellDescription);
        textLines.add(ITextProperties.of("Cooldown: " + maxCooldown));
        textLines.add(ITextProperties.of("Level: " + spell.getSpellLevel()));
        textLines.add(ITextProperties.of(spell.getSpellExperience() + "/" + spell.getSpellMaxExperience()));

        RenderHelper.drawAbilityTooltip(spell, matrixStack, textLines, mouseX, mouseY, screen.width, screen.height, 210, backgroundColorStart, backgroundColorEnd, backgroundStart, backgroundEnd, screen.getMinecraft().font);
    }

    public static void renderAttributeTooltip(MatrixStack matrixStack, String nameAttribute, int mouseX, int mouseY, Screen screen)
    {
        TranslationTextComponent attributeName = new TranslationTextComponent("blockclover.attribute." + nameAttribute.toLowerCase() + ".name");
        TranslationTextComponent attributeDescription = new TranslationTextComponent("blockclover.attribute." + nameAttribute.toLowerCase() + ".description");

        List<ITextProperties> textLines = new ArrayList<>();
        textLines.add(attributeName);
        textLines.add(attributeDescription);
        RenderHelper.drawTooltip(nameAttribute, matrixStack, textLines, mouseX, mouseY, screen.width, screen.height, 210, backgroundColorStart, backgroundColorEnd, backgroundStart, backgroundEnd, screen.getMinecraft().font);
    }

    public static void renderSpellTooltip(MatrixStack matrixStack, Spell spell, int mouseX, int mouseY, Screen screen, boolean renderLevel)
    {
        String name = String.valueOf(spell.getName());
        String description = String.valueOf(spell.getDescription());
        int maxCooldown = spell.getMaxCooldown();
        TranslationTextComponent spellName = spell.getName();
        TranslationTextComponent spellDescription = spell.getDescription();
        //StringBuilder longString = new StringBuilder("Name: " + name + "\n" + "Description: " + description + "\n" +
        //        "Cooldown: " + maxCooldown +
        //        "\n" + "Level: " +spell.getSpellLevel() +
        //        "\n" + spell.getSpellExperience() + "/" + spell.getSpellMaxExperience());
        /*
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

         */
        List<ITextProperties> textLines = new ArrayList<>();
        textLines.add(spellName);
        textLines.add(spellDescription);
        textLines.add(ITextProperties.of("Cooldown: " + maxCooldown));
        if (renderLevel)
        {
            textLines.add(ITextProperties.of("Level: " + spell.getSpellLevel()));
            textLines.add(ITextProperties.of(spell.getSpellExperience() + "/" + spell.getSpellMaxExperience()));
        }
        RenderHelper.drawAbilityTooltip(spell, matrixStack, textLines, mouseX, mouseY, screen.width, screen.height, 210, backgroundColorStart, backgroundColorEnd, backgroundStart, backgroundEnd, screen.getMinecraft().font);
    }
}
