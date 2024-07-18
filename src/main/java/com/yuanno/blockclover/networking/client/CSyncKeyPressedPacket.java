package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.api.spells.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.data.spell.SpellDatabase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CSyncKeyPressedPacket {

    private int key;

    public CSyncKeyPressedPacket() {}

    public CSyncKeyPressedPacket(int key)
    {
        this.key = key;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeInt(key);
    }

    public static CSyncKeyPressedPacket decode(PacketBuffer buffer)
    {
        CSyncKeyPressedPacket msg = new CSyncKeyPressedPacket();
        msg.key = buffer.readInt();
        return msg;
    }

    public static void handle(CSyncKeyPressedPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();

                ISpellData spellData = SpellDataCapability.get(player);
                Spell usedSpell = spellData.getEquippedSpells().get(message.key);
                for (int i = 0; i < usedSpell.getSpellComponents().size(); i++)
                {
                    if (usedSpell.getSpellComponents().get(i) instanceof ProjectileSpellComponent)
                        ((ProjectileSpellComponent) usedSpell.getSpellComponents().get(i)).shootProjectileSpellComponent(player);
                }
            });
        }
        ctx.get().setPacketHandled(true);
    }

}