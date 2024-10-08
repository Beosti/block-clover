package com.yuanno.blockclover.spells.fire;

import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.entity.fire.FireballProjectile;
import com.yuanno.blockclover.util.SpellHelper;

public class FireballSpell extends Spell {
    public static FireballSpell INSTANCE = new FireballSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(FireballProjectile::projectile)
            .onHitEffect(3, (onhit) -> onhit.setSecondsOnFire(3))
            .levelTripleShot(5)
            .build();

    public FireballSpell()
    {
        this.setName("fireball");
        this.setDescription("Fires a ball of fire towards where you're looking");
        SpellHelper.onLevelOneStandardSpell(this);
        //this.onStandardization = SpellHelper::onLevelOneStandardSpell;
        this.addSpellCompontent(spellComponent);
    }
}