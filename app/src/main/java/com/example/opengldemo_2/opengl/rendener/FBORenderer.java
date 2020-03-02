package com.example.opengldemo_2.opengl.rendener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.util.Log;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.OpenGLSurfaceView;
import com.example.opengldemo_2.opengl.texture.BaseTexture;
import com.example.opengldemo_2.opengl.texture.FBOTexture;
import com.example.opengldemo_2.opengl.texture.VBOTexture;
import com.example.opengldemo_2.opengl.texture.ZhenJiaoTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class FBORenderer implements OpenGLSurfaceView.Renderer {
    private static final String TAG = "PPRenderer";
    private final Context mContext;
    private String sourceVertex;
    private String sourceFragment;
    private FBOTexture fboTexture;
    private VBOTexture texture;

    public FBORenderer(Context context, String sourceVertex, String sourceFragment) {
        this.mContext = context;
        this.sourceVertex = sourceVertex;
        this.sourceFragment = sourceFragment;
    }

    public FBOTexture getFBOTexture() {
        if (null == fboTexture) {
            fboTexture = new FBOTexture();
            if (null != onFBOTextureListener) {
                onFBOTextureListener.onCreate(fboTexture);
            }
        }
        return fboTexture;
    }

    public int[] getTexIds() {
        return getFBOTexture().drawBitmap(getBitmap());
    }

    public void setSourceVertex(String sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public void setSourceFragment(String sourceFragment) {
        this.sourceFragment = sourceFragment;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (TextUtils.isEmpty(sourceVertex) || TextUtils.isEmpty(sourceFragment)) {
            Log.i(TAG, "sourceVertex or sourceFragment must not be null.");
            return;
        }
        getFBOTexture().init(sourceVertex, sourceFragment);
        texture = new VBOTexture();
        texture.init(sourceVertex, sourceFragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        getFBOTexture().viewPort(0, 0, width, height);
        texture.viewPort(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        BaseTexture.drawTextures(texture, getTexIds());
    }

    private Bitmap bitmap;

    public Bitmap getBitmap() {
        if (null == bitmap) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.androids);
        }
        return bitmap;
    }

    private OnFBOTextureListener onFBOTextureListener;

    public void setOnFBOTextureListener(OnFBOTextureListener onFBOTextureListener) {
        this.onFBOTextureListener = onFBOTextureListener;
    }

    public interface OnFBOTextureListener {
        void onCreate(FBOTexture fboTexture);
    }
}
