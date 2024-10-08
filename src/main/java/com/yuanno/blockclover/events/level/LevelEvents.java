package com.yuanno.blockclover.events.level;

import com.yuanno.blockclover.Main;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import com.yuanno.blockclover.init.ModValues;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import com.yuanno.blockclover.spells.fire.FiredupSpell;
import com.yuanno.blockclover.spells.fire.FirewaveSpell;
import com.yuanno.blockclover.spells.fire.FirebatSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class LevelEvents {

    @SubscribeEvent
    public static void onLevelUp(LevelUpEvent event)
    {
        PlayerEntity player = event.getPlayer();
        IEntityStats entityStats = EntityStatsCapability.get(player);

        for(Map.Entry<String, HashMap<Integer, ArrayList<Spell>>> entry : spellGainHashMap.entrySet())
        {
            if (!entry.getKey().equals(entityStats.getMagicData().getAttribute()))
                continue;
            for (Map.Entry<Integer, ArrayList<Spell>> entryAttribute : entry.getValue().entrySet())
            {
                if (entityStats.getMagicData().getLevel() != entryAttribute.getKey())
                    continue;
                PacketHandler.sendTo(new SOpenSpellChoiceScreenPacket(entryAttribute.getValue()), player);
            }
        }
    }

    private static final HashMap<Integer, ArrayList<Spell>> fireHashmap = new HashMap<>();
    static {
        ArrayList<Spell> level5 = new ArrayList<>();
        level5.add(FirebatSpell.INSTANCE);
        level5.add(FiredupSpell.INSTANCE);
        level5.add(FirewaveSpell.INSTANCE);
        fireHashmap.put(5, level5);
    }

    private static final HashMap<String, HashMap<Integer, ArrayList<Spell>>> spellGainHashMap= new HashMap<>();
    static {
        spellGainHashMap.put(ModValues.FIRE, fireHashmap);
    }

}
