package com.yuanno.blockclover.entity.earth;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class EarthshotProjectile extends SpellProjectileEntity {

    public EarthshotProjectile(EntityType entityType, World world)
    {
        super(entityType, world);
    }

    public EarthshotProjectile(World world, LivingEntity livingEntity)
    {
        super(EarthProjectiles.EARTHSHOT.get(), world, livingEntity);
        this.setDamage(6);
        this.setMaxLife(64);
        this.setPhysical(false);
    }

    public static SpellProjectileEntity projectile(PlayerEntity player)
    {
        return new EarthshotProjectile(player.level, player);
    }
}
