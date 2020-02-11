package com.example.opengldemo_2.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.PPGlSurfaceView;

public class OpenGLActivity extends AppCompatActivity {

    private PPGlSurfaceView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);
        initVideo();
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
    }

}
