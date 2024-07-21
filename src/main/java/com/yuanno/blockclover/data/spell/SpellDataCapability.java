package com.yuanno.blockclover.data.spell;

import com.yuanno.blockclover.api.spells.Spell;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.GameRegistry;

import javax.annotation.Nullable;

public class SpellDataCapability {


    @CapabilityInject(ISpellData.class)
    public static final Capability<ISpellData> INSTANCE = null;

    public static void register()
    {
        CapabilityManager.INSTANCE.register(ISpellData.class, new Capability.IStorage<ISpellData>() {
                    @Nullable
                    @Override
                    public INBT writeNBT(Capability<ISpellData> capability, ISpellData instance, Direction direction) {
                        CompoundNBT props = new CompoundNBT();

                        ListNBT unlockedSpells = new ListNBT();
                        for (int i = 0; i < instance.getUnlockedSpells().size(); i++)
                        {
                            Spell spell = instance.getUnlockedSpells().get(i);
                            CompoundNBT nbtSpell = spell.save();
                            unlockedSpells.add(nbtSpell);
                        }
                        props.put("unlocked_spells", unlockedSpells);

                        ListNBT equippedSpells = new ListNBT();
                        for (int i = 0; i < instance.getEquippedSpells().size(); i++)
                        {
                            Spell spell = instance.getEquippedSpells().get(i);
                            if (spell != null)
                            {
                                CompoundNBT nbtSpell = spell.save();
                                equippedSpells.add(nbtSpell);
                            }
                            else {
                                CompoundNBT spellNBT = new CompoundNBT();
                                equippedSpells.add(spellNBT);
                            }
                        }
                        props.put("equipped_spells", equippedSpells);

                        Spell spell = instance.getPreviousSpellUsed();
                        if (spell != null)
                        {
                            CompoundNBT lastUsedSpellNBT = spell.save();
                            props.put("last_used_spell", lastUsedSpellNBT);
                        }
                        else
                        {
                            CompoundNBT compoundNBT = new CompoundNBT();
                            props.put("last_used_spell", compoundNBT);
                        }


                        return props;
                    }

                    @Override
                    public void readNBT(Capability<ISpellData> capability, ISpellData instance, Direction direction, INBT nbt) {
                        CompoundNBT compoundNBT = (CompoundNBT) nbt;
                        instance.clearUnlockedSpells();

                        ListNBT unlockedSpells = compoundNBT.getList("unlocked_spells", Constants.NBT.TAG_COMPOUND);
                        for (int i = 0; i < unlockedSpells.size(); i++)
                        {
                            CompoundNBT nbtSpell = unlockedSpells.getCompound(i);

                            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(nbtSpell.getString("id")));
                            if (spell == null)
                                continue;
                            spell.load(nbtSpell);
                            Spell spellCheck = instance.getUnlockedSpell(spell);
                            if (spellCheck == null)
                                instance.addUnlockedSpell(spell);
                        }

                        instance.clearEquippedSpells();
                        ListNBT equippedSpells = compoundNBT.getList("equipped_spells", Constants.NBT.TAG_COMPOUND);
                        for (int i = 0; i < equippedSpells.size(); i++)
                        {
                            CompoundNBT nbtSpell = equippedSpells.getCompound(i);

                            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(nbtSpell.getString("id")));
                            if (spell == null) {
                                instance.addEquippedSpell(null);
                            }
                            else
                            {
                                spell.load(nbtSpell);
                                instance.addEquippedSpell(spell);
                            }
                        }

                        instance.clearPreviousSpellUsed();
                        CompoundNBT nbtSpell = compoundNBT.getCompound("last_used_spell");
                        Spell spell = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(nbtSpell.getString("id")));
                        if (spell == null)
                            instance.setPreviousSpellUsed(null);
                        else
                        {
                            spell.load(nbtSpell);
                            instance.setPreviousSpellUsed(spell);
                        }

                    }
                    }, SpellDatabase::new);
    }

    public static ISpellData get(final LivingEntity entity)
    {
        return entity.getCapability(INSTANCE, null).orElse(new SpellDatabase());
    }
}
