package com.yuanno.blockclover.events.data;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.data.entity.*;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.data.spell.SpellDatabase;
import com.yuanno.blockclover.init.ModValues;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SOpenAttributeChoiceScreenPacket;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;
import com.yuanno.blockclover.spells.FireballSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class DataEvents {

    /**
     * Data set when joining the world
     * @param event
     */
    @SubscribeEvent
    public static void registerDataEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        if (player.level.isClientSide)
            return;
        IEntityStats entityStats = EntityStatsCapability.get(player);
        ISpellData spellData = SpellDataCapability.get(player);
        if (entityStats.getMiscData() == null) // when no misc data; sets blank slate
        {
            entityStats.setMiscData(new MiscData()); // called server side -> server side data is updated
            handleMiscData(entityStats.getMiscData());
        }
        if (entityStats.getMagicData() == null)
        {
            entityStats.setMagicData(new MagicData());
            handleMagicData(entityStats.getMagicData());
        }
        //if (entityStats.getMagicData().getAttribute().equals(ModValues.FIRE))
        //    spellData.addUnlockedSpell(FireballSpell.INSTANCE);
        if (entityStats.getCombatData() == null)
            entityStats.setCombatData(new CombatData());

        PacketHandler.sendTo(new SSyncSpellDataPacket(player.getId(), spellData), player);
        PacketHandler.sendTo(new SSyncEntityStatsDataPacket(player.getId(), entityStats), player);
        PacketHandler.sendTo(new SOpenAttributeChoiceScreenPacket(), player);
    }

    // handles all the misc data when joining the world for a first time -> race, rank, title
    static void handleMiscData(MiscData miscData)
    {
        Random random = new Random();
        int raceInteger = random.nextInt(10) + 1;
        String race = "";
        if (raceInteger <= 4)
            race = ModValues.HUMAN;
        else if (raceInteger <= 6)
            race = ModValues.ELF;
        else if (raceInteger <= 8)
            race = ModValues.DWARF;
        else
            race = ModValues.HYBRID;
        miscData.setRace(race);
    }

    // handles the magic data
    static void handleMagicData(MagicData magicData)
    {
        magicData.setMaxMana(50);
        magicData.setCurrentMana(50);
        magicData.setLevel(1);
        magicData.setExperience(0);
        magicData.setMaxExperience(50);
        magicData.setManaRegen(1);
    }
}
