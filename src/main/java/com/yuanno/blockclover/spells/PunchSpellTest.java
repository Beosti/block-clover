package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.PunchSpellComponent;

public class PunchSpellTest extends Spell {
    public static PunchSpellTest INSTANCE = new PunchSpellTest();
    private PunchSpellComponent spellComponent = new PunchSpellComponent.PunchComponentBuilder()
            .setIPunch((player, target) -> {target.setSecondsOnFire(3);})
            .build();
    public PunchSpellTest()
    {
        this.setName("Punch");
        this.setDescription("punch");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
