package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class DvUtils
{
    /**
     * @param item Minecrart ItemStack that you want to check is in the players inventory or not
     *
     * @return if the item is in the players inventory and not in a chest or creative menu.
     */
    public static boolean inInv(ItemStack item)
    {
        return Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(item);

    }
}
