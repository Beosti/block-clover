package com.yuanno.blockclover.networking.client;

import com.yuanno.blockclover.api.spells.components.ComboSpellComponent;
import com.yuanno.blockclover.api.spells.components.ProjectileSpellComponent;
import com.yuanno.blockclover.api.spells.Spell;
import com.yuanno.blockclover.data.spell.ISpellData;
import com.yuanno.blockclover.data.spell.SpellDataCapability;
import com.yuanno.blockclover.events.spell.SpellUseEvent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.MinecraftForge;
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
                SpellUseEvent spellUseEvent = new SpellUseEvent(player, usedSpell);
                MinecraftForge.EVENT_BUS.post(spellUseEvent);
            });
        }
        ctx.get().setPacketHandled(true);
    }

}