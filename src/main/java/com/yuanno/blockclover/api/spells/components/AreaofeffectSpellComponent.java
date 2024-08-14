package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;

import java.io.Serializable;
import java.util.List;

public class AreaofeffectSpellComponent extends SpellComponent {

    //private IArea area = (player) -> {return;};
    private int range;
    private int secondsOnFire = 0;
    private EffectInstance effectInstance;

    public AreaofeffectSpellComponent(AreaofeffectComponentBuilder builder)
    {
        this.range = builder.range;
        this.secondsOnFire = builder.secondsOnFire;
        this.effectInstance = builder.effectInstance;
    }

    public void areaofeffectComponent(PlayerEntity player)
    {
        List<LivingEntity> targets = Beapi.getNearbyEntities(player.blockPosition(), player.level, this.range, null, LivingEntity.class);
        targets.remove(player);
        for (LivingEntity livingEntity : targets)
        {
            if (this.secondsOnFire != 0)
                livingEntity.setSecondsOnFire(this.secondsOnFire);
            if (this.effectInstance != null)
                livingEntity.addEffect(this.effectInstance);
        }
    }

    public static class AreaofeffectComponentBuilder
    {
        private int range;
        private int secondsOnFire;
        private EffectInstance effectInstance;

        public AreaofeffectComponentBuilder()
        {

        }

        public AreaofeffectComponentBuilder setRange(int amount)
        {
            this.range = amount;
            return this;
        }

        public AreaofeffectComponentBuilder setSecondsOnFire(int amount)
        {
            this.secondsOnFire = amount;
            return this;
        }

        public AreaofeffectComponentBuilder setEffectInstance(EffectInstance effectInstance)
        {
            this.effectInstance = effectInstance;
            return this;
        }

        public AreaofeffectSpellComponent build()
        {
            return new AreaofeffectSpellComponent(this);
        }
    }
    /*
    public interface IArea extends Serializable
    {
        void area(PlayerEntity player);
    }
     */
}
