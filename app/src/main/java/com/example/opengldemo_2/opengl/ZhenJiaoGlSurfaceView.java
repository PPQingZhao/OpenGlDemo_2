package com.example.opengldemo_2.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.rendener.FBORenderer;
import com.example.opengldemo_2.opengl.rendener.ZhenJiaoVBORenderer;
import com.example.opengldemo_2.opengl.util.FileUtil;

public class ZhenJiaoGlSurfaceView extends GLSurfaceView {

    private ZhenJiaoVBORenderer mRenderer;

    public ZhenJiaoGlSurfaceView(Context context) {
        this(context, null);
    }

    public ZhenJiaoGlSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setEGLContextClientVersion(2);
        setRenderer(getRenderer());
    }

    private ZhenJiaoVBORenderer getRenderer() {
        if (null == mRenderer) {
            mRenderer = new ZhenJiaoVBORenderer(getContext(),
                    FileUtil.getRawSource(getContext(), R.raw.shader_vertex_matrix),
                    FileUtil.getRawSource(getContext(), R.raw.shader_fragment));
        }
        return mRenderer;
    }
}
