package com.example.opengldemo_2.opengl.texture;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.util.Log;

public class FBOTexture extends ZhenJiaoTexture {
    private final static String TAG = "FBOTexture";
    private final int[] fbos = new int[1];

    @Override
    public void init(String sourceVertex, String sourceFragment) {
        super.init(sourceVertex, sourceFragment);
        createFBO();
    }

    private void createFBO() {
        // 创建fbo
        GLES20.glGenFramebuffers(fbos.length, fbos, 0);
    }

    @Override
    protected float[] getFragmentData() {
        return new float[]{
                0f, 0f,
                1f, 0f,
                0f, 1f,
                1f, 1f
        };
    }

    @Override
    public void viewPort(int x, int y, int width, int height) {
        super.viewPort(x, y, width, height);
        setupFBO(width, height);
    }

    private final int[] fboTextureIds = new int[1];

    private void setupFBO(int width, int height) {
        // 创建 fbo 纹理
        createTextureId(fboTextureIds);
        // 设置纹理大小
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, fboTextureIds[0]);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_RGBA, width, height, 0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        // 绑定纹理
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbos[0]);
        GLES20.glFramebufferTexture2D(GLES20.GL_FRAMEBUFFER, GLES20.GL_COLOR_ATTACHMENT0, GLES20.GL_TEXTURE_2D, fboTextureIds[0], 0);
        if (GLES20.glCheckFramebufferStatus(GLES20.GL_FRAMEBUFFER) != GLES20.GL_FRAMEBUFFER_COMPLETE) {
            Log.e(TAG, "glFramebufferTexture2D failed.");
        }
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);
    }

    @Override
    public int[] drawBitmap(Bitmap bitmap) {
        int[] imgTextures = super.drawBitmap(bitmap);
        GLES20.glUseProgram(getGLProgram().getProgram());
        setupShaderAttrib();

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, fbos[0]);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, imgTextures[0]);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glUniformMatrix4fv(getGLProgram().getUMatrix(), 1, false, getMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0);

        return fboTextureIds;
    }
}
