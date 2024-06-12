package com.yuanno.blockclover.data.spell;

import com.yuanno.blockclover.api.spells.Spell;

import java.util.ArrayList;

public class SpellDatabase implements ISpellData {

    private ArrayList<Spell> unlockedSpells = new ArrayList<Spell>();
    private ArrayList<Spell> equippedSpells = new ArrayList<Spell>();
    
    private Spell previousSpellUsed;

    @Override
    public void addUnlockedSpell(Spell spell)
    {
        this.unlockedSpells.add(spell);
    }
    @Override
    public <T extends Spell> T getUnlockedSpell(T sp)
    {
        this.unlockedSpells.removeIf(spell -> spell == null);
        return (T) this.unlockedSpells.stream().filter(spell -> spell.equals(sp)).findFirst().orElse(null);
    }
    @Override
    public void removeUnlockedSpell(Spell spell)
    {
        if (this.equippedSpells.contains(spell))
            this.equippedSpells.remove(spell);
        this.unlockedSpells.remove(spell);
    }
    @Override
    public ArrayList<Spell> getUnlockedSpells()
    {
        return this.unlockedSpells;
    }
    @Override
    public void clearUnlockedSpells()
    {
        this.unlockedSpells.clear();
    }

    @Override
    public void addEquippedSpell(Spell spell, int placement)
    {
        this.equippedSpells.set(placement, spell);
    }
    @Override
    public void addEquippedSpell(Spell spell)
    {
        this.equippedSpells.add(spell);
    }
    @Override
    public <T extends Spell> T getEquippedSpell(T sp)
    {
        this.equippedSpells.removeIf(spell -> spell == null);
        return (T) this.equippedSpells.stream().filter(spell -> spell.equals(sp)).findFirst().orElse(null);
    }
    @Override
    public void removeEquippedSpell(Spell spell)
    {
        this.equippedSpells.remove(spell);
    }
    @Override
    public ArrayList<Spell> getEquippedSpells()
    {
        return this.equippedSpells;
    }
    @Override
    public void clearEquippedSpells()
    {
        this.equippedSpells.clear();
    }

}
