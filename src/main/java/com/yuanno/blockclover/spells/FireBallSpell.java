package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;

public class FireBallSpell extends Spell {
    public static FireBallSpell INSTANCE = new FireBallSpell();

    public FireBallSpell()
    {
        this.setName("Fireball");
        this.setDescription("Fires a ball of fire towards where you're looking");
        this.setMaxCooldown(10);
    }
}
