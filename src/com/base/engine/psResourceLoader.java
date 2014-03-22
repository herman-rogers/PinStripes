package com.base.engine;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Admin on 3/21/14.
 */
public class psResourceLoader {
    static ArrayList< psVertex > vertices = new ArrayList< psVertex >( );
    static ArrayList< Integer > indices = new ArrayList< Integer >( );
    static StringBuilder shaderSource;
    static String newFileName;

    public static String LoadShader( String filename ){
        shaderSource = new StringBuilder( );
        newFileName = filename;
        TryCatchNewFile( "shader", "./resources/shaders/" );
        return shaderSource.toString( );
    }

    public static psMesh LoadMesh( String filename ){
        newFileName = filename;
        String[] splitArray = newFileName.split( "\\." );
        String extension = splitArray[splitArray.length - 1];
        CheckFileExtension( extension );
        TryCatchNewFile( "mesh", "./resources/models/" );
        return CreateNewMesh( );
    }

    static void TryCatchNewFile( String fileType, String filePath ){
        try {
            ReadNewFile( fileType, filePath );
        } catch ( Exception e ){
            e.printStackTrace( );
            System.exit( 1 );
        }
    }

    static void CheckFileExtension( String isSupported ){
        if( !isSupported.equals( "obj" ) ){
            System.err.println( "Error: File System not supported for mesh data " + isSupported );
            new Exception(  ).printStackTrace( );
            System.exit( 1 );
        }
    }

    static void ReadNewFile( String typeOfProcess, String systemPath ) throws IOException{
        BufferedReader fileReader;
        fileReader = new BufferedReader( new FileReader( systemPath + newFileName ) );
        String line;
        while( ( line = fileReader.readLine( ) ) != null ){
            if( typeOfProcess.equals( "mesh" ) ) {
                ProcessMeshFile( line );
            }
            else if( typeOfProcess.equals( "shader" ) ) {
                ProcessShaderFile( line );
            }
        }
        fileReader.close( );
    }

    static void ProcessMeshFile( String currentLine ){
        String[] tokens = currentLine.split( " " );
        tokens = psUtil.RemoveEmptyStrings( tokens );
        if( tokens.length == 0 || tokens[0].equals( "#" ) ){
            return;
        }
        else if ( tokens[0].equals( "v" ) ){
            vertices.add( new psVertex( new Vector3f( Float.valueOf( tokens[1] ),
                                                      Float.valueOf( tokens[2] ),
                                                      Float.valueOf( tokens[3] ) ) ) );
        }
        else if ( tokens[0].equals( "f" ) ){
            indices.add(Integer.parseInt(tokens[1]) - 1);
            indices.add( Integer.parseInt( tokens[2] ) - 1 );
            indices.add( Integer.parseInt( tokens[3] ) - 1 );
        }
    }

    static void ProcessShaderFile( String currentLine ){
        shaderSource.append( currentLine ).append( "\n" );
    }

    static psMesh CreateNewMesh( ){
        psMesh createdMesh = new psMesh( );
        psVertex[] vertexData = new psVertex[vertices.size( )];
        Integer[] indexData = new Integer[indices.size( )];
        vertices.toArray( vertexData );
        indices.toArray( indexData );
        createdMesh.AddVertices( vertexData, psUtil.ToIntArray( indexData ) );
        return createdMesh;
    }
}