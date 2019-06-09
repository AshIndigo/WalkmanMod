package com.ashindigo.walkman;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

public class ContainerWalkman extends Container {

    ContainerWalkman(InventoryPlayer invPlayer, IItemHandlerModifiable walkman) {
        this.addSlotToContainer(new SlotItemHandler(walkman, 0, 80, 18));

        for (int i1 = 0; i1 < 3; ++i1) {
            for (int k1 = 0; k1 < 9; ++k1) {
                this.addSlotToContainer(new Slot(invPlayer, k1 + i1 * 9 + 9, 8 + k1 * 18, 86 + i1 * 18));
            }
        }

        for (int j1 = 0; j1 < 9; ++j1) {
            this.addSlotToContainer(new Slot(invPlayer, j1, 8 + j1 * 18, 144));
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
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
