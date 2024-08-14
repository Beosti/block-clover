package com.yuanno.blockclover.client.screens.menu;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.SpellComponent;
import com.yuanno.blockclover.client.util.Entry;
import com.yuanno.blockclover.client.util.RenderHelper;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncSpellDataPacket;
import com.yuanno.blockclover.util.BCHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.*;
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
    List<Entry> spellEntries = new ArrayList<>();
    List<Entry> combatEntries = new ArrayList<>();
    Entry selectedEntry;
    int backgroundColorStart = Beapi.hexToRGB("#000000").getRGB();
    int backgroundColorEnd = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundStart = Beapi.hexToRGB("#36454F").getRGB();
    int backgroundEnd = Beapi.hexToRGB("#000000").getRGB();
    private final ResourceLocation combatResource = new ResourceLocation(Main.MODID + ":textures/gui/combat_widget.png");
    Color iconColor = Beapi.hexToRGB("#FFFFFF");

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
        // gets the spells they have and put it in the spell entries
        for (int i = 0; i < spellData.getUnlockedSpells().size(); i++)
        {
            Entry spellEntry = new Entry(spellData.getUnlockedSpells().get(i), 50 + (i * 20), 50, Entry.Category.SPELL);
            this.spellEntries.add(spellEntry);
        }
        // sets the combat entries
        for (int i = 0; i < 9; i++)
        {
            Entry combatEntry = new Entry(null, 50 + (i * 20), 150, Entry.Category.COMBAT_BAR);
            this.combatEntries.add(combatEntry);
        }
        for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
        {
            if (spellData.getEquippedSpells().get(i) != null)
            {
                this.combatEntries.get(i).setSpell(spellData.getEquippedSpells().get(i));
            }
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

        this.renderBackground(matrixStack);
        for (Entry entry : this.combatEntries) {
            if (entry.getSpell() != null)
                RenderHelper.drawIcon(BCHelper.getResourceLocationSpell(entry.getSpell()), entry.getX(), entry.getY(), 0, 16, 16, 16, 16, 0, 0, 16, 16, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f);

            if (!entry.equals(selectedEntry))
                RenderHelper.drawIcon(combatResource, entry.getX(), entry.getY(), 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f);
            else
                RenderHelper.drawIcon(combatResource, entry.getX(), entry.getY(), 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, 255.0f, iconColor.getRed() / 255.0f);
        }
        // draws the spells
        for (Entry entry : this.spellEntries) {
            RenderHelper.drawIcon(BCHelper.getResourceLocationSpell(entry.getSpell()), entry.getX(), entry.getY(), 0, 16, 16, 16, 16, 0, 0, 16, 16, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f);

            if (!entry.equals(selectedEntry))
                RenderHelper.drawIcon(combatResource, entry.getX(), entry.getY(), 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f, iconColor.getRed() / 255.0f);
            else
                RenderHelper.drawIcon(combatResource, entry.getX(), entry.getY(), 0, 16, 16, 256, 256, 23, 0, 24, 23, iconColor.getRed() / 255.0f, 255.0f, iconColor.getRed() / 255.0f);
        }
        Entry spellEntry = getHoveredEntry(mouseX, mouseY);
        if (spellEntry != null && spellEntry.getCategory().equals(Entry.Category.SPELL))
            renderSpellTooltip(matrixStack, spellEntry.getSpell(), mouseX, mouseY);
        super.render(matrixStack, mouseX, mouseY, f);
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Entry clickedOnEntry = getHoveredEntry(mouseX, mouseY);
        if (clickedOnEntry == null)
            return super.mouseClicked(mouseX, mouseY, button);
        if (this.selectedEntry == null) // didn't have anything selected -> remember the one selected now and return
        {
            if (this.spellEntries.contains(clickedOnEntry))
                clickedOnEntry.setCategory(Entry.Category.SPELL);
            else
                clickedOnEntry.setCategory(Entry.Category.COMBAT_BAR);
            this.selectedEntry = clickedOnEntry;
            return super.mouseClicked(mouseX, mouseY, button);
        }
        else if (this.selectedEntry.equals(clickedOnEntry)) // reselect the one selected -> unselect
        {
            this.selectedEntry.setSpell(null);
            this.selectedEntry = null;
            return super.mouseClicked(mouseX, mouseY, button);
        }
        else if (this.combatEntries.contains(this.selectedEntry) && this.spellEntries.contains(this.selectedEntry)) // if you don't click on 2 diff lists
        {
            if (this.spellEntries.contains(clickedOnEntry))
                clickedOnEntry.setCategory(Entry.Category.SPELL);
            else
                clickedOnEntry.setCategory(Entry.Category.COMBAT_BAR);
            this.selectedEntry = clickedOnEntry;
            return super.mouseClicked(mouseX, mouseY, button);
        }
        else if (clickedOnEntry.getCategory().equals(Entry.Category.SPELL))
            this.selectedEntry.setSpell(clickedOnEntry.getSpell());
        else
            clickedOnEntry.setSpell(clickedOnEntry.getSpell());
        this.selectedEntry = null;

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void renderSpellTooltip(MatrixStack matrixStack, Spell spell, int mouseX, int mouseY)
    {
        String name = String.valueOf(spell.getName());
        String description = String.valueOf(spell.getDescription());
        int maxCooldown = spell.getMaxCooldown();
        StringBuilder longString = new StringBuilder("Name: " + name + "\n" + "Description: " + description + "\n" +
                "Cooldown: " + maxCooldown +
                "\n" + "Level: " +spell.getSpellLevel() +
                "\n" + spell.getSpellExperience() + "/" + spell.getSpellMaxExperience());
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
    @Override
    public boolean isPauseScreen()
    {
        return true;
    }

    public static void open()
    {
        Minecraft.getInstance().setScreen(new PlayerSpellScreen());
    }

    @Override
    public void onClose() {
        ISpellData spellData = SpellDataCapability.get(player);
        try
        {
            spellData.getEquippedSpells().clear();
            for (Entry combatEntry : this.combatEntries) {
                spellData.addEquippedSpell(combatEntry.getSpell());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        PacketHandler.sendToServer(new CSyncSpellDataPacket(spellData));
        super.onClose();
    }


    public Entry getHoveredEntry(double mouseX, double mouseY) {
        int iconWidth = 16;  // Width of each icon
        int iconHeight = 16; // Height of each icon

        ArrayList<Entry> allEntries = new ArrayList<>();
        allEntries.addAll(this.spellEntries);
        allEntries.addAll(this.combatEntries);
        for (Entry entry : allEntries) {
            int iconX = entry.getX();
            int iconY = entry.getY();

            // Check if the mouse is over the current icon
            if (mouseX >= iconX && mouseX < iconX + iconWidth &&
                    mouseY >= iconY && mouseY < iconY + iconHeight) {
                return entry; // Mouse is over the current icon
            }
        }

        return null; // Mouse is not over any icon
    }
}
