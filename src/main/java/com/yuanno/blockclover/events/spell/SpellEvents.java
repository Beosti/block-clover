package com.yuanno.blockclover.events.spell;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.Beapi;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.UtilSpell;
import com.yuanno.blockclover.api.spells.components.*;
import com.yuanno.blockclover.api.vanilla.VanillaUtil;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.events.level.LevelUpEvent;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.geom.Area;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class SpellEvents {

    @SubscribeEvent
    public static void onSpellUse(SpellUseEvent event)
    {
        if (!(event.getSpellUser() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSpellUser();
        IEntityStats entityStats = EntityStatsCapability.get(player);
        ISpellData spellData = SpellDataCapability.get(player);
        Spell usedSpell = event.getSpell();

        if (usedSpell.getManaCost() > entityStats.getMagicData().getCurrentMana()) {
            event.setCanceled(true);
            return;
        }
        for (int i = 0; i < usedSpell.getSpellComponents().size(); i++)
        {
            if (usedSpell.getSpellComponents().get(i) instanceof ProjectileSpellComponent)
                ((ProjectileSpellComponent) usedSpell.getSpellComponents().get(i)).shootProjectileSpellComponent(player, usedSpell);
            if (usedSpell.getSpellComponents().get(i) instanceof AreaofeffectSpellComponent)
            {
                AreaofeffectSpellComponent areaofeffectSpellComponent = (AreaofeffectSpellComponent) usedSpell.getSpellComponents().get(i);
                areaofeffectSpellComponent.areaofeffectComponent(player);
            }
            if (spellData.getPreviousSpellUsed() != null &&  usedSpell.getSpellComponents().get(i) instanceof ComboSpellComponent && spellData.getPreviousSpellUsed().equals(((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).getSpellToCombo()))
                ((ComboSpellComponent) usedSpell.getSpellComponents().get(i)).combo.comboDoing(player);
            if (usedSpell.getSpellComponents().get(i) instanceof ContinuousSpellComponent && usedSpell.getState().equals(Spell.STATE.CONTINUOUS)) {
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).startContinuitySpell.startContinuity(player);
                HashMap<AttributeModifier, Attribute> attributeHashMap = ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).getAttributeHashMap();
                for (Map.Entry<AttributeModifier, Attribute> entry : attributeHashMap.entrySet()) {
                    AttributeModifier key = entry.getKey();
                    Attribute value = entry.getValue();
                    player.getAttribute(value).addTransientModifier(key);
                }
            }
            if (usedSpell.getSpellComponents().get(i) instanceof ContinuousSpellComponent && usedSpell.getState().equals(Spell.STATE.COOLDOWN)) {
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).endContinuitySpell.endContinuity(player);
                ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).setCurrentDuration(((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).getMaxDuration());
                HashMap<AttributeModifier, Attribute> attributeHashMap = ((ContinuousSpellComponent) usedSpell.getSpellComponents().get(i)).getAttributeHashMap();
                for (Map.Entry<AttributeModifier, Attribute> entry : attributeHashMap.entrySet()) {
                    AttributeModifier key = entry.getKey();
                    Attribute value = entry.getValue();
                    player.getAttribute(value).removeModifier(key);
                }
            }
            if (usedSpell.getSpellComponents().get(i) instanceof ItemSpellComponent && usedSpell.getState().equals(Spell.STATE.CONTINUOUS))
            {
                ItemStack itemStack = new ItemStack(((ItemSpellComponent) usedSpell.getSpellComponents().get(i)).getItem());
                itemStack.getOrCreateTag().putBoolean("undroppable", true);
                VanillaUtil.giveItemInMainHand(((ItemSpellComponent) usedSpell.getSpellComponents().get(i)).getItem(), player, true);
            }
            if (usedSpell.getSpellComponents().get(i) instanceof ItemSpellComponent && usedSpell.getState().equals(Spell.STATE.COOLDOWN))
            {
                VanillaUtil.removeItemFromInventory(((ItemSpellComponent) usedSpell.getSpellComponents().get(i)).getItem(), player);
            }
        }
        entityStats.getMagicData().alterCurrentmana(-usedSpell.getManaCost());
        entityStats.getMagicData().alterExperience(usedSpell.getExperienceGain());
        if (entityStats.getMagicData().getExperience() >= entityStats.getMagicData().getMaxExperience())
        {
            entityStats.getMagicData().alterLevel(1);
            entityStats.getMagicData().alterMaxExperience(50);
            entityStats.getMagicData().setExperience(0);
            entityStats.getMagicData().alterMaxMana(50);
            LevelUpEvent levelUpEvent = new LevelUpEvent(player);
            MinecraftForge.EVENT_BUS.post(levelUpEvent);
        }
        PacketHandler.sendTo(new SSyncEntityStatsDataPacket(player.getId(), entityStats), player);
    }

    @SubscribeEvent
    public static void onItemDrop(ItemTossEvent event)
    {
        ItemStack itemStack = event.getEntityItem().getItem();
        if (itemStack.getOrCreateTag().getBoolean("undroppable")) {
            PlayerEntity player = event.getPlayer();
            event.setCanceled(true);
            ISpellData spellData = SpellDataCapability.get(event.getPlayer());
            for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
            {
                Spell currentSpell = spellData.getEquippedSpells().get(i);
                if (currentSpell == null)
                    continue;
                if (!(currentSpell.getState().equals(Spell.STATE.CONTINUOUS)))
                    continue;
                if (!(UtilSpell.hasComponent(currentSpell, ItemSpellComponent.class)))
                    continue;
                ItemSpellComponent itemSpellComponent = (ItemSpellComponent) UtilSpell.getComponent(currentSpell, ItemSpellComponent.class);
                if (!(event.getEntityItem().getItem().getItem().equals(itemSpellComponent.getItem())))
                    continue;
                UtilSpell.usedSpell(currentSpell);
                UtilSpell.usedSpell(currentSpell);
                SpellUseEvent spellUseEvent = new SpellUseEvent(player, spellData.getEquippedSpells().get(i));
                MinecraftForge.EVENT_BUS.post(spellUseEvent);
                PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
            }
        }
    }


    @SubscribeEvent
    public static void onPunchSpell(LivingDamageEvent event)
    {
        if (!(event.getSource().getEntity() instanceof PlayerEntity))
            return;
        PlayerEntity player = (PlayerEntity) event.getSource().getEntity();
        ISpellData spellData = SpellDataCapability.get(player);
        for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
        {
            Spell currentSpell = spellData.getEquippedSpells().get(i);
            if (currentSpell == null)
                continue;
            if (!(UtilSpell.hasComponent(currentSpell, PunchSpellComponent.class)))
                continue;
            if (!(currentSpell.getState().equals(Spell.STATE.CONTINUOUS)))
                continue;
            PunchSpellComponent punchSpellComponent = (PunchSpellComponent) UtilSpell.getComponent(currentSpell, PunchSpellComponent.class);
            LivingEntity target = event.getEntityLiving();
            punchSpellComponent.getiPunch().punch(player, target);
            if (punchSpellComponent.endPunch())
            {
                UtilSpell.usedSpell(currentSpell);
                SpellUseEvent spellUseEvent = new SpellUseEvent(player, spellData.getEquippedSpells().get(i));
                MinecraftForge.EVENT_BUS.post(spellUseEvent);
                PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
            }
        }
    }

    @SubscribeEvent
    public static void onCurrentContinuousSpell(TickEvent.PlayerTickEvent event)
    {
        if (!(event.player.tickCount % 20 == 0))
            return;
        PlayerEntity player = event.player;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        if (entityStats.getMagicData().getMaxMana() > entityStats.getMagicData().getCurrentMana())
            entityStats.getMagicData().alterCurrentmana(entityStats.getMagicData().getManaRegen());
        ISpellData spellData = SpellDataCapability.get(event.player);
        for (int i = 0; i < spellData.getEquippedSpells().size(); i++)
        {
            if (spellData.getEquippedSpells().get(i) == null)
                continue;
            if (!(spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.CONTINUOUS) || spellData.getEquippedSpells().get(i).getState().equals(Spell.STATE.PASSIVE)))
                continue;
            if (UtilSpell.hasComponent(spellData.getEquippedSpells().get(i), ContinuousSpellComponent.class))
            {
                ContinuousSpellComponent continuousSpellComponent = (ContinuousSpellComponent) UtilSpell.getComponent(spellData.getEquippedSpells().get(i), ContinuousSpellComponent.class);
                continuousSpellComponent.duringContinuitySpell.duringContinuity(event.player);
                if (continuousSpellComponent.getCurrentDuration() == -1)
                    continue;
                else if (continuousSpellComponent.getCurrentDuration() > 0)
                    continuousSpellComponent.alterCurrentDuration(-1);
                else if (continuousSpellComponent.getCurrentDuration() == 0)
                {
                    UtilSpell.usedSpell(spellData.getEquippedSpells().get(i));
                    SpellUseEvent spellUseEvent = new SpellUseEvent(event.player, spellData.getEquippedSpells().get(i));
                    MinecraftForge.EVENT_BUS.post(spellUseEvent);
                    PacketHandler.sendTo(new SSyncSpellDataPacket(event.player.getId(), spellData), event.player);
                }
            }
        }
    }
}
