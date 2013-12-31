package com.base.engine;
import org.lwjgl.input.Keyboard;
public class Game {
	private psMesh mesh;
	public Game( ){
		mesh = new psMesh( );
		psVertex[] data = new psVertex[] { new psVertex( new Vector3f( -1, -1, 0 ) ),
				                           new psVertex( new Vector3f( 0, 1, 0 ) ),
                                    	   new psVertex( new Vector3f( 1, -1, 0 ) )};
		mesh.AddVertices( data );
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
	public void Update( ){
	}
	public void Render( ){
		mesh.Draw( );
	}
}
