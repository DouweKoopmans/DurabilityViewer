package com.fallingdutchman.DurabilityViewer.Renderer;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class BarRenderer
{
    public static void Render(ItemStack Item, int x, int y)
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
        renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 13, (LiteModDurabilityViewer.instance.RDurString ? 1.5f : 1.0f), 0);
        renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), 12, 1, var11);
        renderQuad(tes, x + 2, y + (LiteModDurabilityViewer.instance.RDurString ? 15 : 13), var12, 1, var10);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public static void renderQuad(Tessellator tes, int x, int y, int width, float height, int arg6)
    {
        tes.startDrawingQuads();
        tes.setColorOpaque_I(arg6);
        tes.addVertex((double)(x+0), (double)(y+0), 0.0D);
        tes.addVertex((double)(x+0), (double)(y + height), 0.0D);
        tes.addVertex((double)(x + width), (double)(y + height), 0.0D);
        tes.addVertex((double)(x + width), (double)(y+0), 0.0D);
        tes.draw();
    }
}
