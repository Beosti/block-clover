package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.fire.FireballProjectile;

public class ContinuosSpellTest extends Spell {
    public static ContinuosSpellTest INSTANCE = new ContinuosSpellTest();
    private ContinuousSpellComponent spellComponent = new ContinuousSpellComponent.ContinuousSpellComponentBuilder()
            .setStartContinuity((player) -> {System.out.println("stuff");})
            .setDuringContinuity((player -> {System.out.println("during");}))
            .setEndContinuity((player -> {System.out.println("ending");}))
            .setDuration(12)
            .build();

    public ContinuosSpellTest()
    {
        this.setName("Continuous");
        this.setDescription("Does stuff");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
