package com.yuanno.blockclover.data.spell;

import com.yuanno.blockclover.api.spells.Spell;
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
import java.util.List;

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
                            CompoundNBT nbtSpell = spell.save();
                            equippedSpells.add(nbtSpell);
                        }
                        props.put("equipped_spells", equippedSpells);
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

                        ListNBT equippedSpells = compoundNBT.getList("equipped_spells", Constants.NBT.TAG_COMPOUND);
                        for (int i = 0; i < equippedSpells.size(); i++)
                        {
                            CompoundNBT nbtSpell = equippedSpells.getCompound(i);

                            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(nbtSpell.getString("id")));
                            if (spell == null)
                                continue;
                            spell.load(nbtSpell);
                            Spell spellCheck = instance.getEquippedSpell(spell);
                            if (spellCheck == null)
                                instance.addEquippedSpell(spell);
                        }
                    }
                }, SpellDatabase::new);
    }
}
