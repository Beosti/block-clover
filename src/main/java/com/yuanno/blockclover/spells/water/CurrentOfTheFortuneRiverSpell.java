package com.yuanno.blockclover.spells.water;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ContinuousSpellComponent;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CurrentOfTheFortuneRiverSpell extends Spell {

    public static CurrentOfTheFortuneRiverSpell INSTANCE = new CurrentOfTheFortuneRiverSpell();

    private ContinuousSpellComponent continuousSpellComponent = new ContinuousSpellComponent.ContinuousSpellComponentBuilder()
            .setDuringContinuity(this::duringSpellContinuity).build();

    public void duringSpellContinuity(PlayerEntity player)
    {
        World world = player.level;
        BlockPos blockPos = player.blockPosition();
        if (world.getBlockState(blockPos.below()).getBlock() == Blocks.WATER && player.getY() - blockPos.below().getY() < 1.1) {
            player.setDeltaMovement(player.getDeltaMovement().x, 0, player.getDeltaMovement().z);
            player.fallDistance = 0;
            player.setOnGround(true);
        }
    }

    public CurrentOfTheFortuneRiverSpell()
    {
        this.setName("Current of The Fortune River");
        this.setDescription("Makes you able to walk on water");
        this.setMaxCooldown(10);
        this.setSpellMaxExperience(3);
        this.addSpellCompontent(continuousSpellComponent);
    }
}
