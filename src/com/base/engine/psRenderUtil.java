package com.base.engine;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class psRenderUtil {
	public static void ClearScreen( ){
		//TODO: Stencil Buffer
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );
	}

    public static void SetTexutres( boolean enabled ){
        if( enabled ){
            glEnable( GL_TEXTURE_2D );
        } else {
            glEnable( GL_TEXTURE_2D );
        }
    }

    public static void UnbindTextures( ){
        glBindTexture( GL_TEXTURE_2D, 0 );
    }

    public static void SetClearColor( Vector3f color ){
        glClearColor( color.GetX( ), color.GetY( ), color.GetZ( ), 1.0f );
    }

	public static void InitGraphics( ){
		glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		glFrontFace( GL_CW );
		glCullFace( GL_BACK );
		glEnable( GL_CULL_FACE );
		glEnable( GL_DEPTH_TEST );
        glEnable( GL_DEPTH_CLAMP );
        glEnable( GL_TEXTURE_2D );
	}
}