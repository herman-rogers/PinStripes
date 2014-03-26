package com.base.engine;

import org.lwjgl.Sys;

/**
 * Created by Admin on 3/25/14.
 */
public class psPhongShader extends psShader {
    private static final psPhongShader instance = new psPhongShader( );
    private static final int MAX_POINT_LIGHTS = 4;
    private static Vector3f ambientLight = new Vector3f( 0.1f, 0.1f, 0.1f );
    private static psDirectionalLight directionalLight = new psDirectionalLight(
                                        new psBaseLight( new Vector3f( 1.0f, 1.0f, 1.0f ), 0 ),
                                        new Vector3f( 0,0,0 ) );
    private static psPointLight[] pointLights = new psPointLight[] {};

    public static psPhongShader getInstance( ){
        return instance;
    }

    private psPhongShader( ){
        super( );
        addVertexShader( psResourceLoader.LoadShader("phongVertex.vshader") );
        addFragmentShader( psResourceLoader.LoadShader("phongFragment.fshader") );
        compileShader( );
        addUniformVariable( "transform" );
        addUniformVariable( "specularIntensity" );
        addUniformVariable( "specularExponent" );
        addUniformVariable( "eyePosition" );
        addUniformVariable( "transformProjected" );
        addUniformVariable( "baseColor" );
        addUniformVariable( "ambientLight" );
        addUniformVariable( "directionalLight.base.color" );
        addUniformVariable( "directionalLight.base.intensity" );
        addUniformVariable( "directionalLight.direction" );

        for( int i = 0; i < MAX_POINT_LIGHTS; i++ ){
            addUniformVariable( "pointLights[" + i + "].base.color" );
            addUniformVariable( "pointLights[" + i + "].base.intensity" );
            addUniformVariable( "pointLights[" + i + "].attenuation.constant" );
            addUniformVariable( "pointLights[" + i + "].attenuation.linear" );
            addUniformVariable( "pointLights[" + i + "].attenuation.exponent" );
            addUniformVariable( "pointLights[" + i + "].position" );
        }
    }

    public void UpdateUniforms( Matrix4f worldMatrix, Matrix4f projectedMatrix, psMaterial material ){
        if( material.getTexture( ) != null ){
            material.getTexture( ).Bind( );
        } else {
            psRenderUtil.UnbindTextures( );
        }
        setUniformMatrix("transformProjected", projectedMatrix);
        setUniformMatrix( "transform", worldMatrix );
        setUniform( "baseColor", material.getColor( ) );

        setUniformFloat("specularIntensity", material.getSpecularIntensity());
        setUniformFloat( "specularExponent", material.getSpecularExponent( ) );
        setUniform( "eyePosition", psTransform.getCamera( ).getPosition( ) );

        setUniform( "ambientLight", ambientLight );
        setUniform( "directionalLight", directionalLight );
        for( int i = 0; i < pointLights.length; i++ ) {
            setUniform( "pointLights[" + i + "]", pointLights[i] );
        }
    }

    public static Vector3f getAmbientLight( ){
        return ambientLight;
    }

    public static void setAmbientLight( Vector3f ambientLight ) {
        psPhongShader.ambientLight = ambientLight;
    }

    public static void SetDirectionalLight( psDirectionalLight directionalLight ){
        psPhongShader.directionalLight = directionalLight;
    }

    public static void SetPointLight( psPointLight[] pointLights ){
        if( pointLights.length > MAX_POINT_LIGHTS ){
            System.err.println( "LAWDY! TOO MANY POINT LIGHTS! I can only handle " + MAX_POINT_LIGHTS
                                + ". You tried to use" +
                                pointLights.length );
            new Exception( ).printStackTrace( );
            System.exit( 1 );
        }
        psPhongShader.pointLights = pointLights;
    }

    public void setUniform( String uniformName, psBaseLight baseLight ){
        setUniform( uniformName + ".color", baseLight.getColor( ) );
        setUniformFloat( ( uniformName + ".intensity" ), baseLight.getIntensity( ) );
    }

    public void setUniform( String uniformName, psDirectionalLight directionalLight ){
        setUniform( uniformName + ".base", directionalLight.getBase( ) );
        setUniform( uniformName + ".direction", directionalLight.getDirection( ) );
    }

    public void setUniform( String uniformName, psPointLight pointLight ){
        setUniform( uniformName + ".base", pointLight.getBaseLight( ) );
        setUniformFloat( uniformName + ".attenuation.constant", pointLight.getAttenuation( ).getConstant( ) );
        setUniformFloat( uniformName + ".attenuation.linear", pointLight.getAttenuation( ).getLinear( ) );
        setUniformFloat( uniformName + ".attenuation.exponent", pointLight.getAttenuation( ).getExponent( ) );
        setUniform( uniformName + ".position", pointLight.getPosition( ) );
    }
}
