package com.example.opengldemo_2.opengl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public abstract class BaseTexture {

    private static final String TAG = "BaseTexture";
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    protected int textureCount = 1;
    private int[] textureIds = new int[textureCount];
    protected int windowWidth;
    protected int windowHeight;

    public BaseTexture() {
        vertexBuffer = ByteBuffer.allocateDirect(getVertexData().length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(getVertexData());
        vertexBuffer.position(0);

        fragmentBuffer = ByteBuffer.allocateDirect(getFragmentData().length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(getFragmentData());
        fragmentBuffer.position(0);
    }

    public FloatBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public FloatBuffer getFragmentBuffer() {
        return fragmentBuffer;
    }

    private final float[] vertexData = new float[]{
            -1f, -1f,
            1f, -1f,
            -1f, 1f,
            1f, 1f
    };

    /**
     * 获取顶点坐标
     *
     * @return
     */
    protected float[] getVertexData() {
        return vertexData;
    }

    private final float[] fragmentData = new float[]{
            0f, 1f,
            1f, 1f,
            0f, 0f,
            1f, 0f
    };

    private float[] matrix = new float[]{
            1f, 0f, 0f, 0f,
            0f, 1f, 0f, 0f,
            0f, 0f, 1f, 0f,
            0f, 0f, 0f, 1f,
    };

    /**
     * 获取片元坐标
     *
     * @return
     */
    protected float[] getFragmentData() {
        return fragmentData;
    }

    public int[] getTextureIds() {
        return textureIds;
    }

    private GLProgram mProgram;

    public GLProgram getGLProgram() {
        if (null == mProgram) {
            mProgram = new GLProgram();
        }
        return mProgram;
    }

    public float[] getMatrix() {
        return matrix;
    }

    public void init(String sourceVertex, String sourceFragment) {
        getGLProgram().init(sourceVertex, sourceFragment);
        if (!getGLProgram().isInitSuccess()) {
            Log.e(TAG, "GLProgram init failed.");
            return;
        }
        setupShaderAttrib();
        createTextureId(textureIds);
    }

    protected void setupShaderAttrib() {
        // 纹理材质
        GLES20.glUniform1i(getGLProgram().getSTexture(), 0);
        // 顶点
        GLES20.glEnableVertexAttribArray(getGLProgram().getVPosition());
        GLES20.glVertexAttribPointer(getGLProgram().getVPosition(), 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);
        // 片元
        GLES20.glEnableVertexAttribArray(getGLProgram().getFPosition());
        GLES20.glVertexAttribPointer(getGLProgram().getFPosition(), 2, GLES20.GL_FLOAT, false, 8, fragmentBuffer);
    }

    void createTextureId(int[] textureIds) {
        GLES20.glGenTextures(textureIds.length, textureIds, 0);
        for (int texId : textureIds) {
            texParameteri(texId);
        }
    }

    private void texParameteri(int texId) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        // 设置环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        // 设置过滤器
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public void viewPort(int x, int y, int width, int height) {
        this.windowWidth = width - x;
        this.windowHeight = height - y;
        GLES20.glViewport(x, y, width, height);
    }

    int lastTexWidth;
    int lastTexHeight;

    public int[] drawBitmap(Bitmap bitmap) {
        for (int i = 0; i < textureIds.length; i++) {
            if (lastTexWidth != bitmap.getWidth() || lastTexHeight != bitmap.getHeight()) {
                setupTextureSize(textureIds[0], bitmap.getWidth(), bitmap.getHeight(), GLUtils.getInternalFormat(bitmap));
                lastTexWidth = bitmap.getWidth();
                lastTexHeight = bitmap.getHeight();
            }
            drawBitmap(textureIds[i], i, bitmap);
        }
        return textureIds;
    }

    protected void setupTextureSize(int texId, int bintmapWidth, int bitmapHeight, int internalFormat) {
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, internalFormat, bintmapWidth, bitmapHeight, 0, internalFormat, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    protected void drawBitmap(int texId, int index, Bitmap bitmap) {
        GLES20.glUseProgram(getGLProgram().getProgram());
        setupShaderAttrib();
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texId);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + index);
        // 更新渲染数据 替换文理内容
        GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, bitmap);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    public static void drawTextures(BaseTexture texture, int[] textureIds) {
        GLES20.glUseProgram(texture.getGLProgram().getProgram());
        texture.setupShaderAttrib();
        for (int i = 0; i < textureIds.length; i++) {
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[i]);
            GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + i);
            GLES20.glUniformMatrix4fv(texture.getGLProgram().getUMatrix(), 1, false, texture.getMatrix(), 0);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
            GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        }
    }
}
