package com.yuanno.blockclover.entity.fire;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class FireballProjectile extends SpellProjectileEntity {

    public FireballProjectile(EntityType entityType, World world)
    {
        super(entityType, world);
    }

    public FireballProjectile(World world, LivingEntity livingEntity)
    {
        super(FireProjectiles.FIREBALL.get(), world, livingEntity);
        this.setDamage(6);
        this.setMaxLife(64);
        this.setPhysical(false);
    }

    public FireballProjectile() {
        super(FireProjectiles.FIREBALL.get(), null);
    }
}
