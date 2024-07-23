package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.entity.fire.FireballProjectile;

public class FireBallSpell extends Spell {
    public static FireBallSpell INSTANCE = new FireBallSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(FireballProjectile::projectile)
            .onHitEffect(3, (onhit) -> onhit.setSecondsOnFire(3))
            .levelTripleShot(5)
            .build();
    private ComboSpellComponent comboSpellComponent = new ComboSpellComponent.ComboSpellComponentBuilder()
            .setCombo((player) -> player.heal(20))
            .setSpellToCombo(TestBallSpell.INSTANCE)
            .build();
    public FireBallSpell()
    {
        this.setName("Fireball");
        this.setDescription("Fires a ball of fire towards where you're looking");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(comboSpellComponent);
        this.addSpellCompontent(spellComponent);
    }


}
