package com.ashindigo.walkman;

import com.ashindigo.walkman.networking.PacketPlayDisc;
import com.ashindigo.walkman.networking.PacketStopDisc;
import net.minecraft.block.Blocks;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ObjectHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("walkman")
public class WalkmanMod {

    public static CommonProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> CommonProxy::new);

    static final String MODID = "walkman";

    @ObjectHolder("walkman:walkman")
    public static final ContainerType<ContainerWalkman> walkmanType = null;

    static SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(new ResourceLocation(MODID, MODID), () -> "1", "1"::equals, "1"::equals);

    //public static final WalkmanMod INSTANCE;
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public WalkmanMod() {
        int id = 0;
        HANDLER.registerMessage(id++, PacketPlayDisc.class, PacketPlayDisc::writePacketData, PacketPlayDisc::readPacketData, PacketPlayDisc.PlayPacketHandler::handle);
        HANDLER.registerMessage(id++, PacketStopDisc.class, PacketStopDisc::writePacketData, PacketStopDisc::readPacketData, PacketStopDisc.StopPacketHandler::handle);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(Item.class, WalkmanMod::registerItems);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(ContainerType.class, WalkmanMod::registerContainers);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemWalkman());
}

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        proxy.registerGui();
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    @SubscribeEvent
    public static void registerContainers(RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().register(new ContainerType<>(ContainerWalkman::new).setRegistryName(new ResourceLocation(MODID, MODID)));
    }
}
