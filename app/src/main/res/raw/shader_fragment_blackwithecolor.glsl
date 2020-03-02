precision mediump float;
// 共享变量
varying vec2 ft_Position;
// uniform 修饰的变量, 用于在application向shader中传值(这里就是java向vertex和fragment中传值,传的就是一张图片数据据)
uniform sampler2D sTexture;
void main(){
    // 通过texture2D()函数,根据纹理坐标ft_Position取出图片像素.
    // gl_PragColor是默认的变量,接收取出的像素数据
    lowp vec4 textureColor = texture2D(sTexture, ft_Position);
    float gray = textureColor.r*0.2125 + textureColor.g*0.7154 + textureColor.b* 0.0721;
    gl_FragColor = vec4(gray, gray, gray, textureColor.w);
}