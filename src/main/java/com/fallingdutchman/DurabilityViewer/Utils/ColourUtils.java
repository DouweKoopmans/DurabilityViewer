package com.fallingdutchman.DurabilityViewer.Utils;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.item.ItemStack;

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

    public static int toDec(String Hex)
    {
        int var1;
        if (Hex.equals(""))
        {
            return 16777215;
        }
        else
        {
            var1 = Integer.parseInt(Hex.trim(), 16);
        }
        return var1;
    }

    public static String toHex(int r, int g, int b)
    {
        return toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    public static String toBrowserHexValue(int number)
    {
        String hexString = Integer.toHexString(number & 255);
        StringBuilder builder = new StringBuilder();

        if (hexString.length() == 1)
        {
            builder.append("0");
            builder.append(hexString);
        }
        else if (hexString.length() == 2)
        {
            builder.append(hexString);
        }
        else if (hexString.length() == 0)
        {
            builder.append("00");
        }

        return builder.toString().toUpperCase();
    }

    public static int DurColour(ItemStack item)
    {
        if (!LiteModDurabilityViewer.instance.StaticColour)
        {
            int currentDura = item.getMaxDamage() - item.getItemDamage() + 1;
            float var1 = ((float) currentDura - 1.0F) / (float) item.getMaxDamage();
            int g = (int) (var1 * 255.0F);
            int r = (int) ((1.0F - var1) * 255.0F);

            return Integer.parseInt(ColourUtils.toHex(Math.min(r + 100, 255), Math.min(g + 100, 255), 100), 16);
        }
        return toDec(toHex(LiteModDurabilityViewer.instance.DurColour[0], LiteModDurabilityViewer.instance.DurColour[1], LiteModDurabilityViewer.instance.DurColour[2]));
    }
}
