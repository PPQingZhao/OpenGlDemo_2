package com.example.opengldemo_2.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengldemo_2.R;
import com.example.opengldemo_2.opengl.FBOGlSurfaceView;
import com.example.opengldemo_2.opengl.ZhenJiaoGlSurfaceView;

public class ZhenjiaoOpenGLActivity extends AppCompatActivity {

    private ZhenJiaoGlSurfaceView mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhenjiao_opengl);
        initVideo();
    }

    private void initVideo() {
        mVideo = findViewById(R.id.main_video);
    }

}
