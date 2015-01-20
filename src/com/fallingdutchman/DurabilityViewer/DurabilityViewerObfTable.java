    package com.fallingdutchman.DurabilityViewer;

import com.mumfrey.liteloader.core.runtime.Obf;

public class DurabilityViewerObfTable extends Obf
{
    /* Privates */
    private static String SrgClass = "net/minecraft/client/renderer/entity/RenderItem";
    private static String ObfClass = "bny";
    private static String McpMethod = "renderItemOverlayIntoGUI";
    private static String SrgMethod = "func_94148_a";
    private static String ObfMethod = "a";
    private static String FrSrgClass = "net/minecraft/client/gui/FontRenderer";
    private static String FrObfClass = "bbu";
    private static String TmSrgClass = "net/minecraft/client/renderer/texture/TextureManager";
    private static String TmObfClass = "bqf";
    private static String IsSrgClass = "net/minecraft/item/ItemStack";
    private static String IsObfClass = "add";

    public static DurabilityViewerObfTable RenderItem = new DurabilityViewerObfTable(SrgClass, ObfClass);
    public static DurabilityViewerObfTable FontRenderer = new DurabilityViewerObfTable(FrSrgClass, FrObfClass);
    public static DurabilityViewerObfTable TextureManager = new DurabilityViewerObfTable(TmSrgClass, TmObfClass);
    public static DurabilityViewerObfTable ItemStack = new DurabilityViewerObfTable(IsSrgClass, IsObfClass);
    public static DurabilityViewerObfTable renderItemOverlayIntoGUI = new DurabilityViewerObfTable(SrgMethod, ObfMethod, McpMethod);

    public DurabilityViewerObfTable(String seargeName, String obfName)
    {
        super(seargeName.replaceAll("/", "."), obfName.replaceAll("/", "."), seargeName.replaceAll("/", "."));
    }

    public DurabilityViewerObfTable(String seargename, String obfName, String mcpName)
    {
        super(seargename.replaceAll("/", "."), obfName.replaceAll("/", "."), mcpName.replaceAll("/", "."));
    }
}
