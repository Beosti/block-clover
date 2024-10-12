package com.yuanno.blockclover.api.spells.components;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.api.spells.SpellComponent;
import com.yuanno.blockclover.entity.util.CloneEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

import java.io.Serializable;
import java.util.ArrayList;

public class SummoningSpellComponent extends SpellComponent {

    private int amount;
    private int aliveTicks;
    private boolean panicked;
    private boolean targeted;
    private Place place;
    public ISummonSpell summonSpell = (player -> {return null;});
    private ArrayList<Entity> newList = new ArrayList<>();


    public SummoningSpellComponent(SummoningSpellComponentBuilder builder)
    {
        this.amount = builder.amount;
        this.aliveTicks = builder.aliveTicks;
        this.panicked = builder.panicked;
        this.targeted = builder.targeted;
        this.place = builder.place;
        this.summonSpell = builder.iSummonSpell;
    }

    public void summonEntitySummoningSpellComponent(PlayerEntity player, Spell usedSpell)
    {
        for (int i = 0; i < this.amount; i++)
        {
            Entity summonedEntity = summonSpell.getSummoningEntity(player);
            summonedEntity.moveTo(player.getX(), player.getY(), player.getZ(), 180, 0);
            if (summonedEntity instanceof CloneEntity)
            {
                CloneEntity cloneEntity = (CloneEntity) summonedEntity;
                cloneEntity.setOwner(player.getUUID());
                cloneEntity.setLastHurtByMob(player);
                cloneEntity.setMaxAliveTicks(300);
            }
            for(EquipmentSlotType slot : EquipmentSlotType.values())
            {
                ItemStack stack = player.getItemBySlot(slot);
                summonedEntity.setItemSlot(slot, stack);
            }
            player.level.addFreshEntity(summonedEntity);
            newList.add(summonedEntity);
        }
    }

    public void removeSummonEntitySummoningSpellComponent(PlayerEntity player, Spell usedSpell)
    {
        for (int i = 0; i < newList.size(); i++)
        {
            newList.get(i).remove();
        }
        newList.clear();
    }

    public static class SummoningSpellComponentBuilder {
        private int amount;
        private int aliveTicks;
        private boolean panicked;
        private boolean targeted;
        private ISummonSpell iSummonSpell = (player -> {return null;});
        private Place place;

        public SummoningSpellComponentBuilder ()
        {

        }


        public SummoningSpellComponentBuilder summonSpell(ISummonSpell summoningSpell)
        {
            this.iSummonSpell = summoningSpell;
            return this;
        }

        public SummoningSpellComponentBuilder amount(int amount)
        {
            this.amount = amount;
            return this;
        }

        public SummoningSpellComponentBuilder aliveTicks(int amount)
        {
            this.aliveTicks = amount;
            return this;
        }

        public SummoningSpellComponentBuilder panicked(boolean panicked)
        {
            this.panicked = panicked;
            return this;
        }

        public SummoningSpellComponentBuilder targeted(boolean targeted)
        {
            this.targeted = targeted;
            return this;
        }

        public SummoningSpellComponentBuilder place(Place place)
        {
            this.place = place;
            return this;
        }

        public SummoningSpellComponentBuilder summon(ISummonSpell summonSpell)
        {
            this.iSummonSpell = summonSpell;
            return this;
        }

        public SummoningSpellComponent build()
        {
            return new SummoningSpellComponent(this);
        }
    }

    enum Place {
        AROUND,
        FRONT
    }

    public interface ISummonSpell extends Serializable
    {
        Entity getSummoningEntity(PlayerEntity player);
    }
}
