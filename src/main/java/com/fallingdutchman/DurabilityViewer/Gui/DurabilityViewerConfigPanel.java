package com.fallingdutchman.DurabilityViewer.Gui;

import com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer;

import com.mumfrey.liteloader.client.gui.GuiCheckbox;
import com.mumfrey.liteloader.modconfig.ConfigPanel;
import com.mumfrey.liteloader.modconfig.ConfigPanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;

import java.util.ArrayList;
import java.util.List;

public class DurabilityViewerConfigPanel extends Gui implements ConfigPanel
{
    private List<GuiCheckbox> SubButtonList = new ArrayList<GuiCheckbox>();
    private GuiCheckbox DurbarBox, DurStringBox, StaticColourBox;
    private GuiHexField textField;
    private LiteModDurabilityViewer mod;
    private final static int SPACING = 16;
    private final static int BOX_WIDTH = 30;
    private Minecraft mc;
    private GuiButton activeButton;
    private FontRenderer fr;

    public DurabilityViewerConfigPanel()
    {
        mc = Minecraft.getMinecraft();
        fr = Minecraft.getMinecraft().fontRenderer;
    }

    @Override
    public String getPanelTitle()
    {
        return "Durability Viewer options";
    }

    @Override
    public int getContentHeight()
    {
        return SPACING * SubButtonList.size();
    }

    @Override
    public void onPanelShown(ConfigPanelHost host)
    {
        mod = host.getMod();
        int id = 0;
        int line = 0;

        this.SubButtonList.clear();

        DurStringBox = new GuiCheckbox(id++, 10, SPACING * line++, "Draw durability string" );
        SubButtonList.add(DurbarBox = new GuiCheckbox(id++, 20, SPACING * line++,"Draw the default durability bar as well"));
        SubButtonList.add(StaticColourBox = new GuiCheckbox(id++, 20, SPACING * line++, "use a static colour"));
        textField = new GuiHexField(fr,BOX_WIDTH + 20 + fr.getStringWidth("use a static colour"), SPACING * 2, 48, 12 );

        DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar;
        DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;
        StaticColourBox.checked = LiteModDurabilityViewer.instance.StaticColour;
        textField.setEnabled(StaticColourBox.checked);
        textField.setText(LiteModDurabilityViewer.instance.HexColour);
        textField.setMaxStringLength(6);
    }

    @Override
    public void onPanelResize(ConfigPanelHost host){}

    @Override
    public void onPanelHidden()
    {
        mod.writeConfig();
    }

    @Override
    public void onTick(ConfigPanelHost host){}

    @Override
    public void drawPanel(ConfigPanelHost host, int mouseX, int mouseY, float partialTicks)
    {
        DurStringBox.drawButton(this.mc, mouseX, mouseY);
        if (DurStringBox.checked)
        {
            for (GuiButton button : SubButtonList)
            {
                button.drawButton(this.mc,mouseX,mouseY);
                textField.drawTextBox();
            }
        }
    }

    //TODO: make this a bit nicer with a "for" loop and (hash)maps
    @Override
    public void mousePressed(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (DurbarBox.mousePressed(mc,mouseX,mouseY))
        {
            activeButton = DurbarBox;
            LiteModDurabilityViewer.instance.RDurBar = !LiteModDurabilityViewer.instance.RDurBar;
            DurbarBox.checked = LiteModDurabilityViewer.instance.RDurBar;

            //LiteLoaderLogger.info("drawing bar = " + LiteModDurabilityViewer.instance.RDurBar);
        }
        else if (DurStringBox.mousePressed(mc, mouseX,mouseY))
        {
            activeButton = DurStringBox;
            LiteModDurabilityViewer.instance.RDurString = !LiteModDurabilityViewer.instance.RDurString;
            DurStringBox.checked = LiteModDurabilityViewer.instance.RDurString;

            //LiteLoaderLogger.info("drawing string = " + LiteModDurabilityViewer.instance.RDurString);
        }
        else if (StaticColourBox.mousePressed(mc,mouseX,mouseY))
        {
            activeButton = StaticColourBox;
            LiteModDurabilityViewer.instance.StaticColour =!LiteModDurabilityViewer.instance.StaticColour;
            StaticColourBox.checked = LiteModDurabilityViewer.instance.StaticColour;
            textField.setEnabled(StaticColourBox.checked);
            textField.setFocused(!textField.isFocused());
        }
        else
        {
            textField.mouseClicked(mouseX,mouseY,mouseButton);
        }
    }

    @Override
    public void mouseReleased(ConfigPanelHost host, int mouseX, int mouseY, int mouseButton)
    {
        if (activeButton != null)
        {
            activeButton.func_146111_b(mouseX, mouseY);
            if (activeButton != null )
            {
                ((GuiButton) activeButton).mouseReleased(mouseX, mouseY);
                activeButton = null;
            }
        }
    }

    @Override
    public void mouseMoved(ConfigPanelHost host, int mouseX, int mouseY){}

    @Override
    public void keyPressed(ConfigPanelHost host, char keyChar, int keyCode)
    {
        this.textField.textboxKeyTyped(keyChar, keyCode);
        if (textField.isFocused() && (keyCode != org.lwjgl.input.Keyboard.KEY_ESCAPE || keyCode != org.lwjgl.input.Keyboard.KEY_TAB))
        {
            LiteModDurabilityViewer.instance.HexColour = textField.getText();
        }
    }
}
