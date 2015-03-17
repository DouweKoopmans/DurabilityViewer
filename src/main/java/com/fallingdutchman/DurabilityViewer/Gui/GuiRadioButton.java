package com.fallingdutchman.DurabilityViewer.Gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import static org.lwjgl.opengl.GL11.glColor4f;

public class GuiRadioButton extends GuiButton
{
    private static final ResourceLocation RADIOBUTTON = new ResourceLocation("durabilityviewer", "textures/gui/RadioBox.png");
    public boolean checked;

    public GuiRadioButton(int control, int xPos, int yPos, String DisplayString)
    {
        super(control, xPos, yPos, Minecraft.getMinecraft().fontRenderer.getStringWidth(DisplayString) + 16, 12, DisplayString);
    }

    @Override
    public void drawButton(Minecraft minecraft, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            minecraft.getTextureManager().bindTexture(RADIOBUTTON);
            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            this.func_146110_a(this.xPosition, this.yPosition, this.checked ? 16 : 0, 0, 12, 12, 32, 16);
            this.mouseDragged(minecraft, mouseX, mouseY);

            int colour = 0xE0E0E0;
            if (!this.enabled) colour = 0xA0A0A0;
            else if (this.field_146123_n) colour = 0xFFFFA0;

            this.drawString(minecraft.fontRenderer, this.displayString, this.xPosition + 16, this.yPosition + 2, colour);
        }
    }
}
