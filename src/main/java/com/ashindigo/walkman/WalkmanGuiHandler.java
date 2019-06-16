package com.ashindigo.walkman;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class WalkmanGuiHandler implements IGuiHandler {

    static final int walkmanGui = 0;

    @Nullable
    @Override
    public Object getClientGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
        if (id == walkmanGui) {
            return new GuiWalkman(new ContainerWalkman(id, player.inventory), player.inventory, new TranslationTextComponent("gui.walkman.main"));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int id, PlayerEntity player, World world, int x, int y, int z) {
        if (id == walkmanGui) {
            return new ContainerWalkman(id, player.inventory);
        }
        return null;
    }
}
