package com.yuanno.blockclover.data.spell;

import com.yuanno.blockclover.api.spells.Spell;

import javax.annotation.Nullable;
import java.util.ArrayList;

public interface ISpellData {
    void addUnlockedSpell(Spell spell);
    <T extends Spell> T getUnlockedSpell(T sp);
    void removeUnlockedSpell(Spell spell);
    ArrayList<Spell> getUnlockedSpells();
    void clearUnlockedSpells();

    void addEquippedSpell(@Nullable Spell spell, int placement);

    void addEquippedSpell(Spell spell);

    <T extends Spell> T getEquippedSpell(T sp);
    void removeEquippedSpell(Spell spell);
    ArrayList<Spell> getEquippedSpells();
    void clearEquippedSpells();

    void setPreviousSpellUsed(Spell spell);

    Spell getPreviousSpellUsed();

    void clearPreviousSpellUsed();
}
