package com.base.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class psMesh {
	private int vbo;
    private int ibo;
	private int size;

    public psMesh( String fileName ){
        InitMeshData();
        LoadMesh( fileName );
    }

    public psMesh( psVertex[] vertices, int[] indices ){
        this( vertices, indices, false );
    }

    public psMesh( psVertex[] vertices, int[] indices, boolean calculateNormals ){
        InitMeshData();
        AddVertices(vertices, indices, calculateNormals);
    }

    private void InitMeshData(){
        vbo  = glGenBuffers( );
        ibo = glGenBuffers( );
        size = 0;
    }

	private void AddVertices( psVertex[] vertices, int[] indices, boolean calculateNormals ){
        if( calculateNormals ){
            CalculateNormals( vertices, indices );
        }
		size = indices.length;
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glBufferData( GL_ARRAY_BUFFER, psUtil.CreateFlippedBuffer( vertices ), GL_STATIC_DRAW );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, ibo );
        glBufferData( GL_ELEMENT_ARRAY_BUFFER, psUtil.CreateFlippedBuffer(indices), GL_STATIC_DRAW );
	}

    private void CalculateNormals( psVertex[] vertices, int[] indices ){
        for( int i = 0; i < indices.length; i += 3 ){
            int indexZero = indices[i];
            int indexOne = indices[i + 1];
            int indexTwo = indices[i + 2];
            Vector3f vectorOne = vertices[indexOne].GetPos( ).Subtract( vertices[indexZero].GetPos( ) );
            Vector3f vectorTwo = vertices[indexTwo].GetPos( ).Subtract( vertices[indexZero].GetPos( ) );
            Vector3f normal = vectorOne.Cross( vectorTwo ).Normalize( );
            vertices[indexZero].SetNormal( vertices[indexZero].GetNormal( ).Add( normal ) );
            vertices[indexOne].SetNormal( vertices[indexOne].GetNormal( ).Add( normal ) );
            vertices[indexTwo].SetNormal( vertices[indexTwo].GetNormal( ).Add( normal ) );
        }
        for( int i = 0; i < vertices.length; i++ ){
            vertices[i].SetNormal( vertices[i].GetNormal( ).Normalize( ) );
        }
    }

    private psMesh LoadMesh( String fileName ){
        IsFileTypeSupported( fileName );
        ArrayList< psVertex > vertices = new ArrayList< psVertex >( );
        ArrayList< Integer > indices = new ArrayList< Integer >( );
        try {
            BufferedReader meshReader = new BufferedReader( new FileReader( "./resources/models/" + fileName ) );
            String line;
            while ( ( line = meshReader.readLine( ) ) != null ){
                ProcessMeshFile( line, vertices, indices );
            }
            meshReader.close( );
            CreateNewMesh( vertices, indices );
        } catch ( Exception e ){
            e.printStackTrace( );
            System.exit( 1 );
        }
        return null;
    }

    private void IsFileTypeSupported( String fileName ){
        String[] splitExtension = fileName.split( "\\." );
        String extension = splitExtension[ splitExtension.length - 1 ];
        if( !extension.equals( "obj" ) ){
            System.err.println( "Error: File System not supported for mesh data " + extension );
            new Exception(  ).printStackTrace( );
            System.exit( 1 );
        }
    }

    private void ProcessMeshFile( String line, ArrayList< psVertex > vertices, ArrayList< Integer > indices  ){
        String[] tokens = line.split( " " );
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

    private void CreateNewMesh( ArrayList< psVertex > vertices, ArrayList< Integer > indices ){
        psVertex[] vertexData = new psVertex[vertices.size( )];
        Integer[] indexData = new Integer[indices.size( )];
        vertices.toArray( vertexData );
        indices.toArray( indexData );
        AddVertices(vertexData, psUtil.ToIntArray( indexData ), true );
    }

	public void Draw( ){
		glEnableVertexAttribArray( 0 );
        glEnableVertexAttribArray( 1 );
        glEnableVertexAttribArray( 2 );
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, psVertex.SIZE * 4, 0 );
        glVertexAttribPointer( 1, 2, GL_FLOAT, false, psVertex.SIZE * 4, 12 );
        glVertexAttribPointer( 2, 3, GL_FLOAT, false, psVertex.SIZE * 4, 20 );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, ibo );
        glDrawElements( GL_TRIANGLES, size, GL_UNSIGNED_INT, 0 );
		glDisableVertexAttribArray( 0 );
        glDisableVertexAttribArray( 1 );
        glDisableVertexAttribArray( 2 );
	}
}
