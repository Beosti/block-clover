package com.yuanno.blockclover.util;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import net.minecraft.util.ResourceLocation;

public class BCHelper {

    public static ResourceLocation getResourceLocationSpell(Spell spell)
    {
        String originalResourceLocation = spell.getIDName();
        String formatedResourceLocation = originalResourceLocation.replaceAll(" ", "_").toLowerCase();
        ResourceLocation resourceLocation = new ResourceLocation(Main.MODID, "textures/spell/" + formatedResourceLocation + ".png");
        return resourceLocation;
    }
}
