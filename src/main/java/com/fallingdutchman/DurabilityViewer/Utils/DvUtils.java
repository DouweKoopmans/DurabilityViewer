package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;

public class DvUtils
{
    /**
     * @param item ItemStack
     * used to check if the provided item is in the players inventory and not in a chest or creative menu
     * @return boolean.
     */
    public static boolean inInv(ItemStack item)
    {
        return Minecraft.getMinecraft().thePlayer.inventory.hasItemStack(item);
    }
}
