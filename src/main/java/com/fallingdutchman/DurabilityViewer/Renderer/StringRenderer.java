package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class StringRenderer
{
    public static void Render(FontRenderer Fr, ItemStack Item, int x, int y)
    {
        int Durability = Item.getMaxDamage() - Item.getItemDamage() + 1;
        String ItemDurability = Integer.toString(Durability);
        int Stringwidth = Fr.getStringWidth(ItemDurability);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(0.5F,0.5F,0.5F);
        Fr.drawStringWithShadow(ItemDurability, (x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth, (y + 11) * 2, Colour(Item));
        GL11.glScalef(2F,2F,2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    //TODO: figure out what the heck i am doing here.
    private static int Colour(ItemStack item)
    {
        if (!LiteModDurabilityViewer.instance.StaticColour)
        {
            int currentDura = item.getMaxDamage() - item.getItemDamage() + 1;
            float var1 = ((float) currentDura - 1.0F) / (float) item.getMaxDamage();
            int g = (int) (var1 * 255.0F);
            int r = (int) ((1.0F - var1) * 255.0F);

            return Integer.parseInt(toHex(Math.min(r + 100, 255), Math.min(g + 100, 255), 100), 16);
        }
        return toDec(LiteModDurabilityViewer.instance.HexColour);
    }

    private static int toDec(String Hex)
    {
        int var1;
//        if (Hex.contains("a") || Hex.contains("b") || Hex.contains("c") || Hex.contains("d") || Hex.contains("e") ||  Hex.contains("f"))
//        {
        if (Hex.equals(""))
        {
            return 16777215;
        }
        else
        {
            var1 = Integer.parseInt(Hex.trim(), 16);
        }
//        }
        return var1;
    }

    private static String toHex(int r, int g, int b)
    {
        return toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number)
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
}
