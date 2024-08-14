package com.yuanno.blockclover.api.spells;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;


public class Spell extends ForgeRegistryEntry<Spell> {

    private ArrayList<SpellComponent> spellComponents = new ArrayList<>();
    private String name;
    private String description;
    private int currentCooldown;
    private int maxCooldown;
    private STATE state;
    private int experienceGain;
    private int spellMaxExperience;
    private int spellExperience;
    private int spellLevel;
    private int manaCost = 0;

    public Spell()
    {
        this.setState(Spell.STATE.READY);
        this.setSpellLevel(1);
        this.setSpellExperience(0);
    }

    public void addSpellCompontent(SpellComponent spellComponent)
    {
        this.spellComponents.add(spellComponent);
    }
    public ArrayList<SpellComponent> getSpellComponents()
    {
        return this.spellComponents;
    }

    public String getIDName()
    {
        return this.name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    public TranslationTextComponent getName()
    {
        return new TranslationTextComponent("blockclover.spells." + this.name.toLowerCase() + ".name");
    }
    public void setDescription(String description)
    {
        this.description = description;
    }
    public TranslationTextComponent getDescription()
    {
        return new TranslationTextComponent("blockclover.spells." + this.name.toLowerCase() + ".description");
    }
    public void setCurrentCooldown(int amount)
    {
        this.currentCooldown = amount;
    }
    public void alterCurrentCooldown(int amount)
    {
        this.currentCooldown += amount;
    }

    public void setSpellMaxExperience(int maxExperience)
    {
        this.spellMaxExperience = maxExperience;
    }
    public void alterSpellMaxExperience(int amount)
    {
        this.spellMaxExperience += amount;
    }
    public int getSpellMaxExperience()
    {
        return this.spellMaxExperience;
    }

    public void setSpellExperience(int experience)
    {
        this.spellExperience = experience;
    }
    public void alterSpellExperience(int amount)
    {
        this.spellExperience += amount;
    }
    public int getSpellExperience()
    {
        return this.spellExperience;
    }

    public void setSpellLevel(int level)
    {
        this.spellLevel = level;
    }
    public void alterSpellLevel(int amount)
    {
        this.spellLevel += amount;
    }
    public int getSpellLevel()
    {
        return this.spellLevel;
    }

    public void setManaCost(int amount)
    {
        this.manaCost = amount;
    }
    public void alterManaCost(int amount)
    {
        this.manaCost += amount;
    }
    public int getManaCost()
    {
        return this.manaCost;
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
        compoundNBT.putString("displayName", this.getIDName().toString());
        compoundNBT.putString("description", this.getDescription().toString());
        compoundNBT.putInt("cooldown", this.getCurrentCooldown());
        compoundNBT.putInt("maxCooldown", this.getMaxCooldown());
        compoundNBT.putString("state", this.getState().toString());
        compoundNBT.putInt("experienceGain", this.getExperienceGain());
        compoundNBT.putInt("maxSpellExperience", this.getSpellMaxExperience());
        compoundNBT.putInt("spellExperience", this.getSpellExperience());
        compoundNBT.putInt("spellLevel", this.getSpellLevel());
        compoundNBT.putInt("manaCost", this.getManaCost());
        return compoundNBT;
    }

    public void load(CompoundNBT compoundNBT)
    {
        this.setName(compoundNBT.getString("displayName"));
        this.setDescription(compoundNBT.getString("description"));
        this.setCurrentCooldown(compoundNBT.getInt("cooldown"));
        this.setMaxCooldown(compoundNBT.getInt("maxCooldown"));
        this.setState(STATE.valueOf(compoundNBT.getString("state")));
        this.setExperienceGain(compoundNBT.getInt("experienceGain"));
        this.setSpellMaxExperience(compoundNBT.getInt("maxSpellExperience"));
        this.setSpellExperience(compoundNBT.getInt("spellExperience"));
        this.setSpellLevel(compoundNBT.getInt("spellLevel"));
        this.setManaCost(compoundNBT.getInt("manaCost"));
    }
}
