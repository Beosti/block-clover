package com.yuanno.blockclover.client.screens.menu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.util.RenderHelper;
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

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main hub of information of the player;
 * Link to other information
 */
@OnlyIn(Dist.CLIENT)
public class PlayerSpellScreen extends Screen {

    private final PlayerEntity player;
    private final IEntityStats entityStats;
    private final ISpellData spellData;
    List<Entry> entries = new ArrayList<>();
    int backgroundColorStart = Beapi.hexToRGB("#000000").getRGB();
    int backgroundColorEnd = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundStart = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundEnd = Beapi.hexToRGB("#000000").getRGB();

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
            Spell spell = spellData.getUnlockedSpells().get(i);
            String originalResourceLocation = spellData.getUnlockedSpells().get(i).getName();
            String formatedResourceLocation = originalResourceLocation.replaceAll(" ", "_").toLowerCase();
            ResourceLocation resourceLocation = new ResourceLocation(Main.MODID, "textures/ability/" + formatedResourceLocation + ".png");
            //System.out.println(resourceLocation);
            Color iconColor = Beapi.hexToRGB("#FFFFFF");
            RenderHelper.drawIcon(resourceLocation, 0, 0, 0, 16, 16, 16, 16, 0, 0, 16, 16, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f);
            entries.add(new Entry(spell, 0, 0));
        }
        Entry entry = getHoveredEntry(mouseX, mouseY);
        if (entry != null)
        {
            String name = entry.spell.getName();
            String description = entry.spell.getDescription();
            int maxCooldown = entry.spell.getMaxCooldown();
            StringBuilder longString = new StringBuilder("Name: " + name + "\n" + "Description: " + description + "\n" + "Cooldown: " + maxCooldown);
            RenderHelper.drawAbilityTooltip(entry.spell, matrixStack, Arrays.asList(new StringTextComponent(longString.toString())), mouseX, mouseY, this.width, this.height, 210, backgroundColorStart, backgroundColorEnd, backgroundStart, backgroundEnd, this.getMinecraft().font);
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

    public Entry getHoveredEntry(double mouseX, double mouseY) {
        int iconWidth = 16;  // Width of each icon
        int iconHeight = 16; // Height of each icon

        for (Entry entry : entries) {
            int iconX = entry.x;
            int iconY = entry.y;

            // Check if the mouse is over the current icon
            if (mouseX >= iconX && mouseX < iconX + iconWidth &&
                    mouseY >= iconY && mouseY < iconY + iconHeight) {
                return entry; // Mouse is over the current icon
            }
        }

        return null; // Mouse is not over any icon
    }


    class Entry
    {
        private Spell spell;
        private int x;
        private int y;
        public Entry(Spell spell, int x, int y)
        {
            this.spell = spell;
            this.x = x;
            this.y = y;
        }
    }
}
