package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiColouredButton;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import net.minecraft.client.Minecraft;

import java.util.HashMap;

public class GuiColourConfigLine
{
    private int x;
    private int y;
    private int BOX_WIDTH = 18;
    private String DisplayString;
    private GuiCheckbox Checkbox;
    private GuiColouredButton ColouredButton;
    private Minecraft mc;
    private boolean checked;

    public GuiColourConfigLine(Minecraft mc, int Id, int xPos, int yPos, String DisplayString, int Colour, boolean checked, boolean enabled)
    {
        this.x = xPos;
        this.y = yPos;
        this.DisplayString = DisplayString;
        this.mc = mc;
        this.checked = checked;

                Checkbox = new GuiCheckbox(Id++, xPos, yPos, null);
        Checkbox.checked = checked && enabled;
        Checkbox.enabled = enabled;

        ColouredButton = new GuiColouredButton(mc,Id++, xPos + BOX_WIDTH, yPos, 24, 12, Colour ,DisplayString);
        ColouredButton.enabled = Checkbox.checked && Checkbox.enabled;
    }

    public HashMap<Boolean,Integer> save()
    {
        HashMap<Boolean,Integer> save = new HashMap<Boolean, Integer>();
        save.put(checked,ColouredButton.getColour());

        return save;
    }

    public void mousePressed(int mouseX, int mouseY)
    {
        if (Checkbox.mousePressed(this.mc, mouseX, mouseY))
        {
            LiteLoaderLogger.info("checkbox pressed");
            DurabilityViewerConfigPanel.activeButton = Checkbox;
            Checkbox.checked = !checked;
            checked = !checked;
            ColouredButton.enabled = checked;
        }
        else if (ColouredButton.mousePressed(this.mc, mouseX, mouseY))
        {
            LiteLoaderLogger.info("colouredbutton pressed");
            DurabilityViewerConfigPanel.activeButton = ColouredButton;
        }
    }

    public void draw(int mouseX, int mouseY)
    {
        Checkbox.drawButton(this.mc,mouseX,mouseY);
        ColouredButton.drawButton(this.mc,mouseX,mouseY);
        ColouredButton.drawPicker(this.mc,mouseX,mouseY);
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
}
