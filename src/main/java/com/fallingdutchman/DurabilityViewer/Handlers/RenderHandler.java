package com.fallingdutchman.DurabilityViewer.Handlers;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Renderer.DurabilityRenderer;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

public class RenderHandler
{
    private DurabilityRenderer Sr;

    public RenderHandler(RENDER_TYPE RenderType)
    {
        switch (RenderType)
        {
            case CONTAINER:
                Sr = new DurabilityRenderer(LiteModDurabilityViewer.instance.ContDurColour, LiteModDurabilityViewer.instance.DurMode[0], LiteModDurabilityViewer.instance.DurSize, LiteModDurabilityViewer.instance.ContStaticColour);
                break;
            case ARMOURSTATUS:
                Sr = new DurabilityRenderer(LiteModDurabilityViewer.instance.ArmourDurColour, LiteModDurabilityViewer.instance.DurMode[1], 0, LiteModDurabilityViewer.instance.ArmourStaticColour);
                break;
        }
    }

    public enum RENDER_TYPE
    {
        CONTAINER,
        ARMOURSTATUS
    }

    public void RenderDuraString(FontRenderer Fr, ItemStack Item, int x, int y)
    {
        this.Sr.RenderDura(Fr, Item, x, y);
    }

    public void RenderDuraBar(ItemStack Item, int x, int y)
    {
        this.Sr.RenderDuraBar(Item, x, y);
    }

    public void RenderArrowCount(FontRenderer Fr, int x, int y)
    {
        this.Sr.RenderArrowCount(Fr, x,y,LiteModDurabilityViewer.instance.ArrowColour);
    }
}
