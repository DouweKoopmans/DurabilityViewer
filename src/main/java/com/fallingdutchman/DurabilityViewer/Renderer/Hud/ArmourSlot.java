package com.fallingdutchman.DurabilityViewer.Renderer.Hud;

import com.fallingdutchman.DurabilityViewer.Handlers.RenderHandler;
import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
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
        GL11.glEnable(32826);
        RenderHelper.disableStandardItemLighting();
        RenderHelper.enableGUIStandardItemLighting();

        LiteModDurabilityViewer.itemRenderer.renderItemAndEffectIntoGUI(this.Item, xPos, yPos);
        armourRh.RenderDuraString(this.fr, this.Item, xPos, yPos);

        if (RenderBar)
        {
            armourRh.RenderDuraBar(this.Item, xPos, yPos);
        }

        GL11.glDisable(32826);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
    }
}
