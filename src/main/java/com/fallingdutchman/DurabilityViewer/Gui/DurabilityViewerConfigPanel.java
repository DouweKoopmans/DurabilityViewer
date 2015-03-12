package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiControl;
import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.fallingdutchman.DurabilityViewer.Utils.references;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;

import java.util.*;

public class DurabilityViewerConfigPanel extends Gui implements ConfigPanel
{
    //maps and lists
    private List<GuiColourConfigLine> ColouredConfigLines = new ArrayList<GuiColourConfigLine>();
    private List<Object> GuiButtons = new ArrayList<Object>();
    private ArrayList<GuiRadioButton> RadioButtons = new ArrayList<GuiRadioButton>();

    //class refrences
    private GuiColourConfigLine StaticColour, ArrowColour;
    private GuiCheckbox DurbarBox, DurStringBox;
    private GuiRadioController RadioController;
    private LiteModDurabilityViewer mod;
    private Minecraft mc;
    public static GuiButton activeButton;

    //constants
    private final static int SPACING = 16;

    //make sure the instances are initialized
    public DurabilityViewerConfigPanel()
    {
        mc = Minecraft.getMinecraft();
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

        int RankOne = 10;
        int RankTwo = 20;

        this.GuiButtons.clear();
        this.ColouredConfigLines.clear();

        GuiButtons.add(DurStringBox = new GuiCheckbox(id++, RankOne, SPACING * line++, "Draw durability string"));
        GuiButtons.add(DurbarBox = new GuiCheckbox(id++, RankTwo, SPACING * line++, "Draw the default durability bar as well"));
        GuiButtons.add(StaticColour = new GuiColourConfigLine(this.mc, id++, RankTwo, SPACING * line++, "Use static colour",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.DurColour).getRGB(),
                LiteModDurabilityViewer.instance.StaticColour,
                LiteModDurabilityViewer.instance.RDurString));

        RadioButtons.add(new GuiRadioButton(id++, RankTwo, SPACING *line++, "this is a test - 1"));
        RadioButtons.add(new GuiRadioButton(id++, RankTwo, SPACING *line++, "this is a test - 2"));
        GuiRadioButton[] radioButtons = new GuiRadioButton[RadioButtons.size()];

        GuiButtons.add(ArrowColour = new GuiColourConfigLine(this.mc, id++, RankOne, SPACING *line++, "Display arrowcount in the hud",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ArrowColour).getRGB(),
                LiteModDurabilityViewer.instance.ArrowCount,
                true));
        RadioController = new GuiRadioController(RadioButtons.toArray(radioButtons), LiteModDurabilityViewer.instance.DurMode, LiteModDurabilityViewer.instance.RDurString);

        for (GuiRadioButton button: RadioButtons)
        {
            GuiButtons.add(button);
        }

        for (Object button : GuiButtons)
        {
            if (button instanceof GuiColourConfigLine)
            {
                ColouredConfigLines.add((GuiColourConfigLine) button);
            }
        }

        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;
        DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar && DurStringBox.checked;
        DurbarBox.enabled = DurStringBox.checked;
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
        LiteModDurabilityViewer.instance.DurMode = RadioController.getSettings();

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
                GuiButton _button = (GuiButton) button;
                _button.drawButton(this.mc, mouseX, mouseY);
            }
            else if (button instanceof GuiColourConfigLine)
            {
                ((GuiColourConfigLine) button).draw(mouseX,mouseY);
            }
        }
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
                    }
                    else if (button.equals(DurStringBox))
                    {
                        LiteModDurabilityViewer.instance.RDurString = !LiteModDurabilityViewer.instance.RDurString;
                        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;

                        DurbarBox.enabled = DurStringBox.checked;
                        DurbarBox.checked = DurStringBox.checked && LiteModDurabilityViewer.instance.RDurBar;

                        RadioController.Refresh(LiteModDurabilityViewer.instance.RDurString);
                        //refresh the coloured buttons.
                        StaticColour.refresh(LiteModDurabilityViewer.instance.RDurString);
                    }
                    else if (button instanceof GuiRadioButton)
                    {
                        RadioController.mousePressed((GuiRadioButton)button);
                    }
                }
            }
            else if (button instanceof GuiColourConfigLine)
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
