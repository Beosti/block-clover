package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class ContinuousSpellComponent extends SpellComponent {

    private int maxDuration = -1;
    private int currentDuration = -1;
    public IStartContinuitySpell startContinuitySpell = (player -> {return;});
    public IDuringContinuitySpell duringContinuitySpell = (player -> {return;});
    public IEndContinuitySpell endContinuitySpell = (player -> {return;});

    public ContinuousSpellComponent(ContinuousSpellComponentBuilder builder)
    {
        this.startContinuitySpell = builder.startContinuitySpell;
        this.duringContinuitySpell = builder.duringContinuitySpell;
        this.endContinuitySpell = builder.endContinuitySpell;
        this.maxDuration = builder.duration;
        this.currentDuration = builder.duration;
    }

    public static class ContinuousSpellComponentBuilder
    {
        private IStartContinuitySpell startContinuitySpell = (player -> {return;});
        private IDuringContinuitySpell duringContinuitySpell = (player -> {return;});
        private IEndContinuitySpell endContinuitySpell = (player -> {return;});
        private int duration = -1;

        public ContinuousSpellComponentBuilder()
        {

        }

        public ContinuousSpellComponentBuilder setStartContinuity(IStartContinuitySpell startContinuity)
        {
            this.startContinuitySpell = startContinuity;
            return this;
        }

        public ContinuousSpellComponentBuilder setDuringContinuity(IDuringContinuitySpell duringContinuitySpell)
        {
            this.duringContinuitySpell = duringContinuitySpell;
            return this;
        }

        public ContinuousSpellComponentBuilder setEndContinuity(IEndContinuitySpell endContinuitySpell)
        {
            this.endContinuitySpell = endContinuitySpell;
            return this;
        }

        public ContinuousSpellComponentBuilder setDuration(int duration)
        {
            this.duration = duration;
            return this;
        }

        public ContinuousSpellComponent build()
        {
            return new ContinuousSpellComponent(this);
        }
    }

    public interface IStartContinuitySpell extends Serializable
    {
        void startContinuity(PlayerEntity player);
    }

    public interface IDuringContinuitySpell extends Serializable
    {
        void duringContinuity(PlayerEntity player);
    }

    public interface IEndContinuitySpell extends Serializable
    {
        void endContinuity(PlayerEntity player);
    }

    public int getMaxDuration()
    {
        return this.maxDuration;
    }

    public int getCurrentDuration()
    {
        return this.currentDuration;
    }

    public void alterCurrentDuration(int amount)
    {
        this.currentDuration += amount;
    }

    public void setCurrentDuration(int currentDuration)
    {
        this.currentDuration = currentDuration;
    }
}
