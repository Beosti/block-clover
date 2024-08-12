package com.yuanno.blockclover.data.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;


public class EntityStatsCapability {

    @CapabilityInject(IEntityStats.class)
    public static final Capability<IEntityStats> INSTANCE = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(IEntityStats.class, new Capability.IStorage<IEntityStats>() {
            @Override
            public INBT writeNBT(Capability<IEntityStats> capability, IEntityStats instance, Direction side) {
                CompoundNBT props = new CompoundNBT();
                if (instance.getMiscData() != null)
                    props.put("misc", instance.getMiscData().save()); // save misc data if not empty
                if (instance.getCombatData() != null)
                    props.put("combat", instance.getCombatData().save());
                if (instance.getMagicData() != null)
                    props.put("magic", instance.getMagicData().save());
                return props;
            }

            @Override
            public void readNBT(Capability<IEntityStats> capability, IEntityStats instance, Direction side, INBT nbt) {
                CompoundNBT props = (CompoundNBT) nbt;

                if (!props.getCompound("misc").isEmpty())
                {
                    CompoundNBT compoundNBT = props.getCompound("misc"); // retrieve the misc data
                    MiscData miscData = new MiscData(); // we make a new blank slate misc data
                    miscData.load(compoundNBT); // puts the the retrieved misc data INSIDE the blank slate
                    instance.setMiscData(miscData); // puts the filled slate back into the player
                }
                if (!props.getCompound("combat").isEmpty())
                {
                    CompoundNBT compoundNBT = props.getCompound("combat");
                    CombatData combatData = new CombatData();
                    combatData.load(compoundNBT);
                    instance.setCombatData(combatData);
                }
                if (!props.getCompound("magic").isEmpty())
                {
                    CompoundNBT compoundNBT = props.getCompound("magic");
                    MagicData magicData = new MagicData();
                    magicData.load(compoundNBT);
                    instance.setMagicData(magicData);
                }
            }
        }, EntityStatsBase::new);
    }
    public static IEntityStats get(final LivingEntity entity)
    {
        return entity.getCapability(INSTANCE, null).orElse(new EntityStatsBase());
    }
}
