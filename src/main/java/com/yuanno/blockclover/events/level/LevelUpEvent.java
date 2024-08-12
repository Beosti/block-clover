package com.yuanno.blockclover.events.level;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class LevelUpEvent extends PlayerEvent {
    private PlayerEntity player;
    public LevelUpEvent(PlayerEntity player) {
        super(player);
    }
}
