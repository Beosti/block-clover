package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public class ItemSpellComponent extends SpellComponent {

    private Supplier<Item> item;

    public ItemSpellComponent(ItemSpellComponentBuilder itemSpellComponentBuilder)
    {
        this.item = itemSpellComponentBuilder.item;
    }

    public static class ItemSpellComponentBuilder {
        private Supplier<Item> item;

        public ItemSpellComponentBuilder()
        {

        }

        public ItemSpellComponentBuilder setItem(Supplier<Item> item)
        {
            this.item = item;
            return this;
        }

        public ItemSpellComponent build()
        {
            return new ItemSpellComponent(this);
        }
    }

    public Item getItem()
    {
        return this.item.get();
    }
}
