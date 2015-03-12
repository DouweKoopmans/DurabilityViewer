package com.fallingdutchman.DurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Gui.DurabilityViewerConfigPanel;
import com.fallingdutchman.DurabilityViewer.Renderer.BarRenderer;
import com.fallingdutchman.DurabilityViewer.Renderer.StringRenderer;
import com.fallingdutchman.DurabilityViewer.Utils.DvUtils;
import com.fallingdutchman.DurabilityViewer.Utils.references;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.transformers.event.EventInfo;

import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import java.io.File;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "DurabilityViewer.config.json")
public class LiteModDurabilityViewer implements LiteMod, Configurable //TODO add the ability to display the current armor status.
{
    //configurations
    @Expose
    @SerializedName("Durability_bar")
    public boolean RDurBar = true;

    @Expose
    @SerializedName("Armor_durability")
    public boolean RADur = true;

    @Expose
    @SerializedName("Draw_durability_string")
    public boolean RDurString = true;

    @Expose
    @SerializedName("Durability_display_size")
    public String DurSize = "small";

    /**what should be displayed
     *
     * possibilities:
     *  1 = remaining uses
     *  2 = percentage
     **/
    @Expose
    @SerializedName("Durability_text_mode")
    public int DurMode = 1;

    @Expose
    @SerializedName("Static_Colouring")
    public boolean StaticColour = false;

    @Expose
    @SerializedName("Static_Colour")
    public int[] DurColour = new int[3];

    @Expose
    @SerializedName("Arrow_Count")
    public boolean ArrowCount = true;

    @Expose
    @SerializedName("Arrow_Count_Colour")
    public int[] ArrowColour = new int[3];


    //actual class methods
    public static LiteModDurabilityViewer instance;

    public LiteModDurabilityViewer() {
        if (instance != null) {
            LiteLoaderLogger.severe("###########################################################################");
            LiteLoaderLogger.severe("Error: Attempted to instantiate two instances of " + references.MOD_NAME);
            LiteLoaderLogger.severe("###########################################################################");
        } else {
            instance = this;
        }
    }

    @Override
    public String getName()
    {
        return references.MOD_NAME;
    }

    @Override
    public String getVersion()
    {
        return references.VERSION;
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
                if (instance.RDurString)
                {
                    StringRenderer.RenderDura(arg1, arg3, arg4, arg5);
                }
                //draw bar
                if (instance.RDurBar)
                {
                    BarRenderer.Render(arg3, arg4, arg5);
                }
            }
            //checks if the current itemstack is a bow item
            if (instance.ArrowCount && arg3.getItem().equals(Item.getItemById(261)) && DvUtils.inInv(arg3))
            {
                StringRenderer.RenderArrowCount(arg1, arg4, arg5);
            }
        }
    }

    @Override
    public Class<? extends ConfigPanel> getConfigPanelClass()
    {
        return DurabilityViewerConfigPanel.class;
    }

    public void writeConfig()
    {
        LiteLoader.getInstance().writeConfig(this);
    }

    //Not used
    @Override
    public void init(File configPath){}

    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath){}
}
