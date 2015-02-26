package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
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
        GL11.glScalef(
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ));
        Fr.drawStringWithShadow(DurText(Item), (x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth, (y + 11) * 2, Colour(Item));
        GL11.glScalef(
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private static String DurText(ItemStack item)
    {
        String Text;
        if (LiteModDurabilityViewer.instance.DurMode == 1)
        {
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }else if (LiteModDurabilityViewer.instance.DurMode == 2)
        {
            Text = Integer.toString(item.getItemDamage() + 1 / item.getMaxDamage() * 100);
        }else if (LiteModDurabilityViewer.instance.DurMode == 3)
        {
            Text = Integer.toString(item.getItemDamage() + 1) + "/" +  Integer.toString(item.getMaxDamage());
        } else
        {
            LiteLoaderLogger.severe("durability settings broken, fixing this now, please go into settings and reselect the setting you want to use");
            LiteModDurabilityViewer.instance.DurMode = 1;
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }

        return Text;
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
