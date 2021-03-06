package com.andrewofarm.msbcr.objects;

import com.andrewofarm.msbcr.programs.GlobeShaderProgram;
import com.andrewofarm.msbcr.programs.OceanShaderProgram;
import com.andrewofarm.msbcr.programs.ShadowMapShaderProgram;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by Andrew on 7/1/17.
 */
public class Ocean extends Object3D {
    private static final int POSITION_COMPONENT_COUNT = 3;
    private static final int NORMAL_COMPONENT_COUNT = 3;

    private static final int TOTAL_COMPONENT_COUNT =
            POSITION_COMPONENT_COUNT +
                NORMAL_COMPONENT_COUNT;

    private final float radius;

    public Ocean(float radius, int meridians, int parallels) {
        super(TOTAL_COMPONENT_COUNT * BYTES_PER_FLOAT);
        this.radius = radius;

        vertexCount = ObjectBuilder.getSphereVertexCount(meridians, parallels);
        indexCount = ObjectBuilder.getSphereIndexCount(meridians, parallels);
        vertexBuf = newFloatBuffer(vertexCount * TOTAL_COMPONENT_COUNT);
        indexBuf = newIntBuffer(indexCount);
        ObjectBuilder.buildSphere((FloatBuffer) vertexBuf, (IntBuffer) indexBuf,
                radius, meridians, parallels, false);
    }

    public float getRadius() {
        return radius;
    }

    public void draw(OceanShaderProgram shaderProgram) {
        setDataOffset(0);
        bindFloatAttribute(shaderProgram.aPositionLocation, POSITION_COMPONENT_COUNT);
        bindFloatAttribute(shaderProgram.aNormalLocation, NORMAL_COMPONENT_COUNT);
        drawElements(MODE_TRIANGLE_STRIP);
    }
}
