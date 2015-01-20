package com.fallingdutchman.DurabilityViewer;

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;

import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

import static com.fallingdutchman.DurabilityViewer.DurabilityViewerObfTable.*;

public class DurabilityViewerTransformer extends EventInjectionTransformer
{
    @Override
    protected void addEvents()
    {
        Event onRenderItemOverlay = Event.getOrCreate("onRenderItemOverlay", true);
        MethodInfo modRenderItemOverlay  = new MethodInfo(RenderItem, renderItemOverlayIntoGUI , Void.TYPE, FontRenderer, TextureManager, ItemStack, Integer.TYPE, Integer.TYPE, String.class);
        MethodHead InjectPoint = new MethodHead(); //TODO: replace with JumpInsnPoint

        this.addEvent(onRenderItemOverlay,modRenderItemOverlay,InjectPoint).addListener(new MethodInfo("com.fallingdutchman.DurabilityViewer.LiteModHelloWorld", "OnRenderItemOverlay"));
    }
}
