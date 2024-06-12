package com.yuanno.blockclover.data.spell;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class SpellDataProvider implements ICapabilitySerializable<CompoundNBT> {

    private ISpellData instance = SpellDataCapability.INSTANCE.getDefaultInstance();

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
    {
        return SpellDataCapability.INSTANCE.orEmpty(cap, LazyOptional.of(() -> instance));
    }

    @Override
    public CompoundNBT serializeNBT()
    {
        return (CompoundNBT) SpellDataCapability.INSTANCE.getStorage().writeNBT(SpellDataCapability.INSTANCE, instance, null);
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        SpellDataCapability.INSTANCE.getStorage().readNBT(SpellDataCapability.INSTANCE, instance, null, nbt);
    }
}
