package com.ashindigo.walkman;

import com.ashindigo.walkman.networking.PacketPlayDisc;
import com.ashindigo.walkman.networking.PacketStopDisc;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ObjectHolder;

@Mod("walkman")
public class WalkmanMod {

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    static final String MODID = "walkman";

    @ObjectHolder("walkman:walkman")
    public static final ContainerType<ContainerWalkman> walkmanType = null;

    static SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> "1", "1"::equals, "1"::equals);

    public WalkmanMod() {
        int id = 0;
        HANDLER.registerMessage(id++, PacketPlayDisc.class, PacketPlayDisc::writePacketData, PacketPlayDisc::readPacketData, PacketPlayDisc.PlayPacketHandler::handle);
        HANDLER.registerMessage(id++, PacketStopDisc.class, PacketStopDisc::writePacketData, PacketStopDisc::readPacketData, PacketStopDisc.StopPacketHandler::handle);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, WalkmanMod::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, WalkmanMod::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemWalkman());
}

    private void doClientStuff(final FMLClientSetupEvent event) {
        proxy.registerGui();
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(new ContainerType<>(ContainerWalkman::new).setRegistryName(new ResourceLocation(MODID, MODID)));
    }
}
