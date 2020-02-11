package com.example.opengldemo_2.opengl.rendener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.Log;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.texture.BaseTexture;
import com.example.opengldemo_2.opengl.texture.GLTexture;
import com.example.opengldemo_2.opengl.texture.GLTextureTest;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class PPRenderer2 implements GLSurfaceView.Renderer {
    private static final String TAG = "PPRenderer";
    private final Context mContext;
    private String sourceVertex;
    private String sourceFragment;
    private GLTexture glTexture;
    private GLTextureTest glTextureTest;

    public PPRenderer2(Context context, String sourceVertex, String sourceFragment) {
        this.mContext = context;
        this.sourceVertex = sourceVertex;
        this.sourceFragment = sourceFragment;
    }

    private GLTexture getGLTexture() {
        if (null == glTexture) {
            glTexture = new GLTexture();
        }
        return glTexture;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (TextUtils.isEmpty(sourceVertex) || TextUtils.isEmpty(sourceFragment)) {
            Log.i(TAG, "sourceVertex or sourceFragment must not be null.");
            return;
        }
        getGLTexture().init(sourceVertex, sourceFragment);
        glTextureTest = new GLTextureTest();
        glTextureTest.init(sourceVertex,sourceFragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        getGLTexture().viewPort(0, 0, width, height);
        glTextureTest.viewPort(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f);
        int[] texIds = getGLTexture().drawBitmap(getBitmap());
        BaseTexture.drawTextures(glTextureTest, texIds);
    }

    private Bitmap bitmap;

    private Bitmap getBitmap() {
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.androids);
        }
        return bitmap;
    }
}
