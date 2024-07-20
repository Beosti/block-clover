package com.yuanno.blockclover.api.spells;

import com.yuanno.blockclover.entity.SpellProjectileEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.Serializable;

public class ComboSpellComponent extends SpellComponent {

    private Spell spellToCombo;
    public ICombo combo = (player -> {return;});

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
