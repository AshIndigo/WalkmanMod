package com.ashindigo.walkman;

import com.ashindigo.walkman.networking.PacketPlayDisc;
import com.ashindigo.walkman.networking.PacketStopDisc;
import com.ashindigo.walkman.networking.PlayPacketHandler;
import com.ashindigo.walkman.networking.StopPacketHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = WalkmanMod.MODID)
@Mod(modid = WalkmanMod.MODID, name = WalkmanMod.NAME, version = WalkmanMod.VERSION)
public class WalkmanMod {

    static final String MODID = "walkman";
    static final String NAME = "Walkman";
    static final String VERSION = "1.0";

    @SidedProxy(modId = MODID, clientSide = "com.ashindigo.walkman.ClientProxy", serverSide = "com.ashindigo.walkman.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static WalkmanMod INSTANCE;

    private static ItemWalkman walkman;

    static final SimpleNetworkWrapper HANDLER = new SimpleNetworkWrapper(MODID);
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(walkman, 0, new ModelResourceLocation(Objects.requireNonNull(walkman.getRegistryName()), "inventory"));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        HANDLER.registerMessage(PlayPacketHandler.class, PacketPlayDisc.class, 0, Side.CLIENT);
        HANDLER.registerMessage(StopPacketHandler.class, PacketStopDisc.class, 1, Side.CLIENT);
        walkman = new ItemWalkman();
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new WalkmanGuiHandler());
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }
}
