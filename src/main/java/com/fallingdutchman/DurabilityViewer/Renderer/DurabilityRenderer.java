package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.fallingdutchman.DurabilityViewer.Utils.DvUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

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

        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F),
                (SmallFont() ? 0.5F : 1F));
        Fr.drawStringWithShadow(DurText(Item),
                SmallFont() ? ((x + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth) : x + 8 - Stringwidth / 2,
                ((SmallFont() ? (y + 11) * 2 : y + 9)), ColourUtils.DurColour(Item, DurColour, Static));
        GL11.glScalef(
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F),
                (SmallFont() ? 2.0F : 1F));
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void RenderArrowCount(FontRenderer Fr, int x, int y, int[] arrowColour)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        Fr.drawStringWithShadow(ArrowCount(), x * 2, y * 2, ColourUtils.RGBConverter(arrowColour).getRGB());
        GL11.glScalef(2F, 2F, 2F);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
    }

    public void RenderDuraBar(ItemStack Item, int x, int y)
    {
        int var12 = (int)Math.round(13.0D - (double)Item.getItemDamage() * 13.0D / (double)Item.getMaxDamage());
        int var8 = (int)Math.round(255.0D - (double)Item.getItemDamage() * 255.0D / (double)Item.getMaxDamage());
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        Tessellator tes = Tessellator.instance;
        int var10 = 255 - var8 << 16 | var8 << 8;
        int var11 = (255 - var8) / 4 << 16 | 16128;
        DvUtils.renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 13, (LiteModDurabilityViewer.instance.RDurString ? 1.5f : 1.0f), 0);
        DvUtils.renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 12, 1, var11);
        DvUtils.renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), var12, 1, var10);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    //helper methodes

    private String ArrowCount()
    {
        int arrowcount = 0;
        ItemStack[] Inventory = Minecraft.getMinecraft().thePlayer.inventory.mainInventory;

        for (ItemStack item : Inventory)
        {
            //checks if aInventory is an arrow
            if (item != null && item.getItem().equals(Item.getItemById(262)))
            {
                arrowcount += item.stackSize;
            }
        }
        return Integer.toString(arrowcount);
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
