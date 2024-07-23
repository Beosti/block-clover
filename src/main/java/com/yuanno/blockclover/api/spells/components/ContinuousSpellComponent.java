package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class ContinuousSpellComponent extends SpellComponent {

    public int duration;
    public IStartContinuitySpell startContinuitySpell = (player -> {return;});
    public IDuringContinuitySpell duringContinuitySpell = (player -> {return;});
    public IEndContinuitySpell endContinuitySpell = (player -> {return;});

    public ContinuousSpellComponent(ContinuousSpellComponentBuilder builder)
    {
        this.startContinuitySpell = builder.startContinuitySpell;
        this.duringContinuitySpell = builder.duringContinuitySpell;
        this.endContinuitySpell = builder.endContinuitySpell;
    }

    public static class ContinuousSpellComponentBuilder
    {
        private IStartContinuitySpell startContinuitySpell = (player -> {return;});
        private IDuringContinuitySpell duringContinuitySpell = (player -> {return;});
        private IEndContinuitySpell endContinuitySpell = (player -> {return;});

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
}
