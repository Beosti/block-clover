package com.yuanno.blockclover.entity.wind;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WindbladeProjectile extends SpellProjectileEntity {

    public WindbladeProjectile(EntityType entityType, World world)
    {
        super(entityType, world);
    }

    public WindbladeProjectile(World world, LivingEntity livingEntity)
    {
        super(WindProjectiles.WINDBLADE.get(), world, livingEntity);
        this.setDamage(6);
        this.setMaxLife(64);
        this.setPhysical(false);
    }

    public static SpellProjectileEntity projectile(PlayerEntity player)
    {
        return new WindbladeProjectile(player.level, player);
    }
}
