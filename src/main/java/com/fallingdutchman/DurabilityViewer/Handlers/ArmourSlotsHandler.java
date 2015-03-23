package com.fallingdutchman.DurabilityViewer.Handlers;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;
import com.fallingdutchman.DurabilityViewer.Renderer.Hud.ArmourSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;


public class ArmourSlotsHandler
{
    private List<ArmourSlot> ArmourSlots = new ArrayList<ArmourSlot>();
    private boolean RenderBar;

    public ArmourSlotsHandler(ItemStack[] Armour, Minecraft mc, boolean RenderBar)
    {
        FontRenderer fr = mc.fontRendererObj;

        for (ItemStack aArmour : Armour)
        {
            if (aArmour != null)
            {
                ArmourSlots.add(new ArmourSlot(fr, aArmour));
            }
        }

        this.RenderBar = RenderBar;
    }

    public void Render(int width,int height, RenderHandler armourRh)
    {
        for (int i = 0; i < ArmourSlots.size(); i++)
        {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            ArmourSlots.get(i).Render(xPos(width,i), yPos(height), armourRh, RenderBar);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private int xPos(int width, int Iteration)
    {
        final int OFFSET = 16;
        final int HOTBAR_OFFSET = 144;
        int WidthOffset;
        if (LiteModDurabilityViewer.instance.ArmourLoc == 0)
        {
            WidthOffset = width / 2 + (ArmourSlots.size() * OFFSET) / 2;
        }
        else if (LiteModDurabilityViewer.instance.ArmourLoc == 1)
        {
            WidthOffset = width / 2 + HOTBAR_OFFSET + (ArmourSlots.size() * OFFSET) /2;
        }
        else if (LiteModDurabilityViewer.instance.ArmourLoc == 2)
        {
            WidthOffset = width / 2 - HOTBAR_OFFSET + (ArmourSlots.size() * OFFSET) /2;
        }
        else
        {
            WidthOffset = width / 2 +  (ArmourSlots.size() * OFFSET) / 2;
        }

        return WidthOffset - OFFSET * ++Iteration;
    }

    private int yPos(int height)
    {
        if (LiteModDurabilityViewer.instance.ArmourLoc == 0)
        {
            if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)    return 22;
            else                                                                return 2;
        }
        else if (LiteModDurabilityViewer.instance.ArmourLoc == 1 || LiteModDurabilityViewer.instance.ArmourLoc == 2)
        {
            return height - 20;
        }
        else
        {
            //this should never be the case, but users always manage to break things.
            LiteModDurabilityViewer.instance.ArmourLoc = 0;

            if (BossStatus.bossName != null && BossStatus.statusBarTime > 0)    return 22;
            else                                                                return 2;
        }
    }
}
