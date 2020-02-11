package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.FBORenderer;
import com.example.opengldemo_2.opengl.rendener.VBORenderer;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class FBOGlSurfaceView extends GLSurfaceView {

    private FBORenderer mRenderer;

    public FBOGlSurfaceView(Context context) {
        this(context, null);
    }

    public FBOGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(getRenderer());
    }

    private FBORenderer getRenderer() {
        if (null == mRenderer) {
            mRenderer = new FBORenderer(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }
}
