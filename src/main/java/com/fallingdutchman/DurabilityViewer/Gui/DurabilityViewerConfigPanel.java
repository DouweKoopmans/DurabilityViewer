package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiColouredButton;
import com.fallingdutchman.DurabilityViewer.Gui.ColourPicker.GuiControl;
import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;

import com.fallingdutchman.DurabilityViewer.Utils.ColourUtils;
import com.fallingdutchman.DurabilityViewer.Utils.references;
import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import com.mumfrey.liteloader.util.log.LiteLoaderLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;

import java.util.*;

public class DurabilityViewerConfigPanel extends Gui implements ConfigPanel
{
    //privates
    //maps and lists
    private List<GuiColourConfigLine> ColouredConfigLines = new ArrayList<GuiColourConfigLine>();
    private HashMap<Object, Boolean> GuiButtons = new HashMap<Object, Boolean>();

    //class refrences
    private GuiColourConfigLine StaticColour;
    private GuiCheckbox DurbarBox, DurStringBox, StaticColourBox, ArrowCountBox;
    private LiteModDurabilityViewer mod;
    private Minecraft mc;
    public static GuiButton activeButton;

    //constants
    private final static int SPACING = 16;

    //unused - likely to be removed soon
    private List<GuiButton> ButtonList = new ArrayList<GuiButton>();
    private List<GuiButton> SubButtonList = new ArrayList<GuiButton>();
    private List<GuiColouredButton> colouredbuttons = new ArrayList<GuiColouredButton>();

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

    //TODO: add option to change the DurMode -> look into drop down menus
    //TODO: add option to change DurSize -> look into drop down menus
    @Override
    public void onPanelShown(ConfigPanelHost host)
    {
        mod = host.getMod();

        ScaledResolution scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        GuiControl.setScreenSizeAndScale(host.getWidth(), host.getHeight(), scaledResolution.getScaleFactor());
        int id = 0;
        int line = 0;

//        this.SubButtonList.clear();
//        this.ButtonList.clear();
        this.GuiButtons.clear();
        this.ColouredConfigLines.clear();

        //ButtonList.add(DurStringBox = new GuiCheckbox(id++, 10, SPACING * line++, "Draw durability string" ));
        //ButtonList.add(ArrowCountBox = new GuiCheckbox(id++, 10, SPACING * line++, "Draw arrowcount on the bow texture"));

        //SubButtonList.add(DurbarBox = new GuiCheckbox(id++, 20, SPACING * line++,"Draw the default durability bar as well"));
        //SubButtonList.add(StaticColourBox = new GuiCheckbox(id++, 20, SPACING * line++, "use a static colour"));
        //SubButtonList.add(Colouredbutton = new GuiColouredButton(this.mc, id++, 38, SPACING * 2, 48, 12, ColourUtils.toDec(ColourUtils.toHex(
//                LiteModDurabilityViewer.instance.ArrowColour[0],
//                LiteModDurabilityViewer.instance.ArrowColour[1],
//                LiteModDurabilityViewer.instance.ArrowColour[2])),""));

        GuiButtons.put(DurStringBox = new GuiCheckbox(id++, 10, SPACING * line++, "Draw durability string" ), false);
        GuiButtons.put(DurbarBox = new GuiCheckbox(id++, 20, SPACING * line++,"Draw the default durability bar as well"), true);
        GuiButtons.put(StaticColour = new GuiColourConfigLine(this.mc, id++, 20, SPACING * line++, "Use static colour", ColourUtils.toDec(ColourUtils.toHex(
                LiteModDurabilityViewer.instance.DurColour[0],
                LiteModDurabilityViewer.instance.DurColour[1],
                LiteModDurabilityViewer.instance.DurColour[2])),
                LiteModDurabilityViewer.instance.StaticColour,
                LiteModDurabilityViewer.instance.RDurString), true);
        GuiButtons.put(ArrowCountBox = new GuiCheckbox(id++, 10, SPACING * line++, "Draw arrowcount on the bow texture"), false);
        ColouredConfigLines.add(StaticColour);

//        for (GuiButton button : GuiButtons)
//        {
//            ButtonList.add(button);
//            if (button instanceof GuiControl)
//            {
//                colouredbuttons.add((GuiColouredButton) button);
//            }
//        }


        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;
        DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar && DurStringBox.checked;
        DurbarBox.enabled = DurStringBox.checked;
//        StaticColourBox.checked = LiteModDurabilityViewer.instance.StaticColour;
        ArrowCountBox.checked = LiteModDurabilityViewer.instance.ArrowCount;
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
        for (boolean state : StaticColour.save().keySet())
        {
            LiteModDurabilityViewer.instance.StaticColour = state;

            LiteModDurabilityViewer.instance.DurColour = ColourUtils.CurrentRGB(StaticColour.save().get(state));
        }
        mod.writeConfig();
        LiteLoaderLogger.info("" + LiteModDurabilityViewer.instance.StaticColour);
    }

    @Override
    public void onTick(ConfigPanelHost host){}

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        for (Object button : GuiButtons.keySet())
        {
            if (button instanceof GuiButton)
            {
                GuiButton _button = (GuiButton) button;
                _button.drawButton(this.mc, mouseX, mouseY);
            }
            //else if (button instanceof GuiColourConfigLine)
            //{
//                GuiColourConfigLine line = (GuiColourConfigLine) button;
//                line.draw(mouseX,mouseY);
//            }
        }

        for (GuiColourConfigLine line : ColouredConfigLines)
        {
            line.draw(mouseX,mouseY);
        }
//        for (GuiButton button : ButtonList)
//        {
//            if (!SubButtonList.contains(button))
//            {
//                button.drawButton(this.mc,mouseX, mouseY);
//            }
//            if (DurStringBox.checked)
//            {
//                button.drawButton(this.mc, mouseX, mouseY);
//                if (colouredbuttons.contains(button))
//                {
//                   GuiColouredButton Colourbutton = (GuiColouredButton) button;
//                   Colourbutton.drawPicker(this.mc,mouseX,mouseY);
//                }
//            }
//        }
    }

    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        for (Object button : GuiButtons.keySet())
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

                        DurbarBox.enabled = DurStringBox.checked;
                        DurbarBox.checked = DurStringBox.checked && LiteModDurabilityViewer.instance.RDurBar;

                        //refresh the coloured buttons.
                        StaticColour.refresh(LiteModDurabilityViewer.instance.RDurString);
                    } /*else if (button.equals(StaticColourBox))
                {
                    LiteModDurabilityViewer.instance.StaticColour = !LiteModDurabilityViewer.instance.StaticColour;
                    StaticColourBox.checked = LiteModDurabilityViewer.instance.StaticColour;
                } */ else if (button.equals(ArrowCountBox))
                    {
                        LiteModDurabilityViewer.instance.ArrowCount = !LiteModDurabilityViewer.instance.ArrowCount;
                        ArrowCountBox.checked = LiteModDurabilityViewer.instance.ArrowCount;
                    }
                }
            }
            else if (button instanceof GuiColourConfigLine)
            {
                GuiColourConfigLine _button = (GuiColourConfigLine) button;

                _button.mousePressed(mouseX,mouseY);
            }
        }
/*        for (GuiColourConfigLine colourbutton : ColouredConfigLines)
        {
            colourbutton.mousePressed(mouseX,mouseY);
        }
        for (GuiButton button : ButtonList)
        {
            if (button.mousePressed(this.mc, mouseX, mouseY))
            {
                activeButton = button;

                if (button.equals(DurbarBox))
                {
                    LiteModDurabilityViewer.instance.RDurBar = !LiteModDurabilityViewer.instance.RDurBar;
                    DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar;
                } else if (button.equals(DurStringBox))
                {
                    LiteModDurabilityViewer.instance.RDurBar = !LiteModDurabilityViewer.instance.RDurBar;
                    DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar;
                } else if (button.equals(StaticColourBox))
                {
                    LiteModDurabilityViewer.instance.StaticColour = !LiteModDurabilityViewer.instance.StaticColour;
                    StaticColourBox.checked = LiteModDurabilityViewer.instance.StaticColour;
                } else if (button.equals(ArrowCountBox))
                {
                    LiteModDurabilityViewer.instance.ArrowCount = !LiteModDurabilityViewer.instance.ArrowCount;
                    ArrowCountBox.checked = LiteModDurabilityViewer.instance.ArrowCount;
                } else
                {
                    for (GuiColouredButton colourbutton : colouredbuttons)
                    {
                        colourbutton.mousePressed(mc, mouseX, mouseY);
                    }
                }
            }
        }
*/    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (activeButton != null)
        {
            activeButton.func_146111_b(mouseX, mouseY);
            if (activeButton instanceof GuiControl)
            {
                ((GuiButton) activeButton).mouseReleased(mouseX, mouseY);
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
}
