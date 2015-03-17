package com.fallingdutchman.DurabilityViewer.Transformer;

import com.fallingdutchman.DurabilityViewer.Utils.references;
import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;

import com.mumfrey.liteloader.transformers.event.inject.MethodHead;

public class DurabilityViewerTransformer extends EventInjectionTransformer
{
    @Override
    protected void addEvents()
    {
        Event onRenderItemOverlay = Event.getOrCreate(references.EVENT_METHOD, true);
        MethodInfo modRenderItemOverlay  = new MethodInfo(
                DurabilityViewerObfTable.RenderItem,
                DurabilityViewerObfTable.renderItemOverlayIntoGUI ,
                Void.TYPE,
                DurabilityViewerObfTable.FontRenderer,
                DurabilityViewerObfTable.ItemStack,
                Integer.TYPE,
                Integer.TYPE,
                String.class);
        MethodHead InjectPoint = new MethodHead(); //TODO: replace with JumpInsnPoint
        MethodInfo eventloc = new MethodInfo(references.EVENT_CLASS, references.EVENT_METHOD);

        this.addEvent(onRenderItemOverlay,modRenderItemOverlay,InjectPoint).addListener(eventloc);
    }
}
