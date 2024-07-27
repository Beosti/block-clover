package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.SpellComponent;
import net.minecraft.item.Item;

public class ItemSpellComponent extends SpellComponent {

    private Item item;

    public ItemSpellComponent(ItemSpellComponentBuilder itemSpellComponentBuilder)
    {
        this.item = itemSpellComponentBuilder.item;
    }

    public static class ItemSpellComponentBuilder {
        private Item item;

        public ItemSpellComponentBuilder()
        {

        }

        public ItemSpellComponentBuilder setItem(Item item)
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
        return this.item;
    }
}
