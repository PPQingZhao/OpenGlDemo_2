package com.example.opengldemo_2.opengl.rendener;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.opengldemo_2.opengl.OpenGLSurfaceView;
import com.example.opengldemo_2.opengl.texture.ZhenJiaoTexture;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MultiRenderer implements OpenGLSurfaceView.Renderer {
    private static final String TAG = "PPRenderer";
    private final Context mContext;
    private String sourceVertex;
    private String sourceFragment;
    private ZhenJiaoTexture zhenJiaoTexture;
    private int[] textures;
    private int textureWidth;
    private int textureHeight;

    public MultiRenderer(Context context, String sourceVertex, String sourceFragment) {
        this.mContext = context;
        this.sourceVertex = sourceVertex;
        this.sourceFragment = sourceFragment;
    }

    public void setSourceVertex(String sourceVertex) {
        this.sourceVertex = sourceVertex;
    }

    public void setSourceFragment(String sourceFragment) {
        this.sourceFragment = sourceFragment;
    }

    private ZhenJiaoTexture getZhenJiaoTexture() {
        if (null == zhenJiaoTexture) {
            zhenJiaoTexture = new ZhenJiaoTexture();
        }
        return zhenJiaoTexture;
    }

    public void setTexture(int[] texture) {
        this.textures = texture;
    }

    public void setTextureSize(int textureWidth,int textureHeight){
        this.textureWidth = textureWidth;
        this.textureHeight = textureHeight;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        if (TextUtils.isEmpty(sourceVertex) || TextUtils.isEmpty(sourceFragment)) {
            Log.i(TAG, "sourceVertex or sourceFragment must not be null.");
            return;
        }
        getZhenJiaoTexture().init(sourceVertex, sourceFragment);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        getZhenJiaoTexture().viewPort(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        if (null != textures) {
            getZhenJiaoTexture().setupMatrix(textureWidth,textureHeight);
            getZhenJiaoTexture().drawTextures(getZhenJiaoTexture(), textures);
        }
    }
}
