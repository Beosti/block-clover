package com.yuanno.blockclover.api.vanilla;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class VanillaUtil {

    public static void removeItemFromInventory(Item item, PlayerEntity player)
    {
        for (int i = 0; i < player.inventory.getContainerSize(); i++)
        {
            Item itemInInventory = player.inventory.getItem(i).getItem();
            if (itemInInventory.equals(item))
                player.inventory.getItem(i).shrink(1);
        }
    }

    public static void giveItemInMainHand(Item item, PlayerEntity player)
    {
        if (!player.getMainHandItem().isEmpty())
            return;
        player.addItem(new ItemStack(item));
    }

    public static void giveItemInMainHand(Item item, PlayerEntity player, boolean undroppable)
    {
        if (!player.getMainHandItem().isEmpty())
            return;
        ItemStack itemStack = new ItemStack(item);
        if (undroppable)
            itemStack.getOrCreateTag().putBoolean("undroppable", true);
        player.setItemInHand(Hand.MAIN_HAND, itemStack);
    }
}
