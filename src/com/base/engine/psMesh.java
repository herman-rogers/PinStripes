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
		size = indices.length;
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glBufferData( GL_ARRAY_BUFFER, psUtil.CreateFlippedBuffer( vertices ), GL_STATIC_DRAW );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, ibo );
        glBufferData( GL_ELEMENT_ARRAY_BUFFER, psUtil.CreateFlippedBuffer(indices), GL_STATIC_DRAW );
	}

	public void Draw( ){
		glEnableVertexAttribArray( 0 );
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, psVertex.SIZE * 4, 0 );
        glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, ibo );
        glDrawElements( GL_TRIANGLES, size, GL_UNSIGNED_INT, 0 );
		glDisableVertexAttribArray( 0 );
	}
}
