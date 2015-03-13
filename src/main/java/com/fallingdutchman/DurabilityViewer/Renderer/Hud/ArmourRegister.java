package com.fallingdutchman.DurabilityViewer.Renderer.Hud;

import com.fallingdutchman.DurabilityViewer.Utils.ArmourKind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;


public class ArmourRegister
{
    private ItemStack[] Armour;
    private Minecraft mc;
    private FontRenderer fr;
    private List<ArmourSlot> ArmourSlots = new ArrayList<ArmourSlot>();
    private RenderItem itemRenderer;

    public ArmourRegister(ItemStack[] Armour, Minecraft mc, RenderItem itemRenderer)
    {
        this.Armour = Armour;
        this.mc = mc;
        this.fr = mc.fontRenderer;
        this.itemRenderer = itemRenderer;

        for (int i = 0; i<  this.Armour.length; i++)
        {
            ArmourSlots.add(new ArmourSlot(this.mc, this.fr,this.Armour[i], ArmourKind.values()[i]));
        }
    }

    public void Render(int width, int height)
    {
        for (ArmourSlot slot : ArmourSlots)
        {
            slot.Render(width, this.itemRenderer);
        }
    }
}
