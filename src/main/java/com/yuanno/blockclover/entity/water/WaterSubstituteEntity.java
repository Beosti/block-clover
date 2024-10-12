package com.yuanno.blockclover.entity.water;

import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.entity.SpellProjectileEntity;
import com.yuanno.blockclover.entity.fire.FireProjectiles;
import com.yuanno.blockclover.entity.fire.FireballProjectile;
import com.yuanno.blockclover.entity.util.CloneEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class WaterSubstituteEntity extends CloneEntity {

    public WaterSubstituteEntity(EntityType type, World world)
    {
        super(type, world);
    }


    public static WaterSubstituteEntity summon(PlayerEntity player)
    {
        return new WaterSubstituteEntity(WaterAttributeEntities.WATER_SUBSTITUTE.get(), player.level);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes()
    {
        return WaterSubstituteEntity.createMobAttributes()
                .add(Attributes.FOLLOW_RANGE, 35)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 10.0D)
                .add(Attributes.ARMOR, 2.0D);
    }


    @Override
    public void remove()
    {
        if (!this.level.isClientSide)
        {
            for (int i = 0; i < 10; i++)
            {
                double offsetX = Beapi.randomDouble();
                double offsetY = Beapi.randomDouble();
                double offsetZ = Beapi.randomDouble();

                if (i % 2 == 0)
                    ((ServerWorld) this.getCommandSenderWorld()).sendParticles(ParticleTypes.DRIPPING_WATER, this.getX() + offsetX , (this.getY() + 1.5) + offsetY, this.getZ() + offsetZ, 1, 0.0D, 0, 0.0D, 0.05);
                else
                    ((ServerWorld) this.getCommandSenderWorld()).sendParticles(ParticleTypes.FALLING_WATER, this.getX() + offsetX , (this.getY() + 1.5) + offsetY, this.getZ() + offsetZ, 1, 0.0D, 0, 0.0D, 0.05);
            }
        }
        super.remove();
    }


    @Override
    public void die(DamageSource source)
    {
        if (source.getEntity() instanceof LivingEntity)
        {
            LivingEntity entity = (LivingEntity) source.getEntity();
            if (entity == this.getOwner())
                return;
            //entity.addEffect(new EffectInstance(ModEffects.BUBBLED.get(), 100, 0));
        }
        super.die(source);
    }
}
