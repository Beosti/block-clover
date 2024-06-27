package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CSyncSpellDataPacket {

    private INBT data;

    public CSyncSpellDataPacket() {}

    public CSyncSpellDataPacket(ISpellData props)
    {
        this.data = new CompoundNBT();
        this.data = SpellDataCapability.INSTANCE.getStorage().writeNBT(SpellDataCapability.INSTANCE, props, null);
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeNbt((CompoundNBT) data);
    }

    public static CSyncSpellDataPacket decode(PacketBuffer buffer)
    {
        CSyncSpellDataPacket msg = new CSyncSpellDataPacket();
        msg.data = buffer.readNbt();
        return msg;
    }

    public static void handle(CSyncSpellDataPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();
                ISpellData props = SpellDataCapability.get(player);

                SpellDataCapability.INSTANCE.getStorage().readNBT(SpellDataCapability.INSTANCE, props, null, message.data);
            });
        }
        ctx.get().setPacketHandled(true);
    }

}