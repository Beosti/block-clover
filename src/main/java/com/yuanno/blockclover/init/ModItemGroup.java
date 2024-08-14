package com.yuanno.blockclover.init;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModItemGroup {
    public static final ItemGroup BLOCKCLOVER_WEAPONS = new ItemGroup("blockcloverModTabWeapons") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FIRE_BAT.get());
        }
    };
}
