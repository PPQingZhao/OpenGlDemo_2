package com.example.opengldemo_2.opengl.rendener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.text.TextUtils;
import android.util.Log;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.util.ShaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PPRenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "PPRenderer";
    private final Context mContext;
    private String sourceVertex;
    private String sourceFragment;
    private int mProgram;
    private final FloatBuffer vertexBuffer;
    private final FloatBuffer fragmentBuffer;
    private int vPosition;
    private int fPosition;
    private int sTexture;

    public PPRenderer(Context context, String sourceVertex, String sourceFragment) {
        this.mContext = context;
        this.sourceVertex = sourceVertex;
        this.sourceFragment = sourceFragment;

        // 分配顶点坐标内存
        float[] vertexData = getVertexData();
        vertexBuffer = ByteBuffer.allocateDirect(vertexData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertexData);
        vertexBuffer.position(0);

        // 分配纹理坐标内存
        float[] fragmentData = getFragmentData();
        fragmentBuffer = ByteBuffer.allocateDirect(fragmentData.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(fragmentData);
        fragmentBuffer.position(0);
    }

    /**
     * 获取顶点坐标
     *
     * @return
     */
    protected float[] getVertexData() {
        return new float[]{
                -1f, -1f,
                1f, -1f,
                -1f, 1f,
                1f, 1f
        };
    }

    /**
     * 获取纹理坐标
     *
     * @return
     */
    protected float[] getFragmentData() {
        return new float[]{
                0f, 1f,
                1f, 1f,
                0f, 0f,
                1f, 0f
        };
    }

    int[] textureIds = new int[1];

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (TextUtils.isEmpty(sourceVertex) || TextUtils.isEmpty(sourceFragment)) {
            Log.i(TAG, "sourceVertex or sourceFragment must not be null.");
            return;
        }
        // 创建渲染程序
        mProgram = ShaderUtil.createProgram(sourceVertex, sourceFragment);
        if (ShaderUtil.ERROR == mProgram) {
            Log.e(TAG, "create program failed.");
            return;
        }
        // 激活渲染程序
        GLES20.glUseProgram(mProgram);
        // 获取属性
        vPosition = GLES20.glGetAttribLocation(mProgram, "v_Position");
        fPosition = GLES20.glGetAttribLocation(mProgram, "f_Position");
        sTexture = GLES20.glGetUniformLocation(mProgram, "sTexture");

        // 使顶点变量有效
        GLES20.glEnableVertexAttribArray(vPosition);
        // 传递顶点数据
        GLES20.glVertexAttribPointer(vPosition, 2, GLES20.GL_FLOAT, false, 8, vertexBuffer);

        GLES20.glEnableVertexAttribArray(fPosition);
        GLES20.glVertexAttribPointer(fPosition, 2, GLES20.GL_FLOAT, false, 8, fragmentBuffer);
        // 设置纹理层
        GLES20.glUniform1i(sTexture, 0);

        GLES20.glGenTextures(textureIds.length, textureIds, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
        // 设置环绕方式
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        // 设置过滤器
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        Bitmap bitmap = getBitmap();
        int internalformat = GLUtils.getInternalFormat(bitmap);
        GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, internalformat, bitmap.getWidth(), bitmap.getHeight(), 0, internalformat, GLES20.GL_UNSIGNED_BYTE, null);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureIds[0]);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0 + 0);
        // 更新渲染数据 替换文理内容
        GLUtils.texSubImage2D(GLES20.GL_TEXTURE_2D, 0, 0, 0, getBitmap());
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
    }

    private Bitmap bitmap;

    private Bitmap getBitmap() {
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.androids);
        }
        return bitmap;
    }
}
