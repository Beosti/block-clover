package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.entity.fire.FireballProjectile;

public class TestBallSpell extends Spell {
    public static TestBallSpell INSTANCE = new TestBallSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(FireballProjectile::projectile)
            .onHitEffect(3, (onhit) -> onhit.setSecondsOnFire(3))
            .levelTripleShot(5)
            .build();
    public TestBallSpell()
    {
        this.setName("Test");
        this.setDescription("T");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
