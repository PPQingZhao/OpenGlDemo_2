// 顶点坐标 4 维
attribute vec4 v_Position;
// 纹理坐标 2 维
attribute vec2 f_Position;
// varying修饰的变量, 用于shader之间共享(在这里就是 vertex与fragment两个shader之间共享ft_Posiotion)
varying vec2 ft_Position;
uniform mat4 u_Matrix;
// 入口函数
void main(){
    // 为共享变量赋值, 会在fragment shader中使用
    ft_Position = f_Position;
    // gl_Position是默认的变量
    gl_Position = v_Position * u_Matrix;
}