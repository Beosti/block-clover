package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.AreaofeffectSpellComponent;

public class FirewaveSpell extends Spell {
    public static FirewaveSpell INSTANCE = new FirewaveSpell();
    private AreaofeffectSpellComponent spellComponent = new AreaofeffectSpellComponent.AreaofeffectComponentBuilder()
            .setSecondsOnFire(5)
            .setRange(10)
            .build();

    public FirewaveSpell()
    {
        this.setName("Firewave");
        this.setDescription("Sends out a wave of fire around you putting people on fire");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.setManaCost(12);
        this.setExperienceGain(10);
        this.addSpellCompontent(spellComponent);
    }
}
