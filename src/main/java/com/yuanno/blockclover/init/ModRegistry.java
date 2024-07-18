package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.*;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class ModRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, Main.MODID);
    static
    {
        make(new ResourceLocation(Main.MODID, "spells"), Spell.class);
    }

    public static final IForgeRegistry<Spell> SPELLS = RegistryManager.ACTIVE.getRegistry(Spell.class);

    public static <T extends IForgeRegistryEntry<T>> void make(ResourceLocation name, Class<T> type)
    {
        new RegistryBuilder<T>().setName(name).setType(type).setMaxID(Integer.MAX_VALUE - 1).create();
    }
}
