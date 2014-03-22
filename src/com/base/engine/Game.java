package com.base.engine;
import org.lwjgl.input.Keyboard;
public class Game {
	private psMesh mesh;
    private psShader shader;
    private psTransform transform;

	public Game( ){
//		mesh = new psMesh( );
        mesh = psResourceLoader.LoadMesh( "teddyBear.obj" );
        shader = new psShader( );
        transform = new psTransform( );
        shader.addVertexShader( psResourceLoader.LoadShader("basicVertex.vshader") );
        shader.addFragmentShader( psResourceLoader.LoadShader("basicFragment.fshader") );
        shader.compileShader( );
        shader.addUniformVariable( "transform" );
	}

    private void Triangle( ){
        psVertex[] vertices = new psVertex[] { new psVertex( new Vector3f( -1, -1, 0 ) ),
                new psVertex( new Vector3f( 0, 1, 0 ) ),
                new psVertex( new Vector3f( 1, -1, 0 ) )};
        int[] indices = new int[] { 0,1,2 };
        mesh.AddVertices( vertices, indices );
    }

    private void Pyramid( ){
        psVertex[] vertices = new psVertex[] { new psVertex( new Vector3f( -1, -1, 0 ) ),
                                               new psVertex( new Vector3f( 0, 1, 0 ) ),
                                               new psVertex( new Vector3f( 1, -1, 0 ) ),
                                               new psVertex( new Vector3f( 0, -1, 1 ) ) };
        int[] indices = new int[] { 0, 1, 3,
                                    3, 1, 2,
                                    2, 1, 0,
                                    0, 2, 3 };
        mesh.AddVertices( vertices, indices );
    }

	public void Input( ){
		if ( psInput.GetKeyDown(Keyboard.KEY_SPACE) ){
			System.out.println( "We've just pressed Down!" );
		}
		if ( psInput.GetKeyUp(Keyboard.KEY_SPACE) ){
			System.out.println( "We've just pressed Up!" );
		}
		if ( psInput.GetMouseUp( 0 ) ){
			System.out.println( "We've just released Right Mouse at " + psInput.GetMousePosition(  ).toString( ) );
		}
	}

    float temp = 0.0f; //testing for uniforms, can be removed

	public void Update( ){
        temp += psTime.GetDelta();
        float sineTemp = ( float )Math.sin( temp );
        transform.setTranslation( sineTemp, 0, 0 );
        transform.setRotation( 0, sineTemp * 180, 0 );
        transform.setScale( 0.05f,0.05f,0.05f );
	}

	public void Render( ){
		shader.bind( );
        shader.setUniformMatrix("transform", transform.getTransformation());
        mesh.Draw( );
	}
}
