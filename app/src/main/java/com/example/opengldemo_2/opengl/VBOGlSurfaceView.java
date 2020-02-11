package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.PPRenderer2;
import com.example.opengldemo_2.opengl.rendener.VBORenderer;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class VBOGlSurfaceView extends GLSurfaceView {

    private VBORenderer mRenderer;

    public VBOGlSurfaceView(Context context) {
        this(context, null);
    }

    public VBOGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(getRenderer());
    }

    private VBORenderer getRenderer() {
        if (null == mRenderer) {
            mRenderer = new VBORenderer(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }
}
