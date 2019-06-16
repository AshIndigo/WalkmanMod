package com.ashindigo.walkman;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Objects;

public class ItemWalkman extends Item implements INamedContainerProvider {

    public ItemWalkman() {
        super(new Properties().maxStackSize(1).group(ItemGroup.MISC));
        this.setRegistryName(WalkmanMod.MODID, "walkman");
        ForgeRegistries.ITEMS.register(this);
    }

    public static ItemStackHandler getHandler(ItemStack stack) {
        if (stack.isEmpty()) {
            return null;
        }
        ItemStackHandler handler = new ItemStackHandler(1);
        if (stack.hasTag()) {
            handler.deserializeNBT(stack.getTag().getCompound("disc"));
        }

        return handler;
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            if (!world.isRemote()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, this);
            }
        } else {
            if (!getHandler(player.getHeldItem(hand)).getStackInSlot(0).isEmpty()) {
                if (!world.isRemote) {
                    WalkmanMod.proxy.playDisc(getHandler(player.getHeldItem(hand)).getStackInSlot(0));
                    //player.playSound();
                    //WalkmanMod.HANDLER.sendTo(new PacketPlayDisc(Objects.requireNonNull(Objects.requireNonNull(player.getHeldItem(hand).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)).orElse(new ItemStackHandler(1)).getStackInSlot(0).getItem().getRegistryName()).toString()), (ServerPlayerEntity) player);
                }
            } else {
                if (!world.isRemote) {
                    WalkmanMod.proxy.stopDisc();
                    WalkmanMod.HANDLER.send(PacketDistributor.PacketTarget);
                    //WalkmanMod.HANDLER.send
                    //WalkmanMod.HANDLER.sendTo(new PacketStopDisc(), (ServerPlayerEntity) player);
                }
            }
        }
        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new StringTextComponent("Playing: ").appendSibling(new TranslationTextComponent(getHandler(stack).getStackInSlot(0).getTranslationKey())));
    }

    @Nullable
    @Override
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
        return new ContainerWalkman(id, inv);
    }
}
