package com.base.engine;

/**
 * Created by Admin on 3/25/14.
 */
public class psBasicShader extends psShader {
    private static final psBasicShader instance = new psBasicShader( );

    public static psBasicShader getInstance( ){
        return instance;
    }

    private psBasicShader( ){
        super( );
        addVertexShaderFromFile( "basicVertex.vert" );
        addFragmentShaderFromFile( "basicFragment.frag" );
        compileShader( );
        addUniformVariable( "transform" );
        addUniformVariable( "color" );
    }

    public void UpdateUniforms( Matrix4f worldMatrix, Matrix4f projectedMatrix, psMaterial material ){
        if( material.getTexture( ) != null ){
            material.getTexture( ).Bind( );
        } else {
            psRenderUtil.UnbindTextures( );
        }
        setUniformMatrix( "transform", projectedMatrix );
        setUniform( "color", material.getColor( ) );
    }
}
