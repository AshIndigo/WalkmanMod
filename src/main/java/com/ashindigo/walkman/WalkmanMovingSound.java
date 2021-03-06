package com.ashindigo.walkman;

import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class WalkmanMovingSound extends MovingSound {
    private final EntityPlayer player;
    private float distance = 0.0F;

    WalkmanMovingSound(EntityPlayer player, SoundEvent event) {
        super(event, SoundCategory.RECORDS);
        this.player = player;
        this.repeat = false;
        this.repeatDelay = 0;
    }

    public void update() {
        if (this.player.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.player.posX;
            this.yPosF = (float) this.player.posY;
            this.zPosF = (float) this.player.posZ;
            float f = 1.0f;
            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
            this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 0.5F) * 0.7F;
        }
    }
}
