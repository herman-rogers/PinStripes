package com.base.engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
public class psMesh {
	private int vbo;
	private int size;
	public psMesh( ){
		vbo  = glGenBuffers( );
		size = 0;
	}
	public void AddVertices( psVertex[] vertices ){
		size = vertices.length; //* psVertex.SIZE
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glBufferData( GL_ARRAY_BUFFER, psUtil.CreateFlippedBuffer( vertices ), GL_STATIC_DRAW );
	}
	public void Draw( ){
		glEnableVertexAttribArray( 0 );
		glBindBuffer( GL_ARRAY_BUFFER, vbo );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, psVertex.SIZE * 4, 0 );
		glDrawArrays( GL_TRIANGLES, 0, size );
		glDisableVertexAttribArray( 0 );
	}
}
