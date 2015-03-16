package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.item.ItemStack;

import java.awt.*;

public class ColourUtils
{
    /**
     * @param rgb int
     * takes a rgb value in the form of an int.
     * used to convert the current colour (as provided by {@link com.fallingdutchman.DurabilityViewer.Gui.DurabilityViewerConfigPanel}) to an int array which is saved.
     * @return array of int's representing a R, G and B value.
     */
    public static int[] CurrentRGB(int rgb)
    {
        int[] RGB = new int[3];

        RGB[0] = (rgb >> 16) & 0xFF;
        RGB[1] = (rgb >> 8) & 0xFF;
        RGB[2] = rgb & 0xFF;

        return RGB;
    }


    /**
     * @param rgb int array
     *
     * takes an array of 3 int's representing a R, G and B value.
     * used to convert the RGB arrays from {@link com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer} which are used to save the current RGB values.
     *
     * @return a java color instance of the given rgb value
     */
    public static Color RGBConverter(int[] rgb)
    {
        return new Color(rgb[0], rgb[1], rgb[2]);
    }


    public static int DurColour(ItemStack item, int[] RgbArray, boolean Static)
    {
        if (!Static)
        {
            int[] rgb = new int[3];
            int currentDura = item.getMaxDamage() - item.getItemDamage() + 1;
            float percentage = ((float) currentDura - 1.0F) / (float) item.getMaxDamage();

            rgb[0] = (int) (Delta(100, 255) * (1-percentage)) + 100;
            rgb[1] = (int) (Delta(255, 100) * percentage) + 100;
            rgb[2] = 100;

            return RGBConverter(rgb).getRGB();
        }
        return RGBConverter(RgbArray).getRGB();
    }

    private static int Delta(int Old, int New)
    {
        return Math.abs(New - Old);
    }
}
