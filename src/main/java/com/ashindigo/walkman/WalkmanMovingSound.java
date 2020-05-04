package com.ashindigo.walkman;

import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;

public class WalkmanMovingSound extends TickableSound {
    private final PlayerEntity player;
    private float distance = 0.0F;

    WalkmanMovingSound(PlayerEntity player, SoundEvent event) {
        super(event, SoundCategory.RECORDS);
        this.player = player;
        this.repeat = false;
        this.repeatDelay = 0;
    }

    public void tick() {
        if (!this.player.isAlive()) {
            this.donePlaying = true;
        } else {
            this.x = (float) this.player.getPosX();
            this.y = (float) this.player.getPosY();
            this.z = (float) this.player.getPosZ();
            float f = 1.0f;
            this.distance = MathHelper.clamp(this.distance + 0.0025F, 0.0F, 1.0F);
            this.volume = 0.0F + MathHelper.clamp(f, 0.0F, 0.5F) * 0.7F;
        }
    }
}
