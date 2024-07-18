package com.yuanno.blockclover.events.projectiles;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;

@Cancelable
public class ProjectileHitEvent extends Event
{
	private SpellProjectileEntity projectile;
	private RayTraceResult hit;



	public ProjectileHitEvent(SpellProjectileEntity abilityProjectileEntity, RayTraceResult hit) {
		this.projectile = abilityProjectileEntity;
		this.hit = hit;

	}

	public SpellProjectileEntity getProjectile()
	{
		return this.projectile;
	}

	public RayTraceResult getHit()
	{
		return this.hit;
	}
}
