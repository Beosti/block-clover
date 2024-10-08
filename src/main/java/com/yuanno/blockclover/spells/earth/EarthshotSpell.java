package com.yuanno.blockclover.spells.earth;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.earth.EarthshotProjectile;
import com.yuanno.blockclover.entity.water.WaterballProjectile;
import com.yuanno.blockclover.util.SpellHelper;

public class EarthshotSpell extends Spell {
    public static EarthshotSpell INSTANCE = new EarthshotSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(EarthshotProjectile::projectile)
            .build();

    public EarthshotSpell()
    {
        this.setName("Earthshot");
        this.setDescription("Fires a ball of earth towards where you're looking");
        SpellHelper.onLevelOneStandardSpell(this);
        this.addSpellCompontent(spellComponent);
    }
}
