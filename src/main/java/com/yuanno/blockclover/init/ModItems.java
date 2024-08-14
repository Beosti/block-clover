package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.items.FireBatItem;
import net.minecraft.item.Item;
import net.minecraft.util.RegistryKey;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);
    public List<Supplier<Item>> items = new ArrayList<>();

    // WEAPONS
    public static final RegistryObject<Item> FIRE_BAT = ITEMS.register("firebat", FireBatItem::new);
}
