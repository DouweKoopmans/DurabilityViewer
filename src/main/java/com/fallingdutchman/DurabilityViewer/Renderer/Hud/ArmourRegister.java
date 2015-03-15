package com.fallingdutchman.DurabilityViewer.Renderer.Hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;


public class ArmourRegister
{
    private List<ArmourSlot> ArmourSlots = new ArrayList<ArmourSlot>();
    public ArmourRegister(ItemStack[] Armour, Minecraft mc)
    {
        FontRenderer fr = mc.fontRenderer;

        for (ItemStack aArmour : Armour)
        {
            if (aArmour != null)
            {
                ArmourSlots.add(new ArmourSlot(mc, fr, aArmour));
            }
        }
    }

    public void Render(int width)
    {
        for (int i = 0; i < ArmourSlots.size(); i++)
        {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ArmourSlots.get(i).Render(xPos(width,i));
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private int xPos(int width, int Iteration)
    {
        final int OFFSET = 16;
        int WidthOffset = width / 2 + (ArmourSlots.size() * OFFSET) / 2;
        return WidthOffset - OFFSET * ++Iteration;
    }
}
