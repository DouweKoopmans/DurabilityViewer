package com.fallingdutchman.DurabilityViewer.Utils;

import net.minecraft.client.Minecraft;
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


}
