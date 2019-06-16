//package com.ashindigo.walkman.networking;
//
//import io.netty.buffer.ByteBuf;
//import net.minecraftforge.fml.common.network.ByteBufUtils;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//
//public class PacketPlayDisc implements IMessage {
//
//    String soundName;
//
//    public PacketPlayDisc() {
//
//    }
//
//    public PacketPlayDisc(String soundName) {
//        this.soundName = soundName;
//    }
//
//    @Override
//    public void fromBytes(ByteBuf buf) {
//        soundName = ByteBufUtils.readUTF8String(buf);
//    }
//
//    @Override
//    public void toBytes(ByteBuf buf) {
//        ByteBufUtils.writeUTF8String(buf, soundName);
//    }
//}
