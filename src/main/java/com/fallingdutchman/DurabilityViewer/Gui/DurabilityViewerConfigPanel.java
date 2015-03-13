package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiControl;
import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.fallingdutchman.DurabilityViewer.Utils.references;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;

import java.util.*;
import java.util.List;

public class DurabilityViewerConfigPanel extends Gui implements ConfigPanel
{
    //maps and lists
    private List<GuiColourConfigLine> ColouredConfigLines = new ArrayList<GuiColourConfigLine>();
    private List<Object> GuiButtons = new ArrayList<Object>();

    //class refrences
    private GuiColourConfigLine StaticColour, ArrowColour;
    private GuiCheckbox DurbarBox, DurStringBox;
    private GuiRadioController DurModeRadioControler;
    private GuiRadioController DurSizeRadioControler;
    private LiteModDurabilityViewer mod;
    private Minecraft mc;
    private FontRenderer fr;
    public static GuiButton activeButton;

    //constants
    private final static int SPACING = 16;
    private final static int RANK_ONE = 10;
    private final static int RANK_TWO = 20;

    //make sure the instances are initialized
    public DurabilityViewerConfigPanel()
    {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRenderer;
    }

    @Override
    public String getPanelTitle()
    {
        return references.MOD_NAME + " options";
    }

    @Override
    public int getContentHeight()
    {
        return SPACING * (ColouredConfigLines.size() + GuiButtons.size());
    }


    //TODO: add option to change DurSize
    @Override
    public void onPanelShown(ConfigPanelHost host)
    {
        mod = host.getMod();

        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GuiControl.setScreenSizeAndScale(host.getWidth(), host.getHeight(), scaledResolution.getScaleFactor());
        int id = 0;
        int line = 0;

        this.GuiButtons.clear();
        this.ColouredConfigLines.clear();

        GuiButtons.add(DurStringBox = new GuiCheckbox(id++, RANK_ONE, SPACING * line++, "Draw durability string"));

        String[] DurModeStrings = {"Normal number", "Percentual value"};
        DurModeRadioControler = new GuiRadioController(this.mc, this.fr, DurModeStrings ,id++, RANK_TWO, line++, "Display the current durability as a", LiteModDurabilityViewer.instance.DurMode, LiteModDurabilityViewer.instance.RDurString);
        line = line + DurModeStrings.length;

        String[] DurSizeStrings = {"Display a small font", "Display a large font (disables durability bar)"};
        DurSizeRadioControler = new GuiRadioController(this.mc, this.fr, DurSizeStrings,id++, RANK_TWO, line++, "Size of the displayed font", LiteModDurabilityViewer.instance.DurSize, LiteModDurabilityViewer.instance.RDurString);
        line = line + DurSizeStrings.length;

        ColouredConfigLines.add(StaticColour = new GuiColourConfigLine(this.mc, id++, RANK_TWO, SPACING * line++, "Use static colour",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.DurColour).getRGB(),
                LiteModDurabilityViewer.instance.StaticColour,
                LiteModDurabilityViewer.instance.RDurString));

        GuiButtons.add(DurbarBox = new GuiCheckbox(id++, RANK_ONE, SPACING * line++, "Draw the default durability bar as well"));
        ColouredConfigLines.add(ArrowColour = new GuiColourConfigLine(this.mc, id++, RANK_ONE, SPACING * line++, "Display arrowcount in the hud",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ArrowColour).getRGB(),
                LiteModDurabilityViewer.instance.ArrowCount,
                true));

        GuiButtons.addAll(ColouredConfigLines);
        GuiButtons.addAll(DurModeRadioControler.getButtons());
        GuiButtons.addAll(DurSizeRadioControler.getButtons());

        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;
        DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar && DurSizeRadioControler.getSettings() == 0;
        DurbarBox.enabled = LiteModDurabilityViewer.instance.DurSize == 0;
    }

    @Override
    public void onPanelResize(ConfigPanelHost host)
    {
        ScaledResolution scaledres = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GuiControl.setScreenSizeAndScale(host.getWidth(), host.getHeight(), scaledres.getScaleFactor());
    }

    @Override
    public void onPanelHidden()
    {
        LiteModDurabilityViewer.instance.StaticColour = StaticColour.getChecked() && LiteModDurabilityViewer.instance.RDurString;
        LiteModDurabilityViewer.instance.DurColour = ColourUtils.CurrentRGB(StaticColour.getColour());
        LiteModDurabilityViewer.instance.ArrowCount = ArrowColour.getChecked();
        LiteModDurabilityViewer.instance.ArrowColour = ColourUtils.CurrentRGB(ArrowColour.getColour());
        LiteModDurabilityViewer.instance.DurMode = DurModeRadioControler.getSettings();
        LiteModDurabilityViewer.instance.DurSize = DurSizeRadioControler.getSettings();

        mod.writeConfig();
    }

    @Override
    public void onTick(ConfigPanelHost host){}

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        for (Object button : GuiButtons)
        {
            if (button instanceof GuiButton)
            {
                ((GuiButton) button).drawButton(this.mc, mouseX, mouseY);
            } else if (button instanceof GuiColourConfigLine)
            {
                ((GuiColourConfigLine) button).draw(mouseX, mouseY);
            }
        }
        DurSizeRadioControler.Draw(mouseX,mouseY);
        DurModeRadioControler.Draw(mouseX,mouseY);
        for (GuiColourConfigLine line : ColouredConfigLines)
        {
            line.drawPicker(mouseX, mouseY);
        }
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        for (Object button : GuiButtons)
        {
            if (button instanceof GuiButton)
            {
                GuiButton _button = (GuiButton) button;
                if (_button.mousePressed(this.mc, mouseX, mouseY))
                {
                    activeButton = (GuiButton) button;

                    if (button.equals(DurbarBox))
                    {
                        LiteModDurabilityViewer.instance.RDurBar = !LiteModDurabilityViewer.instance.RDurBar;
                        DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar;
                    } else if (button.equals(DurStringBox))
                    {
                        LiteModDurabilityViewer.instance.RDurString = !LiteModDurabilityViewer.instance.RDurString;
                        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;

                        DurModeRadioControler.Refresh(LiteModDurabilityViewer.instance.RDurString);
                        DurSizeRadioControler.Refresh(LiteModDurabilityViewer.instance.RDurString);
                        //refresh the coloured buttons.
                        StaticColour.refresh(LiteModDurabilityViewer.instance.RDurString);
                    } else if (button instanceof GuiRadioButton)
                    {
                        if (DurModeRadioControler.getButtons().contains(button))
                        {
                            DurModeRadioControler.mousePressed((GuiRadioButton)button);
                        }
                        else if (DurSizeRadioControler.getButtons().contains(button))
                        {
                            DurSizeRadioControler.mousePressed((GuiRadioButton) button);

                            DurbarBox.enabled = DurStringBox.checked && DurSizeRadioControler.getSettings() == 0;
                            DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar = DurSizeRadioControler.getSettings() == 0;
                        }
                    }
                }
            } else if (button instanceof GuiColourConfigLine)
            {
                GuiColourConfigLine _button = (GuiColourConfigLine) button;

                _button.mousePressed(mouseX,mouseY);
            }
        }
    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (activeButton != null)
        {
            activeButton.func_146111_b(mouseX, mouseY);
            if (activeButton instanceof GuiControl)
            {
                activeButton.mouseReleased(mouseX, mouseY);
            }
            activeButton = null;
        }
    }

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        for (GuiColourConfigLine line : ColouredConfigLines)
        {
            line.keyPressed(keyChar,keyCode);
        }
    }

    @Override
    public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY){}

    public static void setActiveButton(GuiButton button)
    {
        activeButton = button;
    }
}
