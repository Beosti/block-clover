package com.yuanno.blockclover.networking.server;

import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
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

public class SSyncSpellDataPacket {

    private int entityId;
    private INBT data;

    public SSyncSpellDataPacket()
    {
    }

    public SSyncSpellDataPacket(int entityId, ISpellData props)
    {
        this.data = new CompoundNBT();
        this.data = SpellDataCapability.INSTANCE.getStorage().writeNBT(SpellDataCapability.INSTANCE, props, null);
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeInt(this.entityId);
        buffer.writeNbt((CompoundNBT) this.data);
    }

    public static SSyncSpellDataPacket decode(PacketBuffer buffer)
    {
        SSyncSpellDataPacket msg = new SSyncSpellDataPacket();
        msg.entityId = buffer.readInt();
        msg.data = buffer.readNbt();
        return msg;
    }

    public static void handle(SSyncSpellDataPacket message, final Supplier<NetworkEvent.Context> ctx)
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
        public static void handle(SSyncSpellDataPacket message)
        {
            Entity target = Minecraft.getInstance().level.getEntity(message.entityId);
            if (target == null || !(target instanceof LivingEntity))
                return;

            ISpellData props = SpellDataCapability.get((LivingEntity) target);
            SpellDataCapability.INSTANCE.getStorage().readNBT(SpellDataCapability.INSTANCE, props, null, message.data);
        }
    }
}