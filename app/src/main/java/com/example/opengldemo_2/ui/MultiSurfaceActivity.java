package com.example.opengldemo_2.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.FBOGlSurfaceView;
import com.example.opengldemo_2.opengl.MultiGlSurfaceView;
import com.example.opengldemo_2.opengl.OpenGLSurfaceView;
import com.example.opengldemo_2.opengl.rendener.FBORenderer;
import com.example.opengldemo_2.opengl.texture.FBOTexture;
import com.example.opengldemo_2.opengl.util.FileUtil;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public class MultiSurfaceActivity extends AppCompatActivity {

    private FBOGlSurfaceView mVideo;
    private LinearLayout mSurfaceParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multisurface);
        initVideo();
        StartMultiSurface();
    }

    private void StartMultiSurface() {
        mVideo.getRenderer().setOnFBOTextureListener(new FBORenderer.OnFBOTextureListener() {
            @Override
            public void onCreate(final FBOTexture fboTexture) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int[] fboTextureIds = fboTexture.getFboTextureIds();
                        int textureWidth = mVideo.getWidth();
                        int textureHeight = mVideo.getHeight();

                        for (int i = 0; i < 4; i++) {
                            final MultiGlSurfaceView multiGlSurfaceView = new MultiGlSurfaceView(getApplicationContext());

                            if (i == 1) {
                                multiGlSurfaceView.getRenderer().setSourceFragment(FileUtil.getRawSource(getApplicationContext(), R.raw.shader_fragment_revercolor));
                            } else if (i == 2) {
                                multiGlSurfaceView.getRenderer().setSourceFragment(FileUtil.getRawSource(getApplicationContext(), R.raw.shader_fragment_blackwithecolor));
                            } else if (i == 3) {
                                multiGlSurfaceView.getRenderer().setSourceFragment(FileUtil.getRawSource(getApplicationContext(), R.raw.shader_fragment_light));
                            }

                            multiGlSurfaceView.getRenderer().setTexture(fboTextureIds);
                            multiGlSurfaceView.getRenderer().setTextureSize(textureWidth, textureHeight);

                            multiGlSurfaceView.setEGLContextFactory(new EGLContextFactory(2, mVideo.getEGLContext()));
                            multiGlSurfaceView.setRender();
                            mSurfaceParent.addView(multiGlSurfaceView, getParams());
                        }
                    }
                });
            }
        });
    }

    private LinearLayout.LayoutParams getParams() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.weight = 1;
        layoutParams.rightMargin = 1;
        return layoutParams;
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
        mSurfaceParent = findViewById(R.id.surface_parent);
    }

    private static class EGLContextFactory implements OpenGLSurfaceView.EGLContextFactory {
        private int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        private int mEGLContextClientVersion = 2;
        private EGLContext mShareContext;

        public EGLContextFactory(int mEGLContextClientVersion, EGLContext mShareContext) {
            this.mEGLContextClientVersion = mEGLContextClientVersion;
            this.mShareContext = mShareContext;
        }

        @Override
        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, mEGLContextClientVersion,
                    EGL10.EGL_NONE};

            return egl.eglCreateContext(display, eglConfig, null == mShareContext ? EGL10.EGL_NO_CONTEXT : mShareContext,
                    2 != 0 ? attrib_list : null);
        }

        @Override
        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
        }
    }

}
