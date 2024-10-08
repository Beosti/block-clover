package com.yuanno.blockclover.spells.fire;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;

import java.util.UUID;

public class FiredupSpell extends Spell {
    public static FiredupSpell INSTANCE = new FiredupSpell();
    public AttributeModifier attributeModifierSpeed = new AttributeModifier(UUID.fromString("fbb3619d-2c6d-4c4e-a3b7-202b5346dee4"), "Fired up speed", 0.2, AttributeModifier.Operation.ADDITION);
    public AttributeModifier attributeModifierAttackSpeed = new AttributeModifier(UUID.fromString("a1a6183c-5a36-11ef-b864-0242ac120002"), "Fired up attack speed", 0.2, AttributeModifier.Operation.ADDITION);
    public AttributeModifier attributeModifierAttackDamage = new AttributeModifier(UUID.fromString("a914878e-5a36-11ef-b864-0242ac120002"), "Fired up attack damage", 2, AttributeModifier.Operation.ADDITION);

    private ContinuousSpellComponent spellComponent = new ContinuousSpellComponent.ContinuousSpellComponentBuilder()
            .setDuration(12)
            .addAttribute(attributeModifierSpeed, Attributes.MOVEMENT_SPEED)
            .addAttribute(attributeModifierAttackSpeed, Attributes.ATTACK_SPEED)
            .addAttribute(attributeModifierAttackDamage, Attributes.ATTACK_DAMAGE)
            .build();



    public FiredupSpell()
    {
        this.setName("Firedup");
        this.setDescription("Fires you up");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
