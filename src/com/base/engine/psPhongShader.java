package com.base.engine;

import org.lwjgl.Sys;

/**
 * Created by Admin on 3/25/14.
 */
public class psPhongShader extends psShader {
    private static final int MAX_POINT_LIGHTS = 4;
    private static final int MAX_SPOT_LIGHTS = 4;
    private static final psPhongShader instance = new psPhongShader( );
    private static Vector3f ambientLight = new Vector3f( 0.1f, 0.1f, 0.1f );
    private static psDirectionalLight directionalLight = new psDirectionalLight(
                                        new psBaseLight( new Vector3f( 1.0f, 1.0f, 1.0f ), 0 ),
                                        new Vector3f( 0,0,0 ) );
    private static psPointLight[] pointLights = new psPointLight[] {};
    private static psSpotLight[] spotLights = new psSpotLight[] {};

    public static psPhongShader getInstance( ){
        return instance;
    }

    private psPhongShader( ){
        super( );
        addVertexShaderFromFile( "phongVertex.vert" );
        addFragmentShaderFromFile( "phongFragment.frag" );
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
            addUniformVariable( "pointLights[" + i + "].range" );
        }

        for( int i = 0; i < MAX_SPOT_LIGHTS; i++ ){
            addUniformVariable( "spotLights[" + i + "].pointLight.base.color" );
            addUniformVariable( "spotLights[" + i + "].pointLight.base.intensity" );
            addUniformVariable( "spotLights[" + i + "].pointLight.attenuation.constant" );
            addUniformVariable( "spotLights[" + i + "].pointLight.attenuation.linear" );
            addUniformVariable( "spotLights[" + i + "].pointLight.attenuation.exponent" );
            addUniformVariable( "spotLights[" + i + "].pointLight.position" );
            addUniformVariable( "spotLights[" + i + "].pointLight.range" );
            addUniformVariable( "spotLights[" + i + "].direction" );
            addUniformVariable( "spotLights[" + i + "].cutoff" );
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

        for( int i = 0; i < spotLights.length; i++ ) {
            setUniform( "spotLights[" + i + "]", spotLights[i] );
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

    public static void SetSpotLights( psSpotLight[] spotLights ){
        if( pointLights.length > MAX_SPOT_LIGHTS ){
            System.err.println( "LAWDY! TOO MANY SPOT LIGHTS! I can only handle " + MAX_SPOT_LIGHTS
                    + ". You tried to use" +
                    spotLights.length );
            new Exception( ).printStackTrace( );
            System.exit( 1 );
        }
        psPhongShader.spotLights = spotLights;
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
        setUniformFloat( uniformName + ".range", pointLight.getRange( ) );
    }

    public void setUniform( String uniformName, psSpotLight spotLight ){
        setUniform( uniformName + ".pointLight", spotLight.getPointLight( ) );
        setUniform( uniformName + ".direction", spotLight.getDirection( ) );
        setUniformFloat( uniformName + ".cutoff", spotLight.getCutoff( ) );

    }

}
