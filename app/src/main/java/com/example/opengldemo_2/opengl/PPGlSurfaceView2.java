package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.PPRenderer;
import com.example.opengldemo_2.opengl.rendener.PPRenderer2;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class PPGlSurfaceView2 extends GLSurfaceView {

    private PPRenderer2 mRenderer;

    public PPGlSurfaceView2(Context context) {
        this(context, null);
    }

    public PPGlSurfaceView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(getRenderer());
    }

    private PPRenderer2 getRenderer() {
        if (null == mRenderer) {
            mRenderer = new PPRenderer2(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }
}
