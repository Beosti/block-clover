package com.yuanno.blockclover.entity.util;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.UUID;

public class CloneEntity extends CreatureEntity {

    private static final DataParameter<Optional<UUID>> OWNER = EntityDataManager.defineId(CloneEntity.class, DataSerializers.OPTIONAL_UUID);
    private static final DataParameter<Boolean> IS_TEXTURED = EntityDataManager.defineId(CloneEntity.class, DataSerializers.BOOLEAN);
    private int maxAliveTicks = 200;

    public CloneEntity(EntityType type, World world)
    {
        super(type, world);
    }


    @Override
    protected void registerGoals()
    {
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.25D));
    }

    @Override
    protected void defineSynchedData()
    {
        super.defineSynchedData();
        this.entityData.define(OWNER, Optional.empty());
        this.entityData.define(IS_TEXTURED, false);
    }

    @Override
    public void tick()
    {
        super.tick();
        if (!this.level.isClientSide && this.getOwner() == null)
        {
            this.remove();
            return;
        }

        this.setLastHurtByMob(this.getOwner());

        if (this.tickCount >= this.maxAliveTicks)
            this.remove();
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT compound)
    {
        super.addAdditionalSaveData(compound);
        if (this.entityData.get(OWNER) != null)
            compound.putString("OwnerUUID", this.entityData.get(OWNER).get().toString());
        compound.putBoolean("isTextured", this.entityData.get(IS_TEXTURED));
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound)
    {
        super.readAdditionalSaveData(compound);
        this.entityData.set(OWNER, Optional.of(UUID.fromString(compound.getString("OwnerUUID"))));
        this.entityData.set(IS_TEXTURED, compound.getBoolean("isTextured"));
    }

    public void setOwner(UUID uuid)
    {
        this.entityData.set(OWNER, Optional.of(uuid));
    }

    @Nullable
    public UUID getOwnerUUID()
    {
        return this.getEntityData().get(OWNER).orElse(null);
    }

    @Nullable
    public PlayerEntity getOwner()
    {
        UUID id = this.getOwnerUUID();
        if(id == null)
            return null;
        return this.level.getPlayerByUUID(id);
    }

    public void setUseOwnerTexture()
    {
        this.entityData.set(IS_TEXTURED, true);
    }

    public boolean isUsingOwnerTexture()
    {
        return this.getEntityData().get(IS_TEXTURED);
    }

    public void setMaxAliveTicks(int ticks)
    {
        this.maxAliveTicks = ticks;
    }
}
