package com.fallingdutchman.DurabilityViewer.Transformer;

import com.mumfrey.liteloader.transformers.event.Event;
import com.mumfrey.liteloader.transformers.event.EventInjectionTransformer;
import com.mumfrey.liteloader.transformers.event.MethodInfo;

import com.mumfrey.liteloader.transformers.event.inject.JumpInsnPoint;
import com.mumfrey.liteloader.transformers.event.inject.MethodHead;
import org.objectweb.asm.Opcodes;

public class DurabilityViewerTransformer extends EventInjectionTransformer
{
    @Override
    protected void addEvents()
    {
        Event onRenderItemOverlay = Event.getOrCreate("onRenderItemOverlay", true);
        MethodInfo modRenderItemOverlay  = new MethodInfo(
                DurabilityViewerObfTable.RenderItem,
                DurabilityViewerObfTable.renderItemOverlayIntoGUI ,
                Void.TYPE,
                DurabilityViewerObfTable.FontRenderer,
                DurabilityViewerObfTable.TextureManager,
                DurabilityViewerObfTable.ItemStack,
                Integer.TYPE,
                Integer.TYPE,
                String.class);
        //MethodHead InjectPoint = new MethodHead(); //TODO: replace with JumpInsnPoint
        JumpInsnPoint InjectPoint = new JumpInsnPoint();

        this.addEvent(onRenderItemOverlay,modRenderItemOverlay,InjectPoint).addListener(new MethodInfo("com.fallingdutchman.DurabilityViewer.LiteModDurabilityViewer", "OnRenderItemOverlay"));
    }
}
