package com.ashindigo.walkman;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemRecord;

public class ClientProxy extends CommonProxy {

    private static final Minecraft mc = Minecraft.getMinecraft();
    private static WalkmanMovingSound sound;
    private boolean isPlaying;

    public void playDisc(EntityPlayer player, ItemRecord item) {
        if (!isPlaying || !mc.getSoundHandler().isSoundPlaying(sound)) {
            sound = new WalkmanMovingSound(player, item.getSound());
            mc.getSoundHandler().playSound(sound);
            isPlaying = true;
        }
    }

    public void stopDisc() {
        mc.getSoundHandler().stopSound(sound);
        isPlaying = false;
    }
}
