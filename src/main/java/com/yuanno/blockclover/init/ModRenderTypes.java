package com.yuanno.blockclover.init;

import com.yuanno.blockclover.Main;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;

public class ModRenderTypes extends RenderType{


    public ModRenderTypes(String name, VertexFormat format, int drawMode, int bufferSize, boolean useDelegate, boolean needsSorting, Runnable setupTask, Runnable clearTask)
    {
        super(name, format, drawMode, bufferSize, useDelegate, needsSorting, setupTask, clearTask);
    }

    public static final RenderType TRANSPARENT_COLOR = create(Main.MODID + "translucent_color_notexture", DefaultVertexFormats.NEW_ENTITY, 7, 256, true, true, RenderType.State.builder()
            .setTransparencyState(TRANSLUCENT_TRANSPARENCY)
            .setTextureState(NO_TEXTURE)
            .setCullState(RenderState.NO_CULL)
            .setLightmapState(LIGHTMAP)
            .setDiffuseLightingState(DIFFUSE_LIGHTING)
            .createCompositeState(true));
}
