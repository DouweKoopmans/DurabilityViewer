package com.fallingdutchman.DurabilityViewer.Renderer.Hud;

import com.fallingdutchman.DurabilityViewer.Utils.ArmourKind;
import com.fallingdutchman.DurabilityViewer.Utils.DvUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

public class ArmourSlot
{
    private Minecraft mc;
    private FontRenderer fr;
    private ItemStack Item;
    private ArmourKind AK;
    private int yPos;

    public ArmourSlot(Minecraft mc, FontRenderer fr, ItemStack item, ArmourKind AK)
    {
        this.mc = mc;
        this.fr = fr;
        this.Item = item;
        this.AK = AK;
        this.yPos = 10;
    }

    public void Render(int width, RenderItem ItemRenderer)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(32826);
        ItemRenderer.renderItemAndEffectIntoGUI(this.fr,this.mc.getTextureManager(),this.Item, 0,0);
        GL11.glDisable(32826);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public int xPos(int width)
    {
        final int HOR_SPACING = 16;
        switch (AK)
        {
            case HELMET:
                return width / 2 - HOR_SPACING * 8;
            case CHESTPLATE:
                return width / 2 - HOR_SPACING * 9;
            case LEGGINS:
                return width / 2 - HOR_SPACING * 10;
            case BOOTS:
                return width / 2 - HOR_SPACING * 11;
        }
        return width / 2;
    }
}
