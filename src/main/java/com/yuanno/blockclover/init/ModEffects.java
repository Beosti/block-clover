package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.effects.ElectrocutedEffect;
import net.minecraft.potion.Effect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEffects {

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, Main.MODID);
    public static final RegistryObject<Effect> ELECTROCUTED = EFFECTS.register("electrocuted", ElectrocutedEffect::new);


    public static void register(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
}
