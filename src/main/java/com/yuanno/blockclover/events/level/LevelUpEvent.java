package com.yuanno.blockclover.events.level;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * Custom class for level ups of players, when a player levels up this is called
 */
public class LevelUpEvent extends PlayerEvent {
    public LevelUpEvent(PlayerEntity player) {
        super(player);
    }
}
