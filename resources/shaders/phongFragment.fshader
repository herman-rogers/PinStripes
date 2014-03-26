#version 330

const int MAX_POINT_LIGHTS = 4;
const float preventZeroDivision = 0.0001f;

in vec2 texCoord0;
in vec3 normal;
in vec3 worldPosition;
out vec4 fragmentColor;

struct BaseLight{
    vec3 color;
	float intensity;
};

struct DirectionalLight{
    BaseLight base;
	vec3 direction;
};

struct Attenuation{
    float constant;
	float linear;
	float exponent;
};

struct PointLight{
    BaseLight base;
	Attenuation attenuation;
	vec3 position;
};

uniform vec3 baseColor;
uniform vec3 eyePosition;
uniform vec3 ambientLight;
uniform sampler2D sampler;
uniform DirectionalLight directionalLight;
uniform float specularIntensity;
uniform float specularExponent;

uniform PointLight pointLights[MAX_POINT_LIGHTS];

vec4 calcLight( BaseLight base, vec3 direction, vec3 normal ){
    float diffuseFactor = dot( normal, -direction );
	vec4 diffuseColor = vec4( 0,0,0,0 );
	vec4 specularColor = vec4( 0,0,0,0 );
	if( diffuseFactor > 0 ){
	    diffuseColor = vec4( base.color, 1.0 ) * base.intensity * diffuseFactor;
		vec3 directionToEye = normalize( eyePosition - worldPosition );
		vec3 reflectDirection = normalize( reflect( direction, normal ) );
		float specularFactor = dot( directionToEye, reflectDirection );
		specularFactor = pow( specularFactor, specularExponent );
		if( specularFactor > 0 ){
		    specularColor = vec4( base.color, 1.0 ) * specularIntensity * specularFactor;
		}
	}
	return diffuseColor + specularColor;
}

vec4 calcPointLight( PointLight pointLight, vec3 normal ){
    vec3 lightDirection = worldPosition - pointLight.position;
	float distanceToPoint = length( lightDirection );
	lightDirection = normalize( lightDirection );
	vec4 color = calcLight( pointLight.base, lightDirection, normal );
	float attenuation = pointLight.attenuation.constant +
	                    pointLight.attenuation.linear * distanceToPoint +
						pointLight.attenuation.exponent * distanceToPoint * distanceToPoint +
						preventZeroDivision;
	return color / attenuation;
}

vec4 calcDirectionalLight( DirectionalLight directionalLight, vec3 normal ){
    return calcLight( directionalLight.base, directionalLight.direction, normal );
}

void main( ){
	vec4 totalLight = vec4( ambientLight, 1 );
    vec4 color = vec4( baseColor, 1 );
    vec4 textureColor = texture2D( sampler, texCoord0.xy );

	if( textureColor != vec4( 0,0,0,0 ) ){
	    color *= textureColor; 
	}
	vec3 normal = normalize( normal );
	totalLight += calcDirectionalLight( directionalLight, normal );
	for( int i = 0; i < MAX_POINT_LIGHTS; i++ ){
	    totalLight += calcPointLight( pointLights[i], normal );
	}
	fragmentColor = color * totalLight;
}