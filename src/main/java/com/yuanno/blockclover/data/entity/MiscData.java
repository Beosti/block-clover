package com.yuanno.blockclover.data.entity;

import com.yuanno.blockclover.init.ModValues;
import net.minecraft.nbt.CompoundNBT;

/**
 * Boiler plate and a lot of data we want to save and load when needed that are miscellaneous
 * Handles: race, title, rank
 */
public class MiscData {
    private String race = "";
    private String title = "";
    private String rank = "";

    public void setRace(String race)
    {
        this.race = race;
    }
    public String getRace()
    {
        return this.race;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getTitle()
    {
        return this.title;
    }

    public void setRank(String rank)
    {
        this.rank = rank;
    }
    public String getRank()
    {
        return this.rank;
    }

    public CompoundNBT save()
    {
        CompoundNBT compoundNBT = new CompoundNBT();
        compoundNBT.putString("race", this.getRace());
        compoundNBT.putString("title", this.getTitle());
        compoundNBT.putString("rank", this.getRank());

        return compoundNBT;
    }

    public void load(CompoundNBT compoundNBT)
    {
        this.race = compoundNBT.getString("race");
        this.title = compoundNBT.getString("title");
        this.rank = compoundNBT.getString("rank");
    }
}
