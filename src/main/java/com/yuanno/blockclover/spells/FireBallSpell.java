package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.entity.SpellProjectileEntity;
import com.yuanno.blockclover.entity.fire.FireProjectiles;
import com.yuanno.blockclover.entity.fire.FireballProjectile;
import net.minecraft.entity.EntityType;

import java.util.function.Supplier;

public class FireBallSpell extends Spell {
    public static FireBallSpell INSTANCE = new FireBallSpell();
    private ProjectileSpellComponent spellComponent = new ProjectileSpellComponent(
            FireballProjectile::new, 1, 1);

    public FireBallSpell()
    {
        this.setName("Fireball");
        this.setDescription("Fires a ball of fire towards where you're looking");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }
}
