package com.example.opengldemo_2.opengl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.Matrix;
import android.util.Log;

public class ZhenJiaoTexture extends VBOTexture {
    private final static String TAG = "ZhenJiaoTexture";
    private int textureWidth;
    private int textureHeight;
    private float aspectRatio;

    public float getAspectRatio() {
        return aspectRatio;
    }

    @Override
    public void setupMatrix(int textureWidth, int textureHeight) {
        if (this.textureWidth != textureWidth || this.textureHeight != textureHeight) {
            this.textureWidth = textureWidth;
            this.textureHeight = textureHeight;
            if (textureWidth / (float) textureHeight > windowWidth / (float) windowHeight) {
                aspectRatio = (float) windowHeight / ((windowWidth / (float) textureWidth) * textureHeight);
                Matrix.orthoM(getMatrix(), 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
            } else {
                aspectRatio = (float) windowWidth / ((windowHeight / (float) textureHeight) * textureWidth);
                Matrix.orthoM(getMatrix(), 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
            }
        }
    }
}
