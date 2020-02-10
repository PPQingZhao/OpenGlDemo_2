package com.example.opengldemo_2.opengl.util;

import android.opengl.GLES20;
import android.util.Log;

public class ShaderUtil {
    private static final String TAG = "ShaderUtil";
    public static final int ERROR = -1;

    /**
     * 创建着色器程序
     *
     * @param shaderType 需要创建的 shder 类型
     * @param sourse     shader 代码
     * @return
     */
    public static int loadShader(int shaderType, String sourse) {
        // 创建 shader(着色器程序:顶点或片元)
        int shader = GLES20.glCreateShader(shaderType);
        if (shader == 0) {
            Log.e(TAG,"glCreateShader failed.");
            return ERROR;
        }
        // 加载shader代码
        GLES20.glShaderSource(shader, sourse);
        // 编译 shader
        GLES20.glCompileShader(shader);
        // 检测编译结果
        // compile存放检测结果
        int[] compile = new int[1];
        GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compile, 0);
        if (GLES20.GL_TRUE != compile[0]) {
            // 编译失败,释放
            GLES20.glDeleteShader(shader);
            shader = ERROR;
            Log.e(TAG,"glGetShaderiv failed.");
        }
        return shader;
    }

    /**
     * 创建渲染程序
     *
     * @param sourceVertex   顶点代码
     * @param sourceFragment 片元着色器代码
     * @return
     */
    public static int createProgram(String sourceVertex, String sourceFragment) {
        int shaderVertex = loadShader(GLES20.GL_VERTEX_SHADER, sourceVertex);
        if (ERROR == shaderVertex) {
            Log.e(TAG, "load vertex shader failed!");
            return ERROR;
        }
        int shaderFragment = loadShader(GLES20.GL_FRAGMENT_SHADER, sourceFragment);
        if (ERROR == shaderFragment) {
            Log.e(TAG, "load fragment shader failed!");
            return ERROR;
        }
        // 创建渲染程序
        int program = GLES20.glCreateProgram();
        // 将着色器程序添加到渲染程序
        GLES20.glAttachShader(program, shaderVertex);
        GLES20.glAttachShader(program, shaderFragment);
        // 连接程序,使其生效
        GLES20.glLinkProgram(program);
        return program;
    }
}
