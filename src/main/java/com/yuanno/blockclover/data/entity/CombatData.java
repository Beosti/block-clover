package com.yuanno.blockclover.data.entity;

import net.minecraft.nbt.CompoundNBT;

/**
 * All the date handles:
 * combat mode
 */
public class CombatData {

    private boolean combatMode = false;

    public void setCombatMode(boolean flag)
    {
        this.combatMode = flag;
    }
    public boolean getCombatMode()
    {
        return this.combatMode;
    }

    public CompoundNBT save()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putBoolean("combatMode", this.getCombatMode());
        return compoundNBT;
    }

    public void load(CompoundNBT compoundNBT)
    {
        this.combatMode = compoundNBT.getBoolean("combatMode");
    }

}
