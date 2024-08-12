package com.yuanno.blockclover.events.spell;

import com.yuanno.blockclover.api.spells.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Cancelable;

/**
 * When a player clicks a button to use a spell in {@link com.yuanno.blockclover.init.ModKeyBinds} it gets funneled through here
 * After that it goes to {@link SpellEvents} and there the rest of the logic is handled
 */
@Cancelable
public class SpellUseEvent extends LivingEvent {

    private LivingEntity spellUser;
    private Spell spell;
    public SpellUseEvent(LivingEntity spellUser) {
        super(spellUser);
        this.spellUser = spellUser;
    }

    public SpellUseEvent(LivingEntity spellUser, Spell spell) {
        super(spellUser);
        this.spellUser = spellUser;
        this.spell = spell;
    }

    public Spell getSpell()
    {
        return this.spell;
    }

    public LivingEntity getSpellUser()
    {
        return this.spellUser;
    }
}
