package com.base.engine;
import java.nio.FloatBuffer;
import org.lwjgl.BufferUtils;
public class psUtil {
	public static FloatBuffer createFloatBuffer( int size ){
		return BufferUtils.createFloatBuffer( size );
	}
	public static FloatBuffer CreateFlippedBuffer( psVertex[] vertices ){
		FloatBuffer buffer = createFloatBuffer( vertices.length * psVertex.SIZE );
		for( int i = 0; i < vertices.length; i++ ){
			buffer.put( vertices[i].GetPos().GetX( ) );
			buffer.put( vertices[i].GetPos().GetY( ) );
			buffer.put( vertices[i].GetPos().GetZ( ) );
		}
		buffer.flip( );
		return buffer;
	}
}
