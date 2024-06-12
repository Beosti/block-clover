package com.yuanno.blockclover.data.spell;

import com.yuanno.blockclover.api.spells.Spell;

import java.util.ArrayList;

public interface ISpellData {
    void addUnlockedSpell(Spell spell);
    <T extends Spell> T getUnlockedSpell(T sp);
    void removeUnlockedSpell(Spell spell);
    ArrayList<Spell> getUnlockedSpells();
    void clearUnlockedSpells();

    void addEquippedSpell(Spell spell, int placement);

    void addEquippedSpell(Spell spell);

    <T extends Spell> T getEquippedSpell(T sp);
    void removeEquippedSpell(Spell spell);
    ArrayList<Spell> getEquippedSpells();
    void clearEquippedSpells();
}
