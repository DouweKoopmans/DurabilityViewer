package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.fallingdutchman.DurabilityViewer.Utils.DvUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DurabilityRenderer
{
    private int[] DurColour;
    private int Durmode;
    private int DurSize;
    private boolean Static;

    public DurabilityRenderer(int[] durColour, int durmode, int durSize, boolean Static)
    {
        DurColour = durColour;
        Durmode = durmode;
        DurSize = durSize;
        this.Static = Static;
    }

    //main render methodes
    public void RenderDura(FontRenderer Fr, ItemStack Item, int x, int y)
    {
        int Stringwidth = Fr.getStringWidth(DurText(Item));

        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.scale(
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F));
        Fr.drawStringWithShadow(DurText(Item),
                SmallFont() ? ((x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth) : x + 8 - Stringwidth / 2,
                ((SmallFont() ? (y + 11) * 2 : y + 9)), ColourUtils.DurColour(Item, DurColour, Static));
        GlStateManager.scale(
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F));
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public void RenderArrowCount(FontRenderer Fr, int x, int y, int[] arrowColour)
    {
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.scale(0.5F, 0.5F, 0.5F);
        Fr.drawStringWithShadow(arrowCount(), x * 2, y * 2, ColourUtils.RGBConverter(arrowColour).getRGB());
        GlStateManager.scale(2F, 2F, 2F);
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public void RenderDuraBar(ItemStack Item, int x, int y)
    {
        int filledDuraBarWidth = (int)Math.round(13.0D - (double)Item.getItemDamage() * 13.0D / (double)Item.getMaxDamage());
        int durabilityColour = (int)Math.round(255.0D - (double)Item.getItemDamage() * 255.0D / (double)Item.getMaxDamage());
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();
        GlStateManager.disableTexture2D();
        GlStateManager.disableAlpha();
        GlStateManager.disableBlend();
        Tessellator tes = Tessellator.getInstance();
        DvUtils.draw(tes.getWorldRenderer(), x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 13,
                (LiteModDurabilityViewer.instance.RDurString ? 1.5f : 1.0f), 0, 0, 0, 255);
        DvUtils.draw(tes.getWorldRenderer(), x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 12, 1,
                (255 - durabilityColour) / 4, 64, 0, 255);
        DvUtils.draw(tes.getWorldRenderer(), x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), filledDuraBarWidth,
                1, 255 - durabilityColour, durabilityColour, 0, 255);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    //helper method's
    private String arrowCount()
    {
        int arrowCount = 0;
        ItemStack[] Inventory = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

        for (ItemStack item : Inventory)
        {
            //checks if aInventory is an arrow
            if (item != null && item.getItem().equals(Item.getItemById(262)))
            {
                arrowCount += item.stackSize;
            }
        }
        return Integer.toString(arrowCount);
    }

    private String DurText(ItemStack item)
    {
        String Text;
        if (Durmode == 0)
        {
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }
        else if (Durmode == 1)
        {
            Text = Integer.toString((int) (((float)item.getMaxDamage() - (float) item.getItemDamage() + 1.0F) / (float)item.getMaxDamage() * 100.0F)) + "%";
        }
        else
        {
            Durmode = 0;
            Text = Integer.toString(item.getMaxDamage() - item.getItemDamage() + 1);
        }

        return Text;
    }

    private boolean SmallFont()
    {
        return DurSize == 0 || DurSize != 1;
    }
}
