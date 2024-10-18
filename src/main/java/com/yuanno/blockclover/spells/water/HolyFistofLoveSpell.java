package com.yuanno.blockclover.spells.water;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.PunchSpellComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

public class HolyFistofLoveSpell extends Spell {
    public static HolyFistofLoveSpell INSTANCE = new HolyFistofLoveSpell();
    public void onPunch(PlayerEntity player, LivingEntity entity)
    {
        entity.hurt(DamageSource.MAGIC, 8);
    }
    private PunchSpellComponent spellComponent = new PunchSpellComponent.PunchComponentBuilder()
            .setOnePunch(true)
            .setIPunch(this::onPunch)
            .build();

    public HolyFistofLoveSpell()
    {
        this.setName("Holy Fist of Love");
        this.setDescription("Next punch hits them with a powerful water fist made out of love");
        this.addSpellCompontent(spellComponent);
    }
}
