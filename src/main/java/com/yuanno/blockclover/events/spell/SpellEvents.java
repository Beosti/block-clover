package com.yuanno.blockclover.events.spell;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SpellEvents {

    @SubscribeEvent
    public static void onSpellUse(SpellUseEvent event)
    {
        if (!(event.getSpellUser() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSpellUser();
        ISpellData spellData = SpellDataCapability.get(player);
        Spell usedSpell = event.getSpell();

        for (int i = 0; i < usedSpell.getSpellComponents().size(); i++)
        {
            if (usedSpell.getSpellComponents().get(i) instanceof ProjectileSpellComponent)
                ((ProjectileSpellComponent) usedSpell.getSpellComponents().get(i)).shootProjectileSpellComponent(player, usedSpell);
            if (spellData.getPreviousSpellUsed() != null &&  usedSpell.getSpellComponents().get(i) instanceof ComboSpellComponent && spellData.getPreviousSpellUsed().equals(((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).getSpellToCombo())) {
                ((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).combo.comboDoing(player);
            }
        }
    }
}
