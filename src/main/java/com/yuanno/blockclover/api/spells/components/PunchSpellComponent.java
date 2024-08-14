package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class PunchSpellComponent extends SpellComponent {

    private IPunch iPunch = ((player, target) -> {return;});
    private boolean onePunch;

    public PunchSpellComponent(PunchComponentBuilder builder)
    {
        this.iPunch = builder.iPunch;
        this.onePunch = builder.onePunch;
    }

    public static class PunchComponentBuilder {
        private IPunch iPunch;
        private boolean onePunch;

        public PunchComponentBuilder()
        {

        }

        public PunchComponentBuilder setIPunch(IPunch iPunch)
        {
            this.iPunch = iPunch;
            return this;
        }

        public PunchComponentBuilder setOnePunch(boolean flag)
        {
            this.onePunch = flag;
            return this;
        }

        public PunchSpellComponent build()
        {
            return new PunchSpellComponent(this);
        }
    }

    public boolean endPunch()
    {
        return this.onePunch;
    }

    public IPunch getiPunch()
    {
        return this.iPunch;
    }

    public interface IPunch extends Serializable
    {
        void punch(PlayerEntity player, LivingEntity target);
    }
}
