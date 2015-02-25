package com.fallingdutchman.DurabilityViewer.Config;

import com.fallingdutchman.DurabilityViewer.References.references;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mumfrey.liteloader.core.LiteLoader;
import com.mumfrey.liteloader.modconfig.ConfigStrategy;
import com.mumfrey.liteloader.modconfig.Exposable;
import com.mumfrey.liteloader.modconfig.ExposableOptions;


public class Configurations implements Exposable
{
    public static Configurations instance;

    public void writeConfig()
    {
        LiteLoader.getInstance().writeConfig(this);
    }

    //--------------------------Standard config settings!--------------------------//
    @Expose
    @SerializedName("Durability_bar")
    public boolean RDurBar = true;

    @Expose
    @SerializedName("Armor_durability")
    public boolean RADur = true;

    @Expose
    @SerializedName("Draw_durability_string")
    public boolean RDurString = true;

    @Expose
    @SerializedName("Durability_display_size")
    public String DurSize = "small";

    /**what should be displayed
     *
     * possibilities:
     *  1 = remaining uses
     *  2 = precentage
     *  3 = remaining uses/max uses
     * */
    @Expose
    @SerializedName("Durability_text_mode")
    public int DurMode = 1;
}
