package com.yuanno.blockclover.data.entity;

import net.minecraft.nbt.CompoundNBT;

public class MagicData {
    private String attribute = "";
    private int level = 1;
    private int maxMana = 0;
    private float currentMana = 0;
    private float manaRegen = 0;

    public void setAttribute(String attribute)
    {
        this.attribute = attribute;
    }
    public String getAttribute()
    {
        return this.attribute;
    }

    public void setLevel(int amount)
    {
        this.level = amount;
    }
    public void alterLevel(int amount)
    {
        this.level += amount;
    }
    public int getLevel()
    {
        return this.level;
    }

    public void setMaxMana(int amount)
    {
        this.maxMana = amount;
    }
    public void alterMaxMana(int amount)
    {
        this.maxMana += amount;
    }
    public int getMaxMana()
    {
        return this.maxMana;
    }

    public void setCurrentMana(float amount)
    {
        this.currentMana = amount;
    }
    public void alterCurrentmana(float amount)
    {
        this.currentMana += amount;
    }
    public float getCurrentMana()
    {
        return this.currentMana;
    }

    public void setManaRegen(float amount)
    {
        this.manaRegen = amount;
    }
    public void alterManaRegen(float amount)
    {
        this.manaRegen += amount;
    }
    public float getManaRegen()
    {
        return this.manaRegen;
    }

    public CompoundNBT save()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("attribute", this.getAttribute());
        compoundNBT.putInt("level", this.getLevel());
        compoundNBT.putInt("maxMana", this.getMaxMana());
        compoundNBT.putFloat("currentMana", this.getCurrentMana());
        compoundNBT.putFloat("manaRegen", this.getManaRegen());

        return compoundNBT;
    }

    public void load(CompoundNBT compoundNBT)
    {
        this.attribute = compoundNBT.getString("attribute");
        this.level = compoundNBT.getInt("level");
        this.maxMana = compoundNBT.getInt("maxMana");
        this.currentMana = compoundNBT.getFloat("currentMana");
        this.manaRegen = compoundNBT.getFloat("manaRegen");
    }
}
