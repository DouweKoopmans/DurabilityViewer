package com.fallingdutchman.DurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Gui.DurabilityViewerConfigPanel;
import com.fallingdutchman.DurabilityViewer.Handlers.RenderHandler;
import com.fallingdutchman.DurabilityViewer.Handlers.ArmourSlotsHandler;
import com.fallingdutchman.DurabilityViewer.Utils.DvUtils;
import com.fallingdutchman.DurabilityViewer.Utils.references;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.Configurable;
import com.mumfrey.liteloader.HUDRenderListener;
import com.mumfrey.liteloader.LiteMod;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.ExposableOptions;
import com.mumfrey.liteloader.transformers.event.EventInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import org.lwjgl.opengl.GL11;

import java.io.File;

@ExposableOptions(strategy = ConfigStrategy.Unversioned, filename = "DurabilityViewer.config.json")
public class LiteModDurabilityViewer implements LiteMod, Configurable, HUDRenderListener
{
    //configurations
    @Expose
    @SerializedName("Draw_Container_durability_string")
    public boolean RDurString = true;

    @Expose
    @SerializedName("Container_Durability_bar")
    public boolean RCDurBar = true;

    @Expose
    @SerializedName("Draw_Armour_durability")
    public boolean RADur = true;

    @Expose
    @SerializedName("Armour_Durability_bar")
    public boolean RADurBar = true;

    /**what size the font needs to be
     *
     * possibilities:
     * 0 = small
     * 1 = large
     **/
    @Expose
    @SerializedName("Durability_display_size")
    public int DurSize = 0;

    /**how the durability should be displayed
     *
     * possibilities:
     *  0 = remaining uses
     *  1 = percentage
     *
     *  the array entry 1 is for container renders, entry 2 is for the armour-hud
     **/
    @Expose
    @SerializedName("Durability_text_mode")
    public int DurMode[] = {0, 0};

    @Expose
    @SerializedName("Container_Static_Colouring")
    public boolean ContStaticColour = false;

    @Expose
    @SerializedName("Armour_Static_Colouring")
    public boolean ArmourStaticColour = false;

    @Expose
    @SerializedName("Container_Static_Colour")
    public int[] ContDurColour = new int[3];

    @Expose
    @SerializedName("Armour_Static_Colour")
    public int[] ArmourDurColour = new int[3];

    @Expose
    @SerializedName("Arrow_Count")
    public boolean ArrowCount = true;

    @Expose
    @SerializedName("Arrow_Count_Colour")
    public int[] ArrowColour = new int[3];

   /** where to display the armour status HUD
    *
    * possibilities:
    * 0: top of the screen
    * 1: right of the durability bar
    * 2: left of the durability bar
    **/
    @Expose
    @SerializedName("Armour_HUD_location")
    public int ArmourLoc = 0;


    //actual class methods
    public static LiteModDurabilityViewer instance;
    private Minecraft mc;
    private RenderHandler ContRh, ArmourRh;
    public static RenderItem itemRenderer = new RenderItem();

    public LiteModDurabilityViewer() {
        if (instance != null) {
            DvUtils.log.error("###########################################################################");
            DvUtils.log.error("Error: Attempted to instantiate two instances of " + references.MOD_NAME);
            DvUtils.log.error("###########################################################################");
        } else {
            instance = this;
        }
        mc = Minecraft.getMinecraft();
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
                    instance.ContRh.RenderDuraString(arg1, arg3, arg4, arg5);
                }
                //draw bar
                if (instance.RCDurBar)
                {
                    instance.ContRh.RenderDuraBar(arg3, arg4, arg5);
                }
            }
            //checks if the current itemstack is a bow item
            if (instance.ArrowCount && arg3.getItem().equals(Item.getItemById(261)) && DvUtils.inInv(arg3))
            {
                instance.ContRh.RenderArrowCount(arg1, arg4, arg5);
            }
        }
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {
        ArmourSlotsHandler AR = new ArmourSlotsHandler(mc.thePlayer.inventory.armorInventory, this.mc, instance.RADurBar);
        ScaledResolution sc = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        if ((this.mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat) && instance.RADur && !(instance.ArmourLoc == 0 && this.mc.gameSettings.keyBindPlayerList.getIsKeyPressed() && this.mc.thePlayer.sendQueue.playerInfoList.size() > 1))
        {
            AR.Render(sc.getScaledWidth(), sc.getScaledHeight(), this.ArmourRh);
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

    @Override
    public void init(File configPath)
    {
        UpdateRenderHandler();
    }

    public void UpdateRenderHandler()
    {
        ContRh = new RenderHandler(RenderHandler.RENDER_TYPE.CONTAINER);
        ArmourRh = new RenderHandler(RenderHandler.RENDER_TYPE.ARMOURSTATUS);
    }

    //Not used
    @Override
    public void upgradeSettings(String version, File configPath, File oldConfigPath){}

    @Override
    public void onPreRenderHUD(int screenWidth, int screenHeight){}
}
