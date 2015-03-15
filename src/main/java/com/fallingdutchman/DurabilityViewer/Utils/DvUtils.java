package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


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
