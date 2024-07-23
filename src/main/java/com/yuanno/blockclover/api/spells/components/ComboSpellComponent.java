package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class ComboSpellComponent extends SpellComponent {

    private Spell spellToCombo;
    public ICombo combo = (player -> {return;});

    public ComboSpellComponent(ComboSpellComponentBuilder builder)
    {
        this.spellToCombo = builder.spellToCombo;
        this.combo = builder.combo;
    }

    public static class ComboSpellComponentBuilder {
        private Spell spellToCombo;
        private ICombo combo = ((player -> {return;}));

        public ComboSpellComponentBuilder()
        {

        }

        public ComboSpellComponentBuilder setSpellToCombo(Spell spell)
        {
            this.spellToCombo = spell;
            return this;
        }

        public ComboSpellComponentBuilder setCombo(ICombo combo)
        {
            this.combo = combo;
            return this;
        }

        public ComboSpellComponent build()
        {
            return new ComboSpellComponent(this);
        }
    }

    public void setSpellToCombo(Spell spell)
    {
        this.spellToCombo = spell;
    }
    public Spell getSpellToCombo() {
        return spellToCombo;
    }

    public interface ICombo extends Serializable
    {
        void comboDoing(PlayerEntity player);
    }
}
