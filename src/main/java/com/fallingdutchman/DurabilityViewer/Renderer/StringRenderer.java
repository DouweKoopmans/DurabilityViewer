package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 0.5F : 1.0F ));
        Fr.drawStringWithShadow(DurText(Item), (x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth, (y + 11) * 2, ColourUtils.DurColour(Item));
        GL11.glScalef(
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ),
                (LiteModDurabilityViewer.instance.DurSize.equals("small") ? 2.0F : 1.0F ));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public static void RenderArrowCount(FontRenderer Fr,ItemStack Item,int x,int y)
    {
        String colour = ColourUtils.toHex(LiteModDurabilityViewer.instance.ArrowColour[0], LiteModDurabilityViewer.instance.ArrowColour[1], LiteModDurabilityViewer.instance.ArrowColour[2]);

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Fr.drawStringWithShadow(ArrowCount(), x * 2, y * 2, ColourUtils.toDec(colour));
        GL11.glScalef(2F, 2F, 2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    private static String ArrowCount()
    {
        int arrowcount = 0;
            ItemStack[] Inventory = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;
            for (ItemStack aInventory : Inventory)
            {
                if (aInventory != null && aInventory.getUnlocalizedName().equals("item.arrow"))
                {
                    arrowcount = aInventory.stackSize;
                }
            }
        return Integer.toString(arrowcount);
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
}
