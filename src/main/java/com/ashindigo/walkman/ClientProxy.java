package com.ashindigo.walkman;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.util.SoundEvent;

import java.util.Objects;

public class ClientProxy extends CommonProxy {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static WalkmanMovingSound sound;
    private boolean isPlaying;

    public void playDisc(String sound2) {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            if (!isPlaying || !mc.getSoundHandler().isSoundPlaying(sound)) {
                SoundEvent sound3 = ((ItemRecord) Objects.requireNonNull(Item.getByNameOrId(sound2))).getSound();
                sound = new WalkmanMovingSound(mc.player, sound3);
                mc.getSoundHandler().playSound(sound);
                isPlaying = true;
            }
        });
    }

    public void stopDisc() {
        Minecraft.getMinecraft().addScheduledTask(() -> {
            mc.getSoundHandler().stopSound(sound);
            isPlaying = false;
        });
    }
}
