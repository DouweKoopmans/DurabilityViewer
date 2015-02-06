package com.fallingdutchman.DurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Gui.DurabilityViewerConfigPanel;
import com.fallingdutchman.DurabilityViewer.Renderer.BarRenderer;
import com.fallingdutchman.DurabilityViewer.Renderer.StringRenderer;

import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.HUDRenderListener;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.transformers.event.EventInfo;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import java.io.File;

public class LiteModDurabilityViewer implements HUDRenderListener, Configurable
{
    private static DurabilityViewerConfig config = new DurabilityViewerConfig();

    @Override
    public String getName()
    {
        return "Durability Viewer";
    }

    @Override
    public String getVersion()
    {
        return "0.5";
    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return DurabilityViewerConfigPanel.class;
    }

    /**
     * called by the EventInjectionTransformer {@link com.fallingdutchman.DurabilityViewer.Transformer.DurabilityViewerTransformer}
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
                //draw string
                StringRenderer.Render(arg1, arg3, arg4, arg5);
                //draw bar
                BarRenderer.Render(arg3, arg4, arg5);
            }
        }
    }
    //Not used (yet)

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight)
    {}

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {}

    @Override
    public void init(File configPath)
    {}

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath)
    {}
}
