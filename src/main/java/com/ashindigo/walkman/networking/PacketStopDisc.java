package com.ashindigo.walkman.networking;

import com.ashindigo.walkman.WalkmanMod;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketStopDisc {

    public static PacketStopDisc readPacketData(PacketBuffer buf) {
        return new PacketStopDisc();
    }

    public static void writePacketData(PacketStopDisc packet, PacketBuffer buf) {
    }

    public static class StopPacketHandler {

        public static void handle(PacketStopDisc packet, Supplier<NetworkEvent.Context> ctx) {
            WalkmanMod.proxy.stopDisc();
        }
    }
}


