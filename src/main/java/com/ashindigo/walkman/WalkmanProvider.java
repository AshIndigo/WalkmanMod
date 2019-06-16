//package com.ashindigo.walkman;
//
//import net.minecraft.item.ItemStack;
//import net.minecraft.item.MusicDiscItem;
//import net.minecraft.nbt.INBT;
//import net.minecraft.util.Direction;
//import net.minecraftforge.common.capabilities.ICapabilitySerializable;
//import net.minecraftforge.common.util.LazyOptional;
//import net.minecraftforge.items.CapabilityItemHandler;
//import net.minecraftforge.items.IItemHandler;
//import net.minecraftforge.items.ItemStackHandler;
//
//import javax.annotation.Nonnull;
//
//public class WalkmanProvider implements ICapabilitySerializable<INBT> {
//
//    private LazyOptional<ItemStackHandler> handler;
//
//    private final IItemHandler inventory = new ItemStackHandler(1) {
//        @Nonnull
//        @Override
//        public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
//            if (!stack.isEmpty() && stack.getItem() instanceof MusicDiscItem) {
//                return super.insertItem(slot, stack, simulate);
//            } else {
//                return stack;
//            }
//        }
//    };
//
//    @Override
//    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(net.minecraftforge.common.capabilities.Capability<T> cap, Direction side) {
//        if (cap == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
//            if (this.handler == null) {
//                this.handler = net.minecraftforge.common.util.LazyOptional.of(this::createHandler);
//            }
//            return this.handler.cast();
//        }
//        return LazyOptional.empty();
//    }
//
//    private <T> T createHandler() {
//
//    }
//
//    @Override
//    public INBT serializeNBT() {
//        return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(inventory, null);
//    }
//
//    @Override
//    public void deserializeNBT(INBT nbt) {
//        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(inventory, null, nbt);
//    }
//}
