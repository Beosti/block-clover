package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.SpellComponent;
import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class ProjectileSpellComponent extends SpellComponent {

    private int speed;
    private float inacuracy;
    private int tripleShot;
    private int effectHit;
    private SpellProjectileEntity.IOnEntityImpact onEntityImpact;
    private int areaOfEffect;
    private int newProjectileLevel;
    public IProjectileSpell projectileSpell = (player -> {return null;});
    private IProjectileSpell newProjectileSpell = (player -> {return null;});

    public ProjectileSpellComponent()
    {
        this.speed = 1;
        this.inacuracy = 1;
    }

    public ProjectileSpellComponent(IProjectileSpell projectileSpell)
    {
        this.projectileSpell = projectileSpell;
        this.speed = 1;
        this.inacuracy = 1;
    }

    public ProjectileSpellComponent(ProjectileSpellComponentBuilder builder)
    {
        this.projectileSpell = builder.projectileSpell;
        this.speed = builder.speed;
        this.inacuracy = builder.inacuracy;
        this.tripleShot = builder.tripleShot;
        this.addToUpgradeMap(this.tripleShot, "triple shot");
        this.effectHit = builder.effectHit;
        this.onEntityImpact = builder.onEntityImpact;
        this.addToUpgradeMap(this.effectHit, "hit effect");
        this.areaOfEffect = builder.areaOfEffect;
        this.addToUpgradeMap(this.areaOfEffect, "Area of effect");
        this.newProjectileLevel = builder.newProjectileLevel;
        this.newProjectileSpell = builder.newProjectile;
        this.addToUpgradeMap(this.newProjectileLevel, "New Projectile");
    }


    public void shootProjectileSpellComponent(PlayerEntity player, Spell usedSpell)
    {
        if (player.level.isClientSide)
            return;
        SpellProjectileEntity spellProjectileGetter = projectileSpell.getSpellProjectile(player);
        if (this.effectHit != 0 && this.effectHit <= usedSpell.getSpellLevel())
            spellProjectileGetter.onEntityImpactEvent = this.onEntityImpact;

        player.level.addFreshEntity(spellProjectileGetter);
        spellProjectileGetter.shootFromRotation(player, player.xRot, player.yRot, 0, this.speed, this.inacuracy);
        if (this.tripleShot > 0 && tripleShot <= usedSpell.getSpellLevel())
        {
            SpellProjectileEntity newSpellprojectile = projectileSpell.getSpellProjectile(player);
            player.level.addFreshEntity(newSpellprojectile);
            newSpellprojectile.shootFromRotation(player, player.xRot, player.yRot + 45, 0, this.speed, this.inacuracy);
            SpellProjectileEntity anotherNewSpellprojectile = projectileSpell.getSpellProjectile(player);
            player.level.addFreshEntity(anotherNewSpellprojectile);
            anotherNewSpellprojectile.shootFromRotation(player, player.xRot, player.yRot - 45, 0, this.speed, this.inacuracy);

        }
    }

    public static class ProjectileSpellComponentBuilder {
        private IProjectileSpell projectileSpell = (player -> {return null;});
        private int speed = 1;
        private float inacuracy = 1;
        private int tripleShot = 0;
        private int effectHit = 0;
        private SpellProjectileEntity.IOnEntityImpact onEntityImpact;
        private int areaOfEffect = 0;
        private int newProjectileLevel = 0;
        private IProjectileSpell newProjectile = (player -> {return null;});


        public ProjectileSpellComponentBuilder()
        {

        }


        public ProjectileSpellComponentBuilder projectileSpell(IProjectileSpell iProjectileSpell)
        {
            this.projectileSpell = iProjectileSpell;
            return this;
        }

        public ProjectileSpellComponentBuilder projectileInacuracy(int speed)
        {
            this.speed = speed;
            return this;
        }

        public ProjectileSpellComponentBuilder projectileSpeed(float inacuracy)
        {
            this.inacuracy = inacuracy;
            return this;
        }

        public ProjectileSpellComponentBuilder onHitEffect(int level, SpellProjectileEntity.IOnEntityImpact onEntityImpact)
        {
            this.effectHit = level;
            this.onEntityImpact = onEntityImpact;
            return this;
        }

        public ProjectileSpellComponentBuilder levelTripleShot(int level)
        {
            this.tripleShot = level;
            return this;
        }

        public ProjectileSpellComponentBuilder newProjectile(int level, IProjectileSpell iProjectileSpell)
        {
            this.newProjectileLevel = level;
            this.newProjectile = iProjectileSpell;
            return this;
        }


        public ProjectileSpellComponent build()
        {
            return new ProjectileSpellComponent(this);
        }
    }


    public interface IProjectileSpell extends Serializable
    {
        SpellProjectileEntity getSpellProjectile(PlayerEntity player);
    }
}
