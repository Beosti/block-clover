package com.yuanno.blockclover.entity.water;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class WaterballProjectile extends SpellProjectileEntity {

    public WaterballProjectile(EntityType entityType, World world)
    {
        super(entityType, world);
    }

    public WaterballProjectile(World world, LivingEntity livingEntity)
    {
        super(WaterAttributeEntities.WATERBALL.get(), world, livingEntity);
        this.setDamage(6);
        this.setMaxLife(64);
        this.setPhysical(false);
    }

    public static SpellProjectileEntity projectile(PlayerEntity player)
    {
        return new WaterballProjectile(player.level, player);
    }
}
