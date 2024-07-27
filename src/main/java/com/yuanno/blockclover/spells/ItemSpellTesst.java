package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import com.yuanno.blockclover.api.spells.components.ItemSpellComponent;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.Items;

import java.util.UUID;

public class ItemSpellTesst extends Spell {
    public static ItemSpellTesst INSTANCE = new ItemSpellTesst();

    private ItemSpellComponent spellComponent = new ItemSpellComponent.ItemSpellComponentBuilder()
            .setItem(Items.DIAMOND)
            .build();



    public ItemSpellTesst()
    {
        this.setName("Continuous item stuff fr");
        this.setDescription("Does stuff fr fr");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
