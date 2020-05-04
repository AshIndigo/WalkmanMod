package com.ashindigo.walkman;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ContainerWalkman extends Container {

    private ItemStack item;
    private ItemStackHandler walkman;

    ContainerWalkman(int id, PlayerInventory inv) {
        super(WalkmanMod.walkmanType, id);
        item = inv.player.getHeldItemMainhand();
        if (item.getItem() instanceof ItemWalkman) {
            walkman = ItemWalkman.getHandler(item);
        } else {
            item = inv.player.getHeldItemOffhand();
            walkman = ItemWalkman.getHandler(item);
        }

        this.addSlot(new SlotItemHandler(walkman, 0, 80, 18) {
            @Override
            public boolean isItemValid(@Nonnull ItemStack stack) {
                return stack.getItem() instanceof MusicDiscItem;
            }
        });

        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlot(new Slot(inv, k1 + i1 * 9 + 9, 8 + k1 * 18, 86 + i1 * 18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new Slot(inv, j1, 8 + j1 * 18, 144));
        }


    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity player) {
        if (!item.hasTag()) {
            item.setTag(new CompoundNBT());
        }
        if (item.getTag() != null) {
            item.getTag().put("disc", walkman.serializeNBT());
        }
        super.onContainerClosed(player);
    }

    @Override
    @Nonnull
    // Doesnt actually work
    public ItemStack transferStackInSlot(PlayerEntity player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            int containerSlots = inventorySlots.size() - player.inventory.mainInventory.size();

            if (index < containerSlots) {
                if (!this.mergeItemStack(itemstack1, containerSlots, inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, containerSlots, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }
}
