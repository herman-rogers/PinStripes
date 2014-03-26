package com.base.engine;
import java.io.*;
import java.util.ArrayList;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Created by Admin on 3/21/14.
 */
public class psResourceLoader {
    static ArrayList< psVertex > vertices = new ArrayList< psVertex >( );
    static ArrayList< Integer > indices = new ArrayList< Integer >( );
    static StringBuilder shaderSource;

    public static String LoadShader( String filename ){
        shaderSource = new StringBuilder( );
        DoesFileExist( "shader", "./resources/shaders/" + filename );
        return shaderSource.toString( );
    }

    public static psMesh LoadMesh( String filename ){
        IsFileTypeSupported( GetFileExtension( filename ) );
        DoesFileExist( "mesh", "./resources/models/" + filename );
        return CreateNewMesh( );
    }

    public static psTexture LoadTexture( String fileName  ){
        try {
            int id = TextureLoader.getTexture( GetFileExtension( fileName ), new FileInputStream(new File("./resources/textures/" + fileName))).getTextureID( );
            return new psTexture( id );
        } catch ( Exception e ){
            e.printStackTrace( );
        }
        return null;
    }

    public static String GetFileExtension( String fileName ){
        String[] splitArray = fileName.split( "\\." );
        String extension = splitArray[ splitArray.length - 1 ];
        return extension;
    }

    static void DoesFileExist( String fileType, String filePath ){
        try {
            ReadNewFile(fileType, filePath);
        } catch ( Exception e ){
            e.printStackTrace( );
            System.exit( 1 );
        }
    }

    static void IsFileTypeSupported( String fileType ){
        if( !fileType.equals( "obj" ) ){
            System.err.println( "Error: File System not supported for mesh data " + fileType );
            new Exception(  ).printStackTrace( );
            System.exit( 1 );
        }
    }

    static void ReadNewFile( String typeOfProcess, String systemPath ) throws IOException{
        BufferedReader fileReader;
        fileReader = new BufferedReader( new FileReader( systemPath ) );
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
            indices.add(Integer.parseInt(tokens[1].split( "/" )[0]) - 1);
            indices.add( Integer.parseInt( tokens[2].split( "/" )[0] ) - 1 );
            indices.add( Integer.parseInt( tokens[3].split( "/" )[0] ) - 1 );
            if( tokens.length > 4 ){
                indices.add(Integer.parseInt(tokens[1].split( "/" )[0] ) - 1);
                indices.add( Integer.parseInt( tokens[3].split( "/" )[0] ) - 1 );
                indices.add( Integer.parseInt( tokens[4].split( "/" )[0] ) - 1 );
            }
        }
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

    static void ProcessShaderFile( String currentLine ){
        shaderSource.append( currentLine ).append( "\n" );
    }
}