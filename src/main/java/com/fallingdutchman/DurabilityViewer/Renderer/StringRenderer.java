package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class StringRenderer
{
    public static void RenderDura(FontRenderer Fr, ItemStack Item, int x, int y)
    {
        int Durability = Item.getMaxDamage() - Item.getItemDamage() + 1;
        String ItemDurability = Integer.toString(Durability);
        int Stringwidth = Fr.getStringWidth(ItemDurability);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F));
        Fr.drawStringWithShadow(DurText(Item),
                SmallFont() ? ((x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth) : x + 8 - Stringwidth / 2,
                ((SmallFont() ? (y + 11) * 2 : y + 9)), ColourUtils.DurColour(Item));
        GL11.glScalef(
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void RenderArrowCount(FontRenderer Fr,int x,int y)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Fr.drawStringWithShadow(ArrowCount(), x * 2, y * 2, ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ArrowColour).getRGB());
        GL11.glScalef(2F, 2F, 2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private static String ArrowCount()
    {
        int arrowcount = 0;
        ItemStack[] Inventory = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

        for (ItemStack item : Inventory)
        {
            //checks if aInventory is an arrow
            if (item != null && item.getItem().equals(Item.getItemById(262)))
            {
                arrowcount = item.stackSize;
            }
        }
        return Integer.toString(arrowcount);
    }

    private static String DurText(ItemStack item)
    {
        String Text;
        if (LiteModDurabilityViewer.instance.DurMode == 0)
        {
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }
        else if (LiteModDurabilityViewer.instance.DurMode == 1)
        {
            Text = Integer.toString((int) (((float)item.getMaxDamage() - (float) item.getItemDamage() + 1.0F) / (float)item.getMaxDamage() * 100.0F)) + "%";
        }
        else
        {
            LiteModDurabilityViewer.instance.DurMode = 0;
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }

        return Text;
    }

    private static boolean SmallFont()
    {
        return LiteModDurabilityViewer.instance.DurSize == 0 || LiteModDurabilityViewer.instance.DurSize != 1;
    }
}
