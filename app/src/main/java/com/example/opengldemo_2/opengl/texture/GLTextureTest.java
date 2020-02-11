package com.example.opengldemo_2.opengl.texture;

public class GLTextureTest extends BaseTexture {

    @Override
    protected float[] getFragmentData() {
        return  new float[]{
                0f, 0f,
                1f, 0f,
                0f, 1f,
                1f, 1f
        };
    }
}
