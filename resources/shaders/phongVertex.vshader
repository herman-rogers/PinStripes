#version 330
layout ( location = 0 ) in vec3 position;
layout ( location = 1 ) in vec2 texture;
layout ( location = 2 ) in vec3 normalCache;

out vec2 texCoord0;
out vec3 normal;
out vec3 worldPosition;
uniform mat4 transform;
uniform mat4 transformProjected;

void main( ){
    gl_Position = transformProjected * vec4( position, 1.0 );
	texCoord0 = texture;
	normal = ( transform * vec4( normalCache, 0.0 ) ).xyz;
	worldPosition = ( transform * vec4( position, 1.0 ) ).xyz;
}