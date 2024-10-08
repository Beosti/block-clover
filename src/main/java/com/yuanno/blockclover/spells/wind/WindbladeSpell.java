package com.yuanno.blockclover.spells.wind;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.water.WaterballProjectile;
import com.yuanno.blockclover.entity.wind.WindbladeProjectile;
import com.yuanno.blockclover.util.SpellHelper;

public class WindbladeSpell extends Spell {
    public static WindbladeSpell INSTANCE = new WindbladeSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(WindbladeProjectile::projectile)
            .build();

    public WindbladeSpell()
    {
        this.setName("Windblade");
        this.setDescription("Fires a blade of wind towards where you're looking");
        SpellHelper.onLevelOneStandardSpell(this);
        this.addSpellCompontent(spellComponent);
    }
}
