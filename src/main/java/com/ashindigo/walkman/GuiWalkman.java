package com.ashindigo.walkman;

import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiWalkman extends ContainerScreen<ContainerWalkman> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(WalkmanMod.MODID, "textures/gui/container/walkman.png");

    public GuiWalkman(ContainerWalkman container, PlayerInventory playerInv, ITextComponent title) {
        super(container, playerInv, title);
    }

    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.blit(i, j, 0, 54, this.xSize, this.ySize);
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.font.drawString(I18n.format("gui.walkman.main"), this.xSize / 2 - this.font.getStringWidth(I18n.format("gui.walkman.main")) / 2, 6, 4210752);
        this.font.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
