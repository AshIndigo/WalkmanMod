package com.ashindigo.walkman;

import com.ashindigo.walkman.networking.PacketPlayDisc;
import com.ashindigo.walkman.networking.PacketStopDisc;
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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

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
        if (player.isCrouching()) {
            if (!world.isRemote()) {
                NetworkHooks.openGui((ServerPlayerEntity) player, this);
            }
        } else {
            if (!getHandler(player.getHeldItem(hand)).getStackInSlot(0).isEmpty()) {
                if (!world.isRemote) {
                    WalkmanMod.HANDLER.sendTo(new PacketPlayDisc(getHandler(player.getHeldItem(hand)).getStackInSlot(0)), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
            } else {
                if (!world.isRemote) {
                    WalkmanMod.HANDLER.sendTo(new PacketStopDisc(), ((ServerPlayerEntity)player).connection.getNetworkManager(), NetworkDirection.PLAY_TO_CLIENT);
                }
            }
        }
        return new ActionResult<>(ActionResultType.PASS, player.getHeldItem(hand));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent(this.getTranslationKey());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (!getHandler(stack).getStackInSlot(0).isEmpty()) {
            tooltip.add(new StringTextComponent("Playing: ").appendSibling(((MusicDiscItem) getHandler(stack).getStackInSlot(0).getItem()).getRecordDescription()));
        }
    }

    @Nullable
    @Override
    @ParametersAreNonnullByDefault
    public Container createMenu(int id, PlayerInventory inv, PlayerEntity player) {
        return new ContainerWalkman(id, inv);
    }
}
