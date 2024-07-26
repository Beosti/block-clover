package com.yuanno.blockclover.spells;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.entity.fire.FireballProjectile;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;

import java.util.UUID;

public class ContinuosSpellTest extends Spell {
    public static ContinuosSpellTest INSTANCE = new ContinuosSpellTest();
    public AttributeModifier attributeModifier = new AttributeModifier(UUID.fromString("fbb3619d-2c6d-4c4e-a3b7-202b5346dee4"), "movement speed goes brrrr", 0.2, AttributeModifier.Operation.ADDITION);

    private ContinuousSpellComponent spellComponent = new ContinuousSpellComponent.ContinuousSpellComponentBuilder()
            .setStartContinuity(player -> {System.out.println("started");})
            .setDuringContinuity((player -> {System.out.println("during");}))
            .setEndContinuity((player -> {System.out.println("ending");}))
            .setDuration(12)
            .addAttribute(attributeModifier, Attributes.MOVEMENT_SPEED)
            .build();



    public ContinuosSpellTest()
    {
        this.setName("Continuous");
        this.setDescription("Does stuff");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(spellComponent);
    }


}
