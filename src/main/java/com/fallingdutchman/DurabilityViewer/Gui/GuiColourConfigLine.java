package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiColouredButton;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import net.minecraft.client.Minecraft;

class GuiColourConfigLine
{
    private GuiCheckbox Checkbox;
    private GuiColouredButton ColouredButton;
    private Minecraft mc;
    private boolean checked;

    @SuppressWarnings("UnusedAssignment")
    public GuiColourConfigLine(Minecraft mc, int Id, int xPos, int yPos, String DisplayString, int Colour, boolean checked, boolean enabled)
    {
        this.mc = mc;
        this.checked = checked;
        int BOX_WIDTH = 18;
        int height = 12;
        int width = 24;

        Checkbox = new GuiCheckbox(Id++, xPos, yPos, null);
        Checkbox.checked = checked && enabled;
        Checkbox.enabled = enabled;

        ColouredButton = new GuiColouredButton(mc,Id++, xPos + BOX_WIDTH, yPos, width, height, Colour ,DisplayString);
        ColouredButton.enabled = Checkbox.checked && Checkbox.enabled;
    }

    public void mousePressed(int mouseX, int mouseY)
    {
        if (Checkbox.mousePressed(this.mc, mouseX, mouseY))
        {
            DurabilityViewerConfigPanel.setActiveButton(Checkbox);
            Checkbox.checked = !checked;
            checked = !checked;
            ColouredButton.enabled = checked;
        }
        else if (ColouredButton.mousePressed(this.mc, mouseX, mouseY))
        {
            DurabilityViewerConfigPanel.setActiveButton(ColouredButton);
        }
    }

    public void draw(int mouseX, int mouseY)
    {
        Checkbox.drawButton(this.mc,mouseX,mouseY);
        ColouredButton.drawButton(this.mc,mouseX,mouseY);
    }

    public void drawPicker(int mouseX, int mouseY)
    {
        ColouredButton.drawPicker(this.mc, mouseX, mouseY);
    }

    //this should take the config line it needs to check against and update the checkbox and colour box accordingly.
    public void refresh(boolean enabled)
    {
        Checkbox.enabled = enabled;
        Checkbox.checked = enabled && checked;
        ColouredButton.enabled = enabled;
    }

    public void keyPressed(char keyChar, int keyCode)
    {
        ColouredButton.keyTyped(keyChar,keyCode);
    }

    public int getColour()
    {
        return ColouredButton.getColour();
    }

    public boolean getChecked()
    {
        return checked;
    }
}
