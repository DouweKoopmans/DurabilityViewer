package com.fallingdutchman.DurabilityViewer.Gui.ColourPicker;

import static org.lwjgl.opengl.GL11.glColor4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class GuiColouredButton extends GuiControl
{
    private int colour;
    private GuiColourPicker picker;
    private boolean pickerClicked = false;
    private String displayText;
    private FontRenderer fr;

    public GuiColouredButton(Minecraft minecraft, int controlId, int xPos, int yPos, int controlWidth, int controlHeight,int colour ,String displayText)
    {
        super(minecraft, controlId, xPos, yPos, controlWidth, controlHeight,displayText);
        this.colour = colour;
        this.displayText = displayText;
        this.fr = Minecraft.getMinecraft().fontRenderer;
    }

    public int getColour()
    {
        return this.colour;
    }

    @Override
    public void drawControl(Minecraft minecraft, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            boolean ButtonmouseOver = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int borderColour = ButtonmouseOver || this.picker != null ? 0xFFFFFFFF : 0xFFA0A0A0;

            drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, borderColour);

            glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            drawRect(this.xPosition + 1, this.yPosition + 1, this.xPosition + this.width - 1, this.yPosition + this.height - 1, 0xFF000000 | this.colour);

            super.mouseDragged(minecraft, mouseX, mouseY);

            boolean StringmouseOver = mouseX >= this.xPosition - 18 && mouseY >= this.yPosition && mouseX < this.xPosition + fr.getStringWidth(displayText) && mouseY < this.yPosition + this.height;
            int StringColour = this.enabled ? (StringmouseOver ? 0xFFFFA0 : 0xFFFFFFFF) : 0xFFA0A0A0;

            if (this.displayString != null && this.displayString.length() > 0)
            {
                this.drawString(minecraft.fontRenderer, this.displayString, this.xPosition + this.width + 8, this.yPosition + (this.height - 8) / 2, StringColour);
            }
        }
    }

    public void drawPicker(Minecraft minecraft, int MouseX, int MouseY)
    {
        if (this.visible && this.picker != null)
        {
            this.picker.drawButton(minecraft, MouseX, MouseY);
            if (this.picker.getDialogResult() == DialogResult.OK)
            {
                this.closePicker(true);
            }
            else if (this.picker.getDialogResult() == DialogResult.Cancel)
            {
                this.closePicker(false);
            }
        }
    }

    void closePicker(boolean getColour)
    {
        if (getColour)
        {
            this.colour = this.picker.getColour();
        }
        this.picker = null;
        this.pickerClicked = false;
    }

    @Override
    public void mouseReleased(int MouseX, int MouseY)
    {
        if (this.pickerClicked && this.picker != null)
        {
            this.picker.mouseReleased(MouseX, MouseY);
            this.pickerClicked = false;
        }
    }

    @Override
    public boolean mousePressed(Minecraft minecraft, int MouseX, int MouseY)
    {
        boolean pressed = super.mousePressed(minecraft, MouseX, MouseY);

        if (this.picker == null)
        {
            if (pressed)
            {
                int xPos = Math.min(this.xPosition + this.width, GuiControl.lastScreenWidth - 233);
                int yPos = Math.min(this.yPosition, GuiControl.lastScreenHeight - 175);

                this.picker = new GuiColourPicker(minecraft, 99, xPos, yPos, 0xFFFFFF & this.colour, "Choose colour");

                this.pickerClicked = false;
            }
            return pressed;
        }
        this.pickerClicked = this.picker.mousePressed(minecraft, MouseX, MouseY);

        if (pressed && !this.pickerClicked)
        {
            this.closePicker(true);
        }
        return this.pickerClicked;
    }

    public boolean keyTyped(char keyChar, int keyCode)
    {
        return (this.picker != null) && this.picker.textBoxKeyTyped(keyChar, keyCode);
    }
}
