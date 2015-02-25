package com.fallingdutchman.DurabilityViewer.Gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

public class GuiHexField extends GuiTextField
{
    public GuiHexField(FontRenderer fr, int x, int y, int w, int h)
    {
        super(fr, x,y, w, h);
    }

    @Override
    public void writeText(String text)
    {
        super.writeText(text.replaceAll("[^0-9a-fA-F]", ""));
    }
}
