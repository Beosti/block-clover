package com.yuanno.blockclover.items;

import com.yuanno.blockclover.init.ModItemGroup;
import com.yuanno.blockclover.init.ModTiers;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SwordItem;

public class FireBatItem  extends SwordItem {

    public FireBatItem()
    {
        //super(new SwordItem(ModTiers.MAGIC_WEAPON, 6, -2.4f, new Item.Properties().tab(ItemGroup.TAB_COMBAT)));
        super(ModTiers.MAGIC_WEAPON, 6, -2.4f, new Item.Properties().tab(ModItemGroup.BLOCKCLOVER_WEAPONS));
    }
}
