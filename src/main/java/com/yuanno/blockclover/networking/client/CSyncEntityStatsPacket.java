package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CSyncEntityStatsPacket {

    private INBT data;

    public CSyncEntityStatsPacket() {}

    public CSyncEntityStatsPacket(IEntityStats props)
    {
        this.data = new CompoundNBT();
        this.data = EntityStatsCapability.INSTANCE.getStorage().writeNBT(EntityStatsCapability.INSTANCE, props, null);
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeNbt((CompoundNBT) data);
    }

    public static CSyncEntityStatsPacket decode(PacketBuffer buffer)
    {
        CSyncEntityStatsPacket msg = new CSyncEntityStatsPacket();
        msg.data = buffer.readNbt();
        return msg;
    }

    public static void handle(CSyncEntityStatsPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if(ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();
                IEntityStats props = EntityStatsCapability.get(player);

                EntityStatsCapability.INSTANCE.getStorage().readNBT(EntityStatsCapability.INSTANCE, props, null, message.data);
            });
        }
        ctx.get().setPacketHandled(true);
    }

}