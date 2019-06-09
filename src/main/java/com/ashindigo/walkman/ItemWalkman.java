package com.ashindigo.walkman;

import com.ashindigo.walkman.networking.PacketPlayDisc;
import com.ashindigo.walkman.networking.PacketStopDisc;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.items.CapabilityItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ItemWalkman extends Item {

    ItemWalkman() {
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
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (player.isSneaking()) {
            player.openGui(WalkmanMod.INSTANCE, WalkmanGuiHandler.walkmanGui, world, hand.ordinal(), 0, 0);
        } else {
            if (!Objects.requireNonNull(player.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).isEmpty()) {
                if(!world.isRemote) {
                    WalkmanMod.HANDLER.sendTo(new PacketPlayDisc(Objects.requireNonNull(Objects.requireNonNull(player.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).getItem().getRegistryName()).toString()), (EntityPlayerMP) player);
                }
            } else {
                if(!world.isRemote) {
                    WalkmanMod.HANDLER.sendTo(new PacketStopDisc(), (EntityPlayerMP) player);
                }
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, player.getHeldItem(hand));
    }


    @Override
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flag) {
        // TODO Doesn't actually work when the player is on a server
//        String str = "Nothing";
//        if (!Objects.requireNonNull(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).isEmpty()) {
//            str = "Playing: " + ((ItemRecord) Objects.requireNonNull(stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).getStackInSlot(0).getItem()).getRecordNameLocal();
//        }
//        list.add(str);
    }
}
