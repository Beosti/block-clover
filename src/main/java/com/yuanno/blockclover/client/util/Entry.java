package com.yuanno.blockclover.client.util;

import com.yuanno.blockclover.api.spells.Spell;

import javax.annotation.Nullable;

public class Entry {

    Spell spell;
    private int x;
    private int y;
    private Category category;
    public Entry(@Nullable Spell spell, int x, int y)
    {
        this.spell = spell;
        this.x = x;
        this.y = y;
    }
    public Entry(@Nullable Spell spell, int x, int y, Category category)
    {
        this.spell = spell;
        this.x = x;
        this.y = y;
        this.category = category;
    }

    public Spell getSpell()
    {
        return this.spell;
    }
    public void setSpell(Spell spell)
    {
        this.spell = spell;
    }
    public int getX() {
        return this.x;
    }
    public void setX(int x)
    {
        this.x = x;
    }
    public int getY()
    {
        return this.y;
    }
    public void setY(int y)
    {
        this.y = y;
    }
    public Category getCategory()
    {
        return this.category;
    }
    public void setCategory(Category category)
    {
        this.category = category;
    }
    public enum Category {
        SPELL,
        COMBAT_BAR
    }
}
