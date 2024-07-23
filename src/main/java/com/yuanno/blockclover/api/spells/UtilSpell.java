package com.yuanno.blockclover.api.spells;

import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;

public class UtilSpell {

    /**
     * Simple check if a spell contains a {@link ContinuousSpellComponent} or not
     * @param spell
     * @return true if it does contain a continuous component, returns false if not
     */
    public static boolean containsContinuousComponent(Spell spell)
    {
        boolean flagContinuous = false;
        for (SpellComponent spellComponent : spell.getSpellComponents())
        {
            if (spellComponent instanceof ContinuousSpellComponent)
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
