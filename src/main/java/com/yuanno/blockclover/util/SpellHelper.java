package com.yuanno.blockclover.util;

import com.yuanno.blockclover.api.spells.Spell;

public class SpellHelper {

    public static void onLevelOneStandardSpell(Spell spell)
    {
        spell.setMaxCooldown(10);
        spell.setSpellMaxExperience(50);
        spell.setManaCost(12);
        spell.setExperienceGain(10);
    }
}
