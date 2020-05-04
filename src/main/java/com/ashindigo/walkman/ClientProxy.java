package com.ashindigo.walkman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;

public class ClientProxy extends CommonProxy {

    private static final Minecraft mc = Minecraft.getInstance();
    private static WalkmanMovingSound sound;
    private boolean isPlaying;

    public void playDisc(ItemStack stack) {
        Minecraft.getInstance().enqueue(() -> {
            if (!isPlaying || !mc.getSoundHandler().isPlaying(sound)) {
                mc.ingameGUI.setRecordPlayingMessage(((MusicDiscItem) stack.getItem()).getRecordDescription().getFormattedText());
                sound = new WalkmanMovingSound(mc.player, ((MusicDiscItem) stack.getItem()).getSound());
                mc.getSoundHandler().play(sound);
                isPlaying = true;
            }
        });
    }

    public void stopDisc() {
        Minecraft.getInstance().enqueue(() -> {
            mc.getSoundHandler().stop(sound);
            isPlaying = false;
        });
    }

    public void registerGui() {
        ScreenManager.registerFactory(WalkmanMod.walkmanType, GuiWalkman::new);
    }
}
