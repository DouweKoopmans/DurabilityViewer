package com.fallingdutchman.DurabilityViewer.Gui;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;

public class GuiRadioController
{
    private GuiRadioButton[] Buttons;
    private int Settings;

    public GuiRadioController(GuiRadioButton[] Buttons, int Settings, boolean enabled)
    {
        this.Buttons =  Buttons;
        this.Settings = Settings - 1;

        for (GuiRadioButton button : this.Buttons)
        {
            button.checked = button.equals(this.Buttons[this.Settings]);
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
                Settings = i + 1;

                LiteLoaderLogger.info("Array entry: " + i);
                LiteLoaderLogger.info("Settings " + this.Settings);
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

    public int getSettings()
    {
        return Settings++;
    }
}
