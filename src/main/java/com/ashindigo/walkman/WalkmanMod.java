package com.ashindigo.walkman;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

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

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(walkman, 0, new ModelResourceLocation(Objects.requireNonNull(walkman.getRegistryName()), "inventory"));
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        walkman = new ItemWalkman();
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new WalkmanGuiHandler());
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }
}
