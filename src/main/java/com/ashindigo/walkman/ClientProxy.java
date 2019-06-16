package com.ashindigo.walkman;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MusicDiscItem;
import net.minecraft.util.SoundEvent;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

    private static final Minecraft mc = Minecraft.getInstance();
    private static WalkmanMovingSound sound;
    private boolean isPlaying;

    public void playDisc(ItemStack stack) {
        Minecraft.getInstance().enqueue(() -> {
            if (!isPlaying || !mc.getSoundHandler().func_215294_c(sound)) { // probably isSoundPlaying
                mc.ingameGUI.setRecordPlayingMessage(((MusicDiscItem) stack.getItem()).getRecordDescription().getFormattedText());
                //mc.field_71456_v.setRecordPlayingMessage(((MusicDiscItem) Objects.requireNonNull(Item.getByNameOrId(sound2))).getRecordNameLocal());
                //SoundEvent sound3 = ((MusicDiscItem) Objects.requireNonNull(Item.getByNameOrId(sound2))).getSound();
                //sound = new WalkmanMovingSound(mc.player, sound3);
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
