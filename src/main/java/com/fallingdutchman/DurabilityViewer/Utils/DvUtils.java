package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class DvUtils
{
    public static Logger log = LogManager.getLogger("DurabilityViewer");

    /**
     * @param item ItemStack
     * used to check if the provided item is in the players inventory and not in a chest or creative menu
     * @return boolean.
     */
    public static boolean inInv(ItemStack item)
    {
        return Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(item);
    }

    public static void draw(WorldRenderer renderer, int x, int y, int width, float height, int red, int green, int blue, int alpha)
    {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double)(x), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double)(x + width), (double)(y), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }

}
