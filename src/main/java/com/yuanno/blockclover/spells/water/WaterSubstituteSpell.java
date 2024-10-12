package com.yuanno.blockclover.spells.water;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.SummoningSpellComponent;
import com.yuanno.blockclover.entity.water.WaterSubstituteEntity;

public class WaterSubstituteSpell extends Spell {
    public static WaterSubstituteSpell INSTANCE = new WaterSubstituteSpell();
    private SummoningSpellComponent spellComponent = new SummoningSpellComponent.SummoningSpellComponentBuilder()
            .summonSpell(WaterSubstituteEntity::summon)
            .amount(5)
            .build();

    public WaterSubstituteSpell()
    {
        this.setName("Water Substitute");
        this.setDescription("Creates multiple water substitutes");
        this.setMaxCooldown(20);
        this.addSpellCompontent(spellComponent);
    }
}
