package com.yuanno.blockclover.init;

import com.yuanno.blockclover.networking.PacketHandler;
import com.yuanno.blockclover.networking.client.CSyncEntityStatsPacket;
import com.yuanno.blockclover.networking.server.SSyncEntityStatsDataPacket;

public class ModNetwork {
    
    public static void init()
    {
        PacketHandler.registerPacket(SSyncEntityStatsDataPacket.class, SSyncEntityStatsDataPacket::encode, SSyncEntityStatsDataPacket::decode, SSyncEntityStatsDataPacket::handle);

        PacketHandler.registerPacket(CSyncEntityStatsPacket.class, CSyncEntityStatsPacket::encode, CSyncEntityStatsPacket::decode, CSyncEntityStatsPacket::handle);

    }
}
