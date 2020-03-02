package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.FBORenderer;
import com.example.opengldemo_2.opengl.rendener.MultiRenderer;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class MultiGlSurfaceView extends OpenGLSurfaceView {

    private MultiRenderer mRenderer;

    public MultiGlSurfaceView(Context context) {
        this(context, null);
    }

    public MultiGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
    }

    public void setRender(){
        setRenderer(getRenderer());
    }

    public MultiRenderer getRenderer() {
        if (null == mRenderer) {
            mRenderer = new MultiRenderer(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex_matrix),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }

}
