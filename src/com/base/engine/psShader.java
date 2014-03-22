package com.base.engine;
import java.util.HashMap;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

/**
 * Created by Admin on 3/21/14.
 */
public class psShader {
    private int program;
    private String failedMsg = "Shader Creation Failed;";
    private HashMap< String, Integer > uniformHashMap;

    public psShader( ){
        String programErrorMessage = failedMsg + "Could not find valid memory location in constructor";
        uniformHashMap = new HashMap< String, Integer >( );
        program = glCreateProgram( );
        ShaderErrorLog( program, 0, programErrorMessage );
    }

    public void bind( ){
        glUseProgram( program );
    }

    public void addUniformVariable( String uniformVariable ){
        int uniformLocation = glGetUniformLocation( program, uniformVariable );
        String uniformMessage = failedMsg + "Could not find uniform " + uniformVariable;
        ShaderErrorLog( uniformLocation, -1, uniformMessage );
        uniformHashMap.put(uniformVariable, uniformLocation);
    }

    public void addVertexShader( String text ){
        addProgram( text, GL_VERTEX_SHADER );
    }

    public void addGeometryShader( String text ){
        addProgram( text, GL_GEOMETRY_SHADER );
    }

    public void addFragmentShader( String text ){
        addProgram( text, GL_FRAGMENT_SHADER );
    }

    public void compileShader( ){
        glLinkProgram( program );
        ShaderErrorLog( glGetProgrami(program, GL_LINK_STATUS), 0, glGetProgramInfoLog( program, 1024 ) );
        glValidateProgram( program );
        ShaderErrorLog( glGetProgrami(program, GL_VALIDATE_STATUS), 0, glGetProgramInfoLog(program, 1024) );
    }

    private void addProgram( String text, int shaderType ){
        int shader = glCreateShader( shaderType );
        String shaderMessage = failedMsg + "Could not find valid memory location when adding shader";
        ShaderErrorLog( shader, 0, shaderMessage );
        glShaderSource( shader, text );
        glCompileShader( shader );
        ShaderErrorLog( glGetShaderi(shader, GL_COMPILE_STATUS), 0, glGetShaderInfoLog( shader, 1024 ) );
        glAttachShader( program, shader );
    }

    public void setUniformInteger( String uniformName, int value ){
        glUniform1i( uniformHashMap.get( uniformName ), value );
    }

    public void setUniformFloat( String uniformName, float value ){
        glUniform1f(uniformHashMap.get(uniformName), value);
    }

    public void setUniform( String uniformName, Vector3f value ){
        glUniform3f(uniformHashMap.get(uniformName), value.GetX(), value.GetY(), value.GetZ());
    }

    public void setUniformMatrix( String uniformName, Matrix4f value ){
        glUniformMatrix4( uniformHashMap.get( uniformName ), true, psUtil.CreateFlippedBuffer(value) );
    }

    private void ShaderErrorLog( int checkValue, int errorValue, String errorMessage ){
        if( checkValue == errorValue ) {
            System.err.println( "Oh NOO! " + errorMessage );
            new Exception( ).printStackTrace( );
            System.exit( 1 );
        }
    }
}
