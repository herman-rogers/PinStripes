package com.base.engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class psMesh {
	private int vbo;
    private int ibo;
	private int size;
	public psMesh( ){
		vbo  = glGenBuffers( );
        ibo = glGenBuffers( );
		size = 0;
	}

    public void AddVertices( psVertex[] vertices, int[] indices ){
        AddVertices( vertices, indices, false );
    }

	public void AddVertices( psVertex[] vertices, int[] indices, boolean calculateNormals ){
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
