package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.PPRenderer;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class PPGlSurfaceView extends GLSurfaceView {

    private PPRenderer mRenderer;

    public PPGlSurfaceView(Context context) {
        this(context, null);
    }

    public PPGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(getRenderer());
    }

    private PPRenderer getRenderer() {
        if (null == mRenderer) {
            mRenderer = new PPRenderer(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }
}
