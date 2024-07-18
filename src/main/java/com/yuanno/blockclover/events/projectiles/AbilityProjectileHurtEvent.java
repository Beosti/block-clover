package com.yuanno.blockclover.events.projectiles;

import net.minecraft.entity.Entity;
import net.minecraftforge.eventbus.api.Event;

public class AbilityProjectileHurtEvent extends Event {
    private Entity projectile;
    private Entity attacker;

    public AbilityProjectileHurtEvent(Entity projectile, Entity attacker)
    {
        this.projectile = projectile;
        this.attacker = attacker;
    }

    public Entity getProjectile()
    {
        return this.projectile;
    }

    public Entity getAttacker()
    {
        return this.attacker;
    }
}