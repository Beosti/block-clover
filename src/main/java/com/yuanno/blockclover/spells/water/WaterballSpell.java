package com.yuanno.blockclover.spells.water;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.water.WaterballProjectile;

public class WaterballSpell extends Spell {
    public static WaterballSpell INSTANCE = new WaterballSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(WaterballProjectile::projectile)
            .on
}
