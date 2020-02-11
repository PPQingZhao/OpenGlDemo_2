package com.example.opengldemo_2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.PPGlSurfaceView2;
import com.example.opengldemo_2.opengl.VBOGlSurfaceView;

public class VBOOpenGLActivity extends AppCompatActivity {

    private VBOGlSurfaceView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vbo_opengl);
        initVideo();
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
    }

}
