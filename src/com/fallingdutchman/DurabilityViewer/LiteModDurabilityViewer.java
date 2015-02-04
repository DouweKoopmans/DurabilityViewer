package com.fallingdutchman.DurabilityViewer;

import com.mumfrey.liteloader.Tickable;
import com.mumfrey.liteloader.transformers.event.EventInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

import java.io.File;

import org.lwjgl.opengl.GL11;

public class LiteModDurabilityViewer implements Tickable
{
    @Override
    public void onTick(Minecraft minecraft, float partialTicks, boolean inGame, boolean clock)
    {}

    @Override
    public String getName()
    {
        return "Hello World Mod";
    }

    @Override
    public String getVersion()
    {
        return "6.9";
    }

    @Override
    public void init(File configPath)
    {}

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {}

    /**
     * called by the EventInjectionTransformer {@link DurabilityViewerTransformer}
     * @param e EventInfo
     * @param arg1 Reference to the FontRenderer Class
     * @param arg2 Reference to the textturemanger class
     * @param arg3 Reference to the ItemStack
     * @param arg4 x
     * @param arg5 y
     * @param arg6
     */
    public static void OnRenderItemOverlay(EventInfo<RenderItem> e, FontRenderer arg1, TextureManager arg2, ItemStack arg3, int arg4, int arg5, String arg6)
    {
        e.cancel();
        if (arg3 != null)
        {
            if (arg3.stackSize > 1 || arg6 != null) //TODO: get rid of this if so i only have the else if left. requires changing the injection point of the event.
            {
                String var7 = arg6 == null ? String.valueOf(arg3.stackSize) : arg6;
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                arg1.drawStringWithShadow(var7, arg4 + 19 - 2 - arg1.getStringWidth(var7), arg5 + 6 + 3, 16777215);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            } else if (arg3.isItemDamaged())
            {
                int Durability = arg3.getMaxDurability() - arg3.getCurrentDurability() + 1;
                String ItemDurability = Integer.toString(Durability);
                int Stringwidth = arg1.getStringWidth(ItemDurability);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                GL11.glScalef(0.5F,0.5F,0.5F);
                arg1.drawStringWithShadow(ItemDurability, (arg4 + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth, (arg5 + 11) * 2, Colour(arg3));
                GL11.glScalef(2F,2F,2F);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }
    }
    //TODO: figure out what the heck i am doing here.
    private static int Colour(ItemStack item)
    {
        int currentDura = item.getMaxDurability() - item.getCurrentDurability() + 1;
        float var1 = ((float) currentDura -1.0F) / (float)item.getMaxDurability();
        int g = (int)(var1 * 255.0F);
        int r = (int)((1.0F - var1) * 255.0F);

        return Integer.parseInt(toHex(Math.min(r + 100 ,255), Math.min(g + 100,255), 100), 16);
    }

    public static String toHex(int r, int g, int b)
    {
        return toBrowserHexValue(r) + toBrowserHexValue(g) + toBrowserHexValue(b);
    }

    private static String toBrowserHexValue(int number)
    {
        String hexString = Integer.toHexString(number & 255);
        StringBuilder builder = new StringBuilder();

        if (hexString.length() == 1)
        {
            builder.append("0");
            builder.append(hexString);
        }
        else if (hexString.length() == 2)
        {
            builder.append(hexString);
        }
        else if (hexString.length() == 0)
        {
            builder.append("00");
        }

        return builder.toString().toUpperCase();
    }
}
