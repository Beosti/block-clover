package com.yuanno.blockclover.spells.water;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.water.WaterballProjectile;
import com.yuanno.blockclover.util.SpellHelper;

public class WaterballSpell extends Spell {
    public static WaterballSpell INSTANCE = new WaterballSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(WaterballProjectile::projectile)
            .build();

    public WaterballSpell()
    {
        this.setName("Waterball");
        this.setDescription("Fires a ball of water towards where you're looking");
        SpellHelper.onLevelOneStandardSpell(this);
        this.addSpellCompontent(spellComponent);
    }
}
