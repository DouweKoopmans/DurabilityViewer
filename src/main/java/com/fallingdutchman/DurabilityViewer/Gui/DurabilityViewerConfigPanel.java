package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiControl;
import com.fallingdutchman.DurabilityViewer.Handlers.GuiRadioHandler;
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

public class DurabilityViewerConfigPanel extends Gui implements ConfigPanel
{
    //Button lists
    private List<GuiColourConfigLine> ColouredConfigLines = new ArrayList<GuiColourConfigLine>();
    private List<Object> GuiButtons = new ArrayList<Object>();

    //class references
    private GuiColourConfigLine ContStaticColour, ArrowColour, ArmourStaticColour;
    private GuiCheckbox ContDurbarBox, ContDurStringBox, ArmourDurbarBox, ArmourDurStringBox;
    private GuiRadioHandler ContDurModeRadioController, ArmourDurModeRadioController, DurSizeRadioController, ArmourLocRadioControler;
    private LiteModDurabilityViewer mod;
    private Minecraft mc;
    private FontRenderer fr;
    private static GuiButton activeButton;

    //constants
    private final static int SPACING = 16;
    private final static int RANK_ONE = 10;
    private final static int RANK_TWO = 20;

    //make sure the instances are initialized
    public DurabilityViewerConfigPanel()
    {
        mc = Minecraft.getMinecraft();
        fr = mc.fontRendererObj;
    }

    @Override
    public String getPanelTitle()
    {
        return references.MOD_NAME + " options"; //TODO: add support for .lang files
    }

    @Override
    public int getContentHeight()
    {
        return SPACING * (ColouredConfigLines.size() + GuiButtons.size());
    }

    @Override
    public void onPanelShown(ConfigPanelHost host) //TODO: add support for .lang files
    {
        mod = host.getMod();

        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GuiControl.setScreenSizeAndScale(host.getWidth(), host.getHeight(), scaledResolution.getScaleFactor());
        int id = 0;
        int line = 0;

        this.GuiButtons.clear();
        this.ColouredConfigLines.clear();

        GuiButtons.add(ContDurStringBox = new GuiCheckbox(id++, RANK_ONE, SPACING * line++, "Draw durability string"));

        String[] ContDurModeStrings = {"Normal number", "Percentual value"};
        ContDurModeRadioController = new GuiRadioHandler(this.mc, this.fr, ContDurModeStrings ,id++, RANK_TWO, line++, "Display the current durability as a", LiteModDurabilityViewer.instance.DurMode[0], LiteModDurabilityViewer.instance.RDurString);
        line += ContDurModeStrings.length;

        String[] DurSizeStrings = {"Display a small font", "Display a large font (disables durability bar)"};
        DurSizeRadioController = new GuiRadioHandler(this.mc, this.fr, DurSizeStrings,id++, RANK_TWO, line++, "Size of the displayed font", LiteModDurabilityViewer.instance.DurSize, LiteModDurabilityViewer.instance.RDurString);
        line += DurSizeStrings.length;

        ColouredConfigLines.add(ContStaticColour = new GuiColourConfigLine(this.mc, id++, RANK_TWO, SPACING * line++, "Use static colour",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ContDurColour).getRGB(),
                LiteModDurabilityViewer.instance.ContStaticColour,
                LiteModDurabilityViewer.instance.RDurString));

        GuiButtons.add(ContDurbarBox = new GuiCheckbox(id++, RANK_TWO, SPACING * line++, "Draw the vanilla durabilitybar on top of the item texture"));
        ColouredConfigLines.add(ArrowColour = new GuiColourConfigLine(this.mc, id++, RANK_ONE, SPACING * line++, "Display arrowcount in the hud",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ArrowColour).getRGB(),
                LiteModDurabilityViewer.instance.ArrowCount,
                true));

        GuiButtons.add(ArmourDurStringBox = new GuiCheckbox(id++, RANK_ONE, SPACING * line++, "Draw your armour-status on the hud"));

        String[] ArmourDurModeStrings = {"Normal number", "Percentual value"};
        ArmourDurModeRadioController = new GuiRadioHandler(this.mc, this.fr, ArmourDurModeStrings, id++, RANK_TWO, line++, "Display current armour durability as a", LiteModDurabilityViewer.instance.DurMode[1], LiteModDurabilityViewer.instance.RADur);
        line += ArmourDurModeStrings.length;

        String[] ArmourLocModes = {"top center", "right of the hotbar", "left of the hotbar"};
        ArmourLocRadioControler = new GuiRadioHandler(this.mc, this.fr, ArmourLocModes, id++, RANK_TWO, line++, "Where to draw the armour HUD", LiteModDurabilityViewer.instance.ArmourLoc, LiteModDurabilityViewer.instance.RADur);
        line += ArmourLocModes.length;

        ColouredConfigLines.add(ArmourStaticColour = new GuiColourConfigLine(this.mc, id++, RANK_TWO, SPACING * line++, "Use a static colour",
                ColourUtils.RGBConverter(LiteModDurabilityViewer.instance.ArmourDurColour).getRGB(),
                LiteModDurabilityViewer.instance.ArmourStaticColour,
                LiteModDurabilityViewer.instance.RADur));

        //noinspection UnusedAssignment
        GuiButtons.add(ArmourDurbarBox = new GuiCheckbox(id++, RANK_TWO, SPACING * line++, "Draw the vanilla durabilitybar on top of the armour pieces on armourhud"));

        GuiButtons.addAll(ColouredConfigLines);
        GuiButtons.addAll(ContDurModeRadioController.getButtons());
        GuiButtons.addAll(DurSizeRadioController.getButtons());
        GuiButtons.addAll(ArmourDurModeRadioController.getButtons());
        GuiButtons.addAll(ArmourLocRadioControler.getButtons());

        ArmourDurStringBox.checked = LiteModDurabilityViewer.instance.RADur;
        ArmourDurbarBox.checked = LiteModDurabilityViewer.instance.RADurBar;
        ContDurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;
        ContDurbarBox.checked = LiteModDurabilityViewer.instance.RCDurBar && DurSizeRadioController.getSettings() == 0;
        ContDurbarBox.enabled = LiteModDurabilityViewer.instance.DurSize == 0;
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
        LiteModDurabilityViewer.instance.ContStaticColour = ContStaticColour.getChecked() && LiteModDurabilityViewer.instance.RDurString;
        LiteModDurabilityViewer.instance.ArmourStaticColour = ArmourStaticColour.getChecked() && LiteModDurabilityViewer.instance.RADur;
        LiteModDurabilityViewer.instance.ContDurColour = ColourUtils.CurrentRGB(ContStaticColour.getColour());
        LiteModDurabilityViewer.instance.ArmourDurColour = ColourUtils.CurrentRGB(ArmourStaticColour.getColour());
        LiteModDurabilityViewer.instance.ArrowCount = ArrowColour.getChecked();
        LiteModDurabilityViewer.instance.ArrowColour = ColourUtils.CurrentRGB(ArrowColour.getColour());
        LiteModDurabilityViewer.instance.DurMode[0] = ContDurModeRadioController.getSettings();
        LiteModDurabilityViewer.instance.DurMode[1] = ArmourDurModeRadioController.getSettings();
        LiteModDurabilityViewer.instance.DurSize = DurSizeRadioController.getSettings();
        LiteModDurabilityViewer.instance.ArmourLoc = ArmourLocRadioControler.getSettings();

        LiteModDurabilityViewer.instance.UpdateRenderHandler();

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
        DurSizeRadioController.Draw(mouseX, mouseY);
        ContDurModeRadioController.Draw(mouseX, mouseY);
        ArmourDurModeRadioController.Draw(mouseX, mouseY);
        ArmourLocRadioControler.Draw(mouseX, mouseY);
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

                    if (button.equals(ContDurbarBox))
                    {
                        LiteModDurabilityViewer.instance.RCDurBar = !LiteModDurabilityViewer.instance.RCDurBar;
                        ContDurbarBox.checked = LiteModDurabilityViewer.instance.RCDurBar;
                    } else if (button.equals(ContDurStringBox))
                    {
                        LiteModDurabilityViewer.instance.RDurString = !LiteModDurabilityViewer.instance.RDurString;
                        ContDurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;

                        ContDurModeRadioController.Refresh(LiteModDurabilityViewer.instance.RDurString);
                        DurSizeRadioController.Refresh(LiteModDurabilityViewer.instance.RDurString);
                        //refresh the coloured buttons.
                        ContStaticColour.refresh(LiteModDurabilityViewer.instance.RDurString);
                    } else if (button instanceof GuiRadioButton)
                    {
                        if (ContDurModeRadioController.getButtons().contains(button))
                        {
                            ContDurModeRadioController.mousePressed((GuiRadioButton)button);
                        } else if (DurSizeRadioController.getButtons().contains(button))
                        {
                            DurSizeRadioController.mousePressed((GuiRadioButton) button);

                            ContDurbarBox.enabled = ContDurStringBox.checked && DurSizeRadioController.getSettings() == 0;
                            ContDurbarBox.checked = LiteModDurabilityViewer.instance.RCDurBar = DurSizeRadioController.getSettings() == 0;
                        } else if (ArmourDurModeRadioController.getButtons().contains(button))
                        {
                            ArmourDurModeRadioController.mousePressed((GuiRadioButton) button);
                        } else if (ArmourLocRadioControler.getButtons().contains(button))
                        {
                            ArmourLocRadioControler.mousePressed((GuiRadioButton) button);
                        }
                    } else if (button.equals(ArmourDurbarBox))
                    {
                        LiteModDurabilityViewer.instance.RADurBar = !LiteModDurabilityViewer.instance.RADurBar;
                        ArmourDurbarBox.checked = LiteModDurabilityViewer.instance.RADurBar;
                    } else if (button.equals(ArmourDurStringBox))
                    {
                        LiteModDurabilityViewer.instance.RADur = !LiteModDurabilityViewer.instance.RADur;
                        ArmourDurStringBox.checked = LiteModDurabilityViewer.instance.RADur;

                        ArmourDurModeRadioController.Refresh(LiteModDurabilityViewer.instance.RADur);
                        ArmourLocRadioControler.Refresh(LiteModDurabilityViewer.instance.RADur);
                        ArmourStaticColour.refresh(LiteModDurabilityViewer.instance.RADur);
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
            activeButton.mouseReleased(mouseX, mouseY);
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
