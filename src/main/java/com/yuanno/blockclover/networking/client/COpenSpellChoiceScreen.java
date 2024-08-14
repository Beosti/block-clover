package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.ArrayList;
import java.util.function.Supplier;

public class COpenSpellChoiceScreen {

    private ArrayList<Spell> spells = new ArrayList<>();

    public COpenSpellChoiceScreen() {}

    public COpenSpellChoiceScreen(ArrayList<Spell> spells)
    {
        this.spells = spells;
    }

    public void encode(PacketBuffer buffer)
    {
        buffer.writeInt(this.spells.size());
        for (int i = 0; i < this.spells.size(); i++)
        {
            buffer.writeUtf(this.spells.get(i).getRegistryName().toString());
        }
    }

    public static COpenSpellChoiceScreen decode(PacketBuffer packetBuffer)
    {
        COpenSpellChoiceScreen msg = new COpenSpellChoiceScreen();
        int size = packetBuffer.readInt();
        ArrayList<Spell> spells = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            Spell spell = GameRegistry.findRegistry(Spell.class).getValue(new ResourceLocation(packetBuffer.readUtf()));
            if (spell != null)
            {
                spells.add(spell);
            }
        }
        msg.spells = spells;
        return msg;
    }

    public static void handle(COpenSpellChoiceScreen message, final Supplier<NetworkEvent.Context> ctx)
    {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_SERVER)
        {
            ctx.get().enqueueWork(() ->
            {
                PlayerEntity player = ctx.get().getSender();
                PacketHandler.sendTo(new SOpenSpellChoiceScreenPacket(message.spells), player);
            });
        }
        ctx.get().setPacketHandled(true);
    }
}
