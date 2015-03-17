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
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumChatFormatting;
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


    //actual class methods
    public static LiteModDurabilityViewer instance;
    private Minecraft mc;
    private RenderHandler ContRh, ArmourRh;
    public static RenderItem itemRenderer;

    public LiteModDurabilityViewer() {
        if (instance != null) {
            DvUtils.log.error("###########################################################################");
            DvUtils.log.error("Error: Attempted to instantiate two instances of " + references.MOD_NAME);
            DvUtils.log.error("###########################################################################");
        } else {
            instance = this;
        }
        mc = Minecraft.getMinecraft();
        itemRenderer =  mc.getRenderItem();
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
     * @param fontRenderer Reference to the FontRenderer
     * @param stack Reference to the ItemStack
     * @param xPos x
     * @param yPos y
     * @param Text text to be drawn on the Gui
     */
    public static void OnRenderItemOverlay(EventInfo<RenderItem> e, FontRenderer fontRenderer, ItemStack stack, int xPos, int yPos, String Text)
    {
        e.cancel();
        if (stack != null)
        {
            if (stack.stackSize > 1 || Text != null)
            {
                String var6 = Text == null ? String.valueOf(stack.stackSize) : Text;

            if (Text == null && stack.stackSize < 1)
            {
                var6 = EnumChatFormatting.RED + String.valueOf(stack.stackSize);
            }

            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            fontRenderer.drawStringWithShadow(var6, (float)(xPos + 19 - 2 - fontRenderer.getStringWidth(var6)), (float)(yPos + 6 + 3), 16777215);
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        } else if (stack.isItemDamaged())
            {
                //draw string
                if (instance.RDurString)
                {
                    instance.ContRh.RenderDuraString(fontRenderer, stack, xPos, yPos);
                }
                //draw bar
                if (instance.RCDurBar)
                {
                    instance.ContRh.RenderDuraBar(stack, xPos, yPos);
                }
            }
            //checks if the current itemstack is a bow item
            if (instance.ArrowCount && stack.getItem().equals(Item.getItemById(261)) && DvUtils.inInv(stack))
            {
                instance.ContRh.RenderArrowCount(fontRenderer, xPos, yPos);
            }
        }
    }

    @Override
    public void onPostRenderHUD(int screenWidth, int screenHeight)
    {
        ArmourSlotsHandler AR = new ArmourSlotsHandler(mc.thePlayer.inventory.armorInventory, this.mc, instance.RADurBar);

        if ((this.mc.inGameHasFocus || mc.currentScreen == null || mc.currentScreen instanceof GuiChat) && instance.RADur)
        {
            AR.Render(screenWidth, this.ArmourRh);
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
