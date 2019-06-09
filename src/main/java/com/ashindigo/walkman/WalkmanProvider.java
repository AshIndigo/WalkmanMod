package com.ashindigo.walkman;

import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class WalkmanProvider implements ICapabilitySerializable<NBTBase> {

    private final IItemHandler inventory = new ItemStackHandler(1) {
        @Nonnull
        @Override
        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
            if (!stack.isEmpty() && stack.getItem() instanceof ItemRecord) {
                return super.insertItem(slot, stack, simulate);
            } else {
                return stack;
            }
        }
    };

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
        } else {
            return null;
        }
    }

    @Override
    public NBTBase serializeNBT() {
        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null);
    }

    @Override
    public void deserializeNBT(NBTBase nbt) {
        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, nbt);
    }
}
