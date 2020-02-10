package com.example.opengldemo_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.opengldemo_2.opengl.PPGlSurfaceView;

public class MainActivity extends AppCompatActivity {

    private PPGlSurfaceView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVideo();
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
    }

}
