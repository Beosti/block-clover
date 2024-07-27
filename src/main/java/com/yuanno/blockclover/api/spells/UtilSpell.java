package com.yuanno.blockclover.api.spells;

import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import com.yuanno.blockclover.api.spells.components.ItemSpellComponent;
import com.yuanno.blockclover.api.spells.components.PunchComponent;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import net.minecraft.entity.player.PlayerEntity;

public class UtilSpell {

    /**
     * Check if a spell has a specific component
     * @param spell
     * @param spellComponentClass
     * @return
     */
    public static boolean hasComponent(Spell spell, Class<? extends SpellComponent> spellComponentClass)
    {
        for (SpellComponent component : spell.getSpellComponents()) {
            if (spellComponentClass.isInstance(component)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the instance of a spell component class you want
     * @param spell
     * @param spellComponentClass
     * @return
     */
    public static SpellComponent getComponent(Spell spell, Class<? extends SpellComponent> spellComponentClass) {
        for (SpellComponent component : spell.getSpellComponents()) {
            if (spellComponentClass.isInstance(component)) {
                return component;
            }
        }
        return null;
    }

    /**
     * Checks if a spell can combo aka the effect will do the combo effects
     */
    public static boolean canCombo(PlayerEntity player, Spell spell)
    {
        for (int i = 0; i < spell.getSpellComponents().size(); i++)
        {
            SpellComponent currentSpellComponent = spell.getSpellComponents().get(i);
            if (spell.getSpellComponents().get(i) instanceof ComboSpellComponent)
            {
                ComboSpellComponent comboSpellComponent = (ComboSpellComponent) currentSpellComponent;
                if (SpellDataCapability.get(player).getPreviousSpellUsed() != null && SpellDataCapability.get(player).getPreviousSpellUsed().equals(comboSpellComponent.getSpellToCombo()))
                    return true;
            }
        }

        return false;
    }


    /**
     * Simple check if a spell contains a {@link ContinuousSpellComponent} or a {@link PunchComponent} or not
     * @param spell
     * @return true if it does contain a continuous component, returns false if not
     */
    public static boolean containsContinuousComponent(Spell spell)
    {
        boolean flagContinuous = false;
        for (SpellComponent spellComponent : spell.getSpellComponents())
        {
            if (spellComponent instanceof ContinuousSpellComponent || spellComponent instanceof PunchComponent || spellComponent instanceof ItemSpellComponent)
            {
                flagContinuous = true;
                break;
            }
        }
        return flagContinuous;
    }

    /**
     * Does all the logic handling of a spell in one handy method (it's annoying to write everywhere)
     * Sets the state to cooldown, puts the cooldown, alters the experience, levels it up if needed and adds to max experience
     * Does NOT do any packets
     * @param spell
     */
    public static void usedSpell(Spell spell)
    {
        spell.setState(Spell.STATE.COOLDOWN);
        spell.setCurrentCooldown(spell.getMaxCooldown() * 20);
        spell.alterSpellExperience(1);
        //spellData.setPreviousSpellUsed(spellUsed);
        if (spell.getSpellMaxExperience() <= spell.getSpellExperience())
        {
            spell.alterSpellLevel(1);
            spell.setSpellExperience(0);
            spell.alterSpellMaxExperience(10);
        }
    }
}
