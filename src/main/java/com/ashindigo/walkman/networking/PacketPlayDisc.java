package com.ashindigo.walkman.networking;

import com.ashindigo.walkman.WalkmanMod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketPlayDisc {

    ItemStack disc;

    public PacketPlayDisc(ItemStack disc) {
        this.disc = disc;
    }

    public static PacketPlayDisc readPacketData(PacketBuffer buf) {
        return new PacketPlayDisc(buf.readItemStack());
    }

    public static void writePacketData(PacketPlayDisc packet, PacketBuffer buf) {
        buf.writeItemStack(packet.disc);
    }

    public static class PlayPacketHandler {

        public static void handle(PacketPlayDisc packet, Supplier<NetworkEvent.Context> ctx) {
            WalkmanMod.proxy.playDisc(packet.disc);
        }
    }
}


