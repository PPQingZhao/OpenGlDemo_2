package com.example.opengldemo_2.opengl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class ZhenJiaoTexture extends VBOTexture {
    private final static String TAG = "ZhenJiaoTexture";

    @Override
    protected void setupTextureSize(int texId, int bitmapWidth, int bitmapHeight, int internalFormat) {
        super.setupTextureSize(texId, bitmapWidth, bitmapHeight, internalFormat);
        float aspectRatio;
        if (bitmapWidth / (float) bitmapHeight > windowWidth / (float) windowHeight) {
            aspectRatio = (float) windowHeight / ((windowWidth / (float) bitmapWidth) * bitmapHeight);
            Matrix.orthoM(getMatrix(), 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        } else {
            aspectRatio = (float) windowWidth / ((windowHeight / (float) bitmapHeight) * bitmapWidth);
            Matrix.orthoM(getMatrix(), 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        }
    }
}
