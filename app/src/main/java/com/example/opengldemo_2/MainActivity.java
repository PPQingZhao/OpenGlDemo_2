package com.example.opengldemo_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.opengldemo_2.opengl.PPGlSurfaceView;
import com.example.opengldemo_2.ui.FBOOpenGLActivity;
import com.example.opengldemo_2.ui.OpenGL2Activity;
import com.example.opengldemo_2.ui.OpenGLActivity;
import com.example.opengldemo_2.ui.VBOOpenGLActivity;
import com.example.opengldemo_2.ui.ZhenjiaoOpenGLActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onOpenGL1(View v) {
        startActivity(new Intent(getApplicationContext(), OpenGLActivity.class));
    }

    public void onOpenGL2(View v) {
        startActivity(new Intent(getApplicationContext(), OpenGL2Activity.class));
    }

    public void onVBOOpenGL(View v) {
        startActivity(new Intent(getApplicationContext(), VBOOpenGLActivity.class));
    }

    public void onFBOOpenGL(View v) {
        startActivity(new Intent(getApplicationContext(), FBOOpenGLActivity.class));
    }

    public void onZhenJiaoOpenGL(View v) {
        startActivity(new Intent(getApplicationContext(), ZhenjiaoOpenGLActivity.class));
    }
}
