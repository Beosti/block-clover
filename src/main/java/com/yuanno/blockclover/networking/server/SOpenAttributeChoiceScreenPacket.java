package com.yuanno.blockclover.networking.server;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.screens.AttributeChoiceScreen;
import com.yuanno.blockclover.client.screens.spell.SpellChoiceScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class SOpenAttributeChoiceScreenPacket {

    public SOpenAttributeChoiceScreenPacket() {}


    public void encode(PacketBuffer buffer)
    {

    }

    public static SOpenAttributeChoiceScreenPacket decode(PacketBuffer buffer)
    {
        SOpenAttributeChoiceScreenPacket msg = new SOpenAttributeChoiceScreenPacket();
        return msg;
    }

    public static void handle(SOpenAttributeChoiceScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            ctx.get().enqueueWork(() -> ClientHandler.handle(message));
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler
    {
        @OnlyIn(Dist.CLIENT)
        public static void handle(SOpenAttributeChoiceScreenPacket message)
        {
            PlayerEntity player = Minecraft.getInstance().player;
            AttributeChoiceScreen.open();
        }
    }
}
