package com.yuanno.blockclover.events.projectiles;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ProjectileShootEvent extends Event
{
	private SpellProjectileEntity projectile;
	private float velocity;
	private float inaccuracy;
	private LivingEntity owner;


	public ProjectileShootEvent(SpellProjectileEntity abilityProjectileEntity, float velocity, float inaccuracy, LivingEntity owner) {
		this.projectile = abilityProjectileEntity;
		this.velocity = velocity;
		this.inaccuracy = inaccuracy;
		this.owner = owner;
	}

	public SpellProjectileEntity getProjectile()
	{
		return this.projectile;
	}

	public float getVelocity()
	{
		return this.velocity;
	}
	
	public float getInaccuracy()
	{
		return this.inaccuracy;
	}

	public LivingEntity getOwner()
	{
		return this.owner;
	}
}
