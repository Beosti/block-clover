package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.components.PunchComponent;
import com.yuanno.blockclover.entity.fire.FireballProjectile;

public class PunchSpellTest extends Spell {
    public static PunchSpellTest INSTANCE = new PunchSpellTest();
    private PunchComponent spellComponent = new PunchComponent.PunchComponentBuilder()
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
