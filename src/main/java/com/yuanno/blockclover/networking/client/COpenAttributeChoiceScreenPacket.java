package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SOpenAttributeChoiceScreenPacket;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class COpenAttributeChoiceScreenPacket {


    public COpenAttributeChoiceScreenPacket() {}

    public void encode(PacketBuffer buffer)
    {

    }

    public static COpenAttributeChoiceScreenPacket decode(PacketBuffer packetBuffer)
    {
        COpenAttributeChoiceScreenPacket msg = new COpenAttributeChoiceScreenPacket();
        return msg;
    }

    public static void handle(COpenAttributeChoiceScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();
                PacketHandler.sendTo(new SOpenAttributeChoiceScreenPacket(), player);

            });
        }
        ctx.get().setPacketHandled(true);
    }
}
