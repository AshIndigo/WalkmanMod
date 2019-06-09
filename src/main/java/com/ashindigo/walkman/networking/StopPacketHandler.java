package com.ashindigo.walkman.networking;

import com.ashindigo.walkman.WalkmanMod;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class StopPacketHandler implements IMessageHandler<PacketStopDisc, IMessage> {

    @Override
    public IMessage onMessage(PacketStopDisc message, MessageContext ctx) {
        WalkmanMod.proxy.stopDisc();
        return null;
    }
}
