package com.base.engine;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;

public class psUtil {
	public static FloatBuffer CreateFloatBuffer(int size){
		return BufferUtils.createFloatBuffer( size );
	}
    public static IntBuffer CreateIntBuffer(int size){ return BufferUtils.createIntBuffer( size ); }

    public static FloatBuffer CreateFlippedBuffer( psVertex[] vertices ){
		FloatBuffer buffer = CreateFloatBuffer(vertices.length * psVertex.SIZE);
		for( int i = 0; i < vertices.length; i++ ){
			buffer.put( vertices[i].GetPos().GetX( ) );
			buffer.put( vertices[i].GetPos().GetY( ) );
			buffer.put( vertices[i].GetPos().GetZ( ) );
		}
		buffer.flip( );
		return buffer;
	}

    public static FloatBuffer CreateFlippedBuffer(Matrix4f matrix){
        FloatBuffer buffer = CreateFloatBuffer(4 * 4);
        for( int i = 0; i < 4; i++ ){
            for( int j = 0; j < 4; j++ ){
                buffer.put( matrix.Get( i, j ) );
            }
        }
        buffer.flip( );
        return buffer;
    }

    public static IntBuffer CreateFlippedBuffer(int... values){
        IntBuffer buffer = CreateIntBuffer(values.length);
        buffer.put( values );
        buffer.flip( );
        return buffer;
    }

    public static String GetOpenGLVersion(){
        return glGetString( GL_VERSION );
    }

    public static String[] RemoveEmptyStrings( String[] data ){
        ArrayList< String > result = new ArrayList< String >( );
        for( int i = 0; i < data.length; i++ ){
            if( !data[i].equals( "" ) ){
                result.add( data[i] );
            }
        }
        String[] res = new String[result.size( )];
        result.toArray( res );
        return  res;
    }

    public  static  int[] ToIntArray( Integer[] data ){
        int[] result = new int[data.length];
        for( int i = 0; i < data.length; i++ ){
            result[i] = data[i].intValue( );
        }
        return result;
    }
}
