package com.fallingdutchman.DurabilityViewer.Handlers;

import com.fallingdutchman.DurabilityViewer.Gui.GuiRadioButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import java.util.ArrayList;
import java.util.Collections;

public class GuiRadioHandler
{
    private GuiRadioButton[] Buttons;
    private int Settings;
    private Minecraft mc;
    private FontRenderer fr;
    private String Label;

    private final static int SPACING = 16;
    private int xPos;
    private int line ;


    public GuiRadioHandler(Minecraft minecraft, FontRenderer fr, String[] DisplayStrings, int id, int xPos, int line, String Label, int Settings, boolean enabled)
    {
        this.Settings = Settings;
        this.Label = Label;
        this.mc = minecraft;
        this.fr = fr;
        this.xPos = xPos;
        this.line = line++;

        Buttons = new GuiRadioButton[DisplayStrings.length];
        for (int i = 0; i < DisplayStrings.length; i++)
        {
            Buttons[i] = new GuiRadioButton(id++, xPos + 10, SPACING * line++, DisplayStrings[i]);
        }

        for (GuiRadioButton button : this.Buttons)
        {
            button.checked = button.equals(this.Buttons[getSettings()]);
            button.enabled = enabled;
        }
    }

    public void mousePressed(GuiRadioButton button)
    {
        for (int i = 0; i < Buttons.length; i++)
        {
            if (button.equals(Buttons[i]))
            {
                Buttons[i].checked = true;
                Settings = i;
            }
            else
            {
                Buttons[i].checked = false;
            }
        }
    }

    public void Refresh(boolean enabled)
    {
        for (GuiRadioButton Button : Buttons)
        {
            Button.enabled = enabled;
        }
    }

    public void Draw(int mouseX, int mouseY)
    {
        for (GuiRadioButton radioButton : Buttons)
        {
            radioButton.drawButton(this.mc, mouseX, mouseY);
        }
        fr.drawString(this.Label, this.xPos, SPACING * this.line ,0xFFFFFF);
    }

    public int getSettings()
    {
        if (Settings == 0 || Settings == 1) return Settings;
        else
        {
            return 0;
        }
    }

    public ArrayList<GuiRadioButton> getButtons()
    {
        ArrayList<GuiRadioButton> ListButtons = new ArrayList<GuiRadioButton>();
        Collections.addAll(ListButtons, Buttons);
        return ListButtons;
    }
}
