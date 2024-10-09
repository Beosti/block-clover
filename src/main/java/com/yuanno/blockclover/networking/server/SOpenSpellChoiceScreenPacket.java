package com.yuanno.blockclover.networking.server;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.client.screens.spell.SpellChoiceScreen;
import com.yuanno.blockclover.data.entity.EntityStatsCapability;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.COpenAttributeChoiceScreenPacket;
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

public class SOpenSpellChoiceScreenPacket {

    private ArrayList<Spell> spells = new ArrayList<>();
    private int amount;
    public SOpenSpellChoiceScreenPacket() {}

    public SOpenSpellChoiceScreenPacket(ArrayList<Spell> spells)
    {
        this.spells = spells;
    }
    public SOpenSpellChoiceScreenPacket(ArrayList<Spell> spells, int amount)
    {
        this.spells = spells;
        this.amount = amount;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeInt(this.spells.size());
        for (int i = 0; i < this.spells.size(); i++)
        {
            buffer.writeUtf(this.spells.get(i).getRegistryName().toString());
        }
        buffer.writeInt(amount);
    }

    public static SOpenSpellChoiceScreenPacket decode(PacketBuffer buffer)
    {
        SOpenSpellChoiceScreenPacket msg = new SOpenSpellChoiceScreenPacket();
        int size = buffer.readInt();
        ArrayList<Spell> spells = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            Spell spellRegistry = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(buffer.readUtf()));
            if (spellRegistry != null)
            {
                spells.add(spellRegistry);
            }
        }
        msg.spells = spells;
        msg.amount = buffer.readInt();
        return msg;
    }

    public static void handle(SOpenSpellChoiceScreenPacket message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT)
            ctx.get().enqueueWork(() -> ClientHandler.handle(message));
        ctx.get().setPacketHandled(true);
    }

    public static class ClientHandler
    {
        @OnlyIn(Dist.CLIENT)
        public static void handle(SOpenSpellChoiceScreenPacket message)
        {
            PlayerEntity player = Minecraft.getInstance().player;
            if (message.amount == 0)
                SpellChoiceScreen.open(message.spells);
            else
                SpellChoiceScreen.open(message.spells, message.amount);
        }
    }
}
