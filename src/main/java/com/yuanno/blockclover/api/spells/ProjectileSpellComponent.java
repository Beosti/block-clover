package com.yuanno.blockclover.api.spells;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import com.yuanno.blockclover.entity.fire.FireballProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;

import java.util.function.Supplier;

public class ProjectileSpellComponent extends SpellComponent {

    public Supplier<SpellProjectileEntity> spellProjectileEntity;
    private int speed;
    private float innacuracy;

    public ProjectileSpellComponent(Supplier<SpellProjectileEntity> entityType, int speed, float precision)
    {
        this.spellProjectileEntity = entityType;
        this.speed = speed;
        this.innacuracy = precision;
    }
    private static final DataParameter<Integer> OWNER = EntityDataManager.defineId(SpellProjectileEntity.class, DataSerializers.INT);

    public void shootProjectileSpellComponent(PlayerEntity player)
    {
        if (player.level.isClientSide)
            return;
        SpellProjectileEntity spellProjectile = new FireballProjectile(player.level, player);
        spellProjectile.setThrower(player);
        player.level.addFreshEntity(spellProjectile);
        spellProjectile.shootFromRotation(player, player.xRot, player.yRot, 0, this.speed, this.innacuracy);
    }
}
