package com.yuanno.blockclover.init;

import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.*;
import com.yuanno.blockclover.networking.server.SOpenAttributeChoiceScreenPacket;
import com.yuanno.blockclover.networking.server.SOpenSpellChoiceScreenPacket;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;
import com.yuanno.blockclover.networking.server.SSyncSpellDataPacket;

public class ModNetwork {
    
    public static void init()
    {
        PacketHandler.registerPacket(SSyncEntityStatsDataPacket.class, SSyncEntityStatsDataPacket::encode, SSyncEntityStatsDataPacket::decode, SSyncEntityStatsDataPacket::handle);
        PacketHandler.registerPacket(SSyncSpellDataPacket.class, SSyncSpellDataPacket::encode, SSyncSpellDataPacket::decode, SSyncSpellDataPacket::handle);
        PacketHandler.registerPacket(SOpenSpellChoiceScreenPacket.class, SOpenSpellChoiceScreenPacket::encode, SOpenSpellChoiceScreenPacket::decode, SOpenSpellChoiceScreenPacket::handle);
        PacketHandler.registerPacket(SOpenAttributeChoiceScreenPacket.class, SOpenAttributeChoiceScreenPacket::encode, SOpenAttributeChoiceScreenPacket::decode, SOpenAttributeChoiceScreenPacket::handle);

        PacketHandler.registerPacket(CSyncEntityStatsPacket.class, CSyncEntityStatsPacket::encode, CSyncEntityStatsPacket::decode, CSyncEntityStatsPacket::handle);
        PacketHandler.registerPacket(CSyncSpellDataPacket.class, CSyncSpellDataPacket::encode, CSyncSpellDataPacket::decode, CSyncSpellDataPacket::handle);
        PacketHandler.registerPacket(CSyncKeyPressedPacket.class, CSyncKeyPressedPacket::encode, CSyncKeyPressedPacket::decode, CSyncKeyPressedPacket::handle);
        PacketHandler.registerPacket(COpenSpellChoiceScreen.class, COpenSpellChoiceScreen::encode, COpenSpellChoiceScreen::decode, COpenSpellChoiceScreen::handle);
        PacketHandler.registerPacket(COpenAttributeChoiceScreenPacket.class, COpenAttributeChoiceScreenPacket::encode, COpenAttributeChoiceScreenPacket::decode, COpenAttributeChoiceScreenPacket::handle);

    }
}
