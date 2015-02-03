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
    {
        minecraft.fontRendererObj.drawString("Hello World", 5, 5, 0xff00fff);
    }

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
            if (arg3.stackSize > 1 || arg6 != null)
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
                arg1.drawStringWithShadow(ItemDurability, (arg4 + 8) * 2 + 1 + Stringwidth / 2 - Stringwidth, (arg5 + 11) * 2 , 16777215);
                GL11.glScalef(2F,2F,2F);
                GL11.glEnable(GL11.GL_LIGHTING);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
            }
        }
    }
}
