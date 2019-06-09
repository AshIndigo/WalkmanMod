package com.ashindigo.walkman;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

public class ItemWalkman extends Item {

    public ItemWalkman() {
        super();
        this.setMaxStackSize(1);
        this.setRegistryName(WalkmanMod.MODID, "walkman");
        this.setUnlocalizedName(WalkmanMod.MODID + "." + "walkman");
        this.setCreativeTab(CreativeTabs.MISC);
        ForgeRegistries.ITEMS.register(this);
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
        return new WalkmanProvider();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            if (!world.isRemote) {
                player.openGui(WalkmanMod.INSTANCE, WalkmanGuiHandler.walkmanGui, world, hand.ordinal(), 0, 0);
            }
        } else {
            if (!Objects.requireNonNull(player.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).isEmpty()) {
                WalkmanMod.proxy.playDisc(player, ((ItemRecord) Objects.requireNonNull(player.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).getItem()));
            } else {
                WalkmanMod.proxy.stopDisc();
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
        String str = "Nothing";
        if (!Objects.requireNonNull(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).isEmpty()) {
            str = "Playing: " + ((ItemRecord) Objects.requireNonNull(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).getItem()).getRecordNameLocal();
        }
        list.add(str);
    }

}
