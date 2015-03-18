package com.fallingdutchman.DurabilityViewer.Renderer.Hud;

import com.fallingdutchman.DurabilityViewer.Handlers.RenderHandler;
import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ArmourSlot
{
    private FontRenderer fr;
    private ItemStack Item;

    public ArmourSlot(FontRenderer Fr, ItemStack item)
    {
        this.fr = Fr;
        this.Item = item;
    }

    public void Render(int xPos,int yPos, RenderHandler armourRh, boolean RenderBar)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.enableGUIStandardItemLighting();

        LiteModDurabilityViewer.itemRenderer.renderItemAndEffectIntoGUI(this.Item, xPos, yPos);
        armourRh.RenderDuraString(this.fr, this.Item, xPos, yPos);

        if (RenderBar)
        {
            armourRh.RenderDuraBar(this.Item, xPos, yPos);
        }

        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
    }
}
