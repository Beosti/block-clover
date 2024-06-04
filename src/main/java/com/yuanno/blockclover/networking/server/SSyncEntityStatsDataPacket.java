package com.yuanno.blockclover.networking.server;

import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.data.entity.IEntityStats;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SSyncEntityStatsDataPacket {

    private int entityId;
    private INBT data;

    public SSyncEntityStatsDataPacket()
    {
    }

    public SSyncEntityStatsDataPacket(int entityId, IEntityStats props)
    {
        this.data = new CompoundNBT();
        this.data = EntityStatsCapability.INSTANCE.getStorage().writeNBT(EntityStatsCapability.INSTANCE, props, null);
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeNbt((CompoundNBT) this.data);
    }

    public static SSyncEntityStatsDataPacket decode(PacketBuffer buffer)
    {
        SSyncEntityStatsDataPacket msg = new SSyncEntityStatsDataPacket();
        msg.entityId = buffer.readInt();
        msg.data = buffer.readNbt();
        return msg;
    }

    public static void handle(SSyncEntityStatsDataPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
        {
            ctx.get().enqueueWork(() ->
            {
                ClientHandler.handle(message);
            });
        }
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler
    {
        @OnlyIn(Dist.CLIENT)
        public static void handle(SSyncEntityStatsDataPacket message)
        {
            Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
            if (target == null || !(target instanceof LivingEntity))
                return;

            IEntityStats props = EntityStatsCapability.get((LivingEntity) target);
            EntityStatsCapability.INSTANCE.getStorage().readNBT(EntityStatsCapability.INSTANCE, props, null, message.data);

        }
    }
}