#ifdef GL_ES
precision mediump float;
precision mediump int;
#endif

uniform sampler2D texture;
uniform float alpha;
varying vec2 vectorNormal;

void main() {
    vec3 base = texture2D( texture, vectorNormal ).rgb;
    gl_FragColor = vec4( base, alpha );
}
