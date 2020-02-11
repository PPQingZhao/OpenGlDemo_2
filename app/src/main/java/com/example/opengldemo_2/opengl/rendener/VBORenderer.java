package com.example.opengldemo_2.opengl.rendener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.Log;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.texture.VBOTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class VBORenderer implements GLSurfaceView.Renderer {
    private static final String TAG = "PPRenderer";
    private final Context mContext;
    private String sourceVertex;
    private String sourceFragment;
    private VBOTexture vboTexture;

    public VBORenderer(Context context, String sourceVertex, String sourceFragment) {
        this.mContext = context;
        this.sourceVertex = sourceVertex;
        this.sourceFragment = sourceFragment;
    }

    private VBOTexture getVBOTexture() {
        if (null == vboTexture) {
            vboTexture = new VBOTexture();
        }
        return vboTexture;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (TextUtils.isEmpty(sourceVertex) || TextUtils.isEmpty(sourceFragment)) {
            Log.i(TAG, "sourceVertex or sourceFragment must not be null.");
            return;
        }
        getVBOTexture().init(sourceVertex, sourceFragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        getVBOTexture().viewPort(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glClearColor(1f, 0f, 0f, 1f);
        int[] texIds = getVBOTexture().drawBitmap(getBitmap());
        getVBOTexture().drawTextures(getVBOTexture(), texIds);
    }

    private Bitmap bitmap;

    private Bitmap getBitmap() {
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.androids);
        }
        return bitmap;
    }
}
