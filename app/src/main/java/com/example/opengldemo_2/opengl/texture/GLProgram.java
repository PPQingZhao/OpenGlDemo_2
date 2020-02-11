package com.example.opengldemo_2.opengl.texture;

import android.opengl.GLES20;
import android.util.Log;

import com.example.opengldemo_2.opengl.util.ShaderUtil;

public class GLProgram {

    private static final String TAG = "GLProgram";
    private int program;
    private boolean initSuccess;
    private int vPosition;
    private int fPosition;
    private int sTexture;

    public boolean isInitSuccess() {
        return initSuccess;
    }

    protected String getVPositionCode() {
        return "v_Position";
    }

    protected String getFPositionCode() {
        return "f_Position";
    }

    protected String getSTextureCode() {
        return "sTexture";
    }

    /**
     * 初始化渲染程序
     *
     * @param sourceVertex
     * @param sourceFragment
     */
    public void init(String sourceVertex, String sourceFragment) {
        // 创建渲染程序
        program = ShaderUtil.createProgram(sourceVertex, sourceFragment);
        if (ShaderUtil.ERROR == program) {
            initSuccess = false;
            Log.e(TAG, "createProgram failed.");
            return;
        }
        initSuccess = true;
        // 激活渲染程序
        GLES20.glUseProgram(program);
        // 获取属性
        vPosition = GLES20.glGetAttribLocation(program, getVPositionCode());
        fPosition = GLES20.glGetAttribLocation(program, getFPositionCode());
        sTexture = GLES20.glGetUniformLocation(program, getSTextureCode());
    }

    public int getVPosition() {
        return vPosition;
    }

    public int getFPosition() {
        return fPosition;
    }

    public int getSTexture() {
        return sTexture;
    }

    public int getProgram() {
        return program;
    }
}
