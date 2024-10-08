package com.yuanno.blockclover.entity.lightning;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LightningchargeProjectile extends SpellProjectileEntity {

    public LightningchargeProjectile(EntityType entityType, World world)
    {
        super(entityType, world);
    }

    public LightningchargeProjectile(World world, LivingEntity livingEntity)
    {
        super(LightningProjectiles.LIGHTNINGCHARGE.get(), world, livingEntity);
        this.setDamage(6);
        this.setMaxLife(64);
        this.setPhysical(false);
    }

    public static SpellProjectileEntity projectile(PlayerEntity player)
    {
        return new LightningchargeProjectile(player.level, player);
    }
}
