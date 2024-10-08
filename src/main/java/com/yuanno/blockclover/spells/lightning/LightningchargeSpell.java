package com.yuanno.blockclover.spells.lightning;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.lightning.LightningchargeProjectile;
import com.yuanno.blockclover.entity.water.WaterballProjectile;
import com.yuanno.blockclover.util.SpellHelper;

public class LightningchargeSpell extends Spell {
    public static LightningchargeSpell INSTANCE = new LightningchargeSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent.ProjectileSpellComponentBuilder()
            .projectileSpell(LightningchargeProjectile::projectile)
            .build();

    public LightningchargeSpell()
    {
        this.setName("Lightningcharge");
        this.setDescription("Fires a ball of lightning towards where you're looking");
        SpellHelper.onLevelOneStandardSpell(this);
        this.addSpellCompontent(spellComponent);
    }
}
