package com.fallingdutchman.DurabilityViewer.Utils;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.item.ItemStack;

import java.awt.*;

public class ColourUtils
{
    public static int[] CurrentRGB(int rgb)
    {
        int[] RGB = new int[3];

        RGB[0] = (rgb >> 16) & 0xFF;
        RGB[1] = (rgb >> 8) & 0xFF;
        RGB[2] = rgb & 0xFF;

        return RGB;
    }


    public static Color RGBConverter(int[] rgb)
    {
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static int DurColour(ItemStack item)
    {
        if (!LiteModDurabilityViewer.instance.StaticColour)
        {
            int[] rgb = new int[3];
            int currentDura = item.getMaxDamage() - item.getItemDamage() + 1;
            float percentage = ((float) currentDura - 1.0F) / (float) item.getMaxDamage();

            rgb[0] = (int) (155 * (1-percentage)) + 100;
            rgb[1] = (int) (155 * percentage) + 100;
            rgb[2] = 100;

            return RGBConverter(rgb).getRGB();
        }
        return RGBConverter(LiteModDurabilityViewer.instance.DurColour).getRGB();
    }
}
