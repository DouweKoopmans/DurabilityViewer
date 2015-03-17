package com.fallingdutchman.DurabilityViewer.Transformer;

import com.mumfrey.liteloader.core.runtime.Obf;

public class DurabilityViewerObfTable extends Obf
{
    /* Privates */
    private static String SrgClass = "net/minecraft/client/renderer/entity/RenderItem";
    private static String ObfClass = "cqh";
    private static String McpMethod = "renderItemOverlayIntoGUI";
    private static String SrgMethod = "func_180453_a";
    private static String ObfMethod = "a";
    private static String FrSrgClass = "net/minecraft/client/gui/FontRenderer";
    private static String FrObfClass = "bty";
    private static String IsSrgClass = "net/minecraft/item/ItemStack";
    private static String IsObfClass = "amj";

    public static DurabilityViewerObfTable RenderItem = new DurabilityViewerObfTable(SrgClass, ObfClass);
    public static DurabilityViewerObfTable FontRenderer = new DurabilityViewerObfTable(FrSrgClass, FrObfClass);
    public static DurabilityViewerObfTable ItemStack = new DurabilityViewerObfTable(IsSrgClass, IsObfClass);
    public static DurabilityViewerObfTable renderItemOverlayIntoGUI = new DurabilityViewerObfTable(SrgMethod, ObfMethod, McpMethod);

    private DurabilityViewerObfTable(String seargeName, String obfName)
    {
        super(seargeName.replaceAll("/", "."), obfName.replaceAll("/", "."), seargeName.replaceAll("/", "."));
    }

    private DurabilityViewerObfTable(String seargename, String obfName, String mcpName)
    {
        super(seargename.replaceAll("/", "."), obfName.replaceAll("/", "."), mcpName.replaceAll("/", "."));
    }
}
