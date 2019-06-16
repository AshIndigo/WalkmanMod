//package com.ashindigo.walkman.networking;
//
//import com.ashindigo.walkman.WalkmanMod;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
//import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
//import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
//
//public class PlayPacketHandler implements IMessageHandler<PacketPlayDisc, IMessage> {
//
//    @Override
//    public IMessage onMessage(PacketPlayDisc message, MessageContext ctx) {
//        WalkmanMod.proxy.playDisc(message.soundName);
//        return null;
//    }
//}
