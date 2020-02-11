package com.example.opengldemo_2.opengl.texture;

import android.opengl.GLES20;

public class VBOTexture extends BaseTexture {
    private final int[] vbos = new int[1];

    @Override
    protected void setupShaderAttrib() {
        GLES20.glUniform1i(getGLProgram().getSTexture(), 0);
        // 初始化 vbo
        initVBOBuffer();
        for (int i = 0; i < getVbos().length; i++) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, getVbos()[i]);
            // 顶点
            GLES20.glEnableVertexAttribArray(getGLProgram().getVPosition());
            GLES20.glVertexAttribPointer(getGLProgram().getVPosition(), 2, GLES20.GL_FLOAT, false, 8, 0);
            // 片元
            GLES20.glEnableVertexAttribArray(getGLProgram().getFPosition());
            GLES20.glVertexAttribPointer(getGLProgram().getFPosition(), 2, GLES20.GL_FLOAT, false, 8, getVertexData().length * 4);
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
    }

    public int[] getVbos() {
        return vbos;
    }

    private void initVBOBuffer() {
        // 创建vbo
        GLES20.glGenBuffers(vbos.length, vbos, 0);
        for (int i = 0; i < getVbos().length; i++) {
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, getVbos()[i]);
            // 分配vbo 内存大小
            GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, getVertexData().length * 4 + getFragmentData().length * 4, null, GLES20.GL_STATIC_DRAW);
            // vertex vbo 内存赋值
            GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, 0, getVertexData().length * 4, getVertexBuffer());
            // 片元 vbo 内存赋值
            GLES20.glBufferSubData(GLES20.GL_ARRAY_BUFFER, getVertexData().length * 4, getFragmentData().length * 4, getFragmentBuffer());
            GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
        }
    }
}
