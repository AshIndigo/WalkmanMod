package com.ashindigo.walkman;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nullable;

public class WalkmanGuiHandler implements IGuiHandler {

    static final int walkmanGui = 0;

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == walkmanGui) {
            return new GuiWalkman(new ContainerWalkman(player.inventory, (IItemHandlerModifiable) player.getHeldItem(EnumHand.values()[x]).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == walkmanGui) {
            return new ContainerWalkman(player.inventory, (IItemHandlerModifiable) player.getHeldItem(EnumHand.values()[x]).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null));
        }
        return null;
    }
}
