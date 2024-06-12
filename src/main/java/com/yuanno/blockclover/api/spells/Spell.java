package com.yuanno.blockclover.api.spells;

import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class Spell extends ForgeRegistryEntry<Spell> {

    private String name;
    private int currentCooldown;
    private int maxCooldown;
    private STATE state;
    private int experienceGain;

    public Spell()
    {
        this.setState(Spell.STATE.READY);
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
    public void setCurrentCooldown(int amount)
    {
        this.currentCooldown = amount;
    }
    public int getCurrentCooldown()
    {
        return this.currentCooldown;
    }
    public void setMaxCooldown(int amount)
    {
        this.maxCooldown = amount;
    }
    public int getMaxCooldown()
    {
        return this.maxCooldown;
    }
    public void setState(STATE state)
    {
        this.state = state;
    }
    public STATE getState()
    {
        return this.state;
    }
    public void setExperienceGain(int amount)
    {
        this.experienceGain = amount;
    }
    public int getExperienceGain()
    {
        return this.experienceGain;
    }

    public enum STATE {
        COOLDOWN,
        READY,
        CONTINUOUS,
        PASSIVE
    }
    public enum pool {
        DEVIL,
        ATTRIBUTE,
        SPIRIT,
        OTHER
    }

    public CompoundNBT save()
    {
        CompoundNBT compoundNBT = new CompoundNBT();

        compoundNBT.putString("id", this.getRegistryName().toString());
        compoundNBT.putString("displayName", this.getName());
        compoundNBT.putInt("cooldown", this.getCurrentCooldown());
        compoundNBT.putInt("maxCooldown", this.getMaxCooldown());
        compoundNBT.putString("state", this.getState().toString());
        compoundNBT.putInt("experienceGain", this.getExperienceGain());

        return compoundNBT;
    }

    public void load(CompoundNBT compoundNBT)
    {
        this.setName(compoundNBT.getString("display"));
        this.setCurrentCooldown(compoundNBT.getInt("cooldown"));
        this.setMaxCooldown(compoundNBT.getInt("maxCooldown"));
        this.setState(STATE.valueOf(compoundNBT.getString("state")));
        this.setExperienceGain(compoundNBT.getInt("experienceGain"));
    }
}
