package com.example.opengldemo_2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.PPGlSurfaceView;
import com.example.opengldemo_2.opengl.PPGlSurfaceView2;

public class OpenGL2Activity extends AppCompatActivity {

    private PPGlSurfaceView2 mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl2);
        initVideo();
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
    }

}
