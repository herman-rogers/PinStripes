package com.base.engine;
public class MainComponent {
	public static final double frameCap = 5000.0;
	static final int           screenWidth = 800;
	static final int           screenHeight = 600;
	static final String        screenTitle	= "PinStripes";
	private boolean            isRunning;
	private Game game;
	public MainComponent( ){
		RenderUtil.InitGraphics( );
		isRunning = false;
		game = new Game( );
	}
	public void Start( ){
		if ( isRunning ){
			return;
		}
		Run( );
	}
	public void Stop( ){
		if( !isRunning ){
			return;
		}
		isRunning = false;
	}
	private void Run( ){
		isRunning = true;
		long frames 			= 0;
		long frameCounter 		= 0;
		final double frameTime	= 1 / frameCap;
		long lastTime 			= Time.GetTime( );
		double unprocessedTime 	= 0;
		while ( isRunning ){
			boolean render = false;
			long startTime 		= Time.GetTime( );
			long passedTime		= startTime - lastTime;
			lastTime = startTime;
			unprocessedTime += passedTime / ( double )Time.second;
			while ( unprocessedTime > frameTime ){
				render = true;
				unprocessedTime -= frameTime;
				frameCounter += passedTime;
				if ( Window.IsCloseRequested( )  ){
					Stop( );
				}
				Time.SetDelta( frameTime );
				game.Input( );
				psInput.update( );
				game.Update();
				if ( frameCounter >= Time.second ){
				//	System.out.println( frames ); //Use this to Debug the Current FrameRate
					frames 		 = 0;
					frameCounter = 0;
				}
			}
			if ( render ){
				Render( );
				frames ++;
			} else {
				try {
					Thread.sleep( 1 );
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	private void Render( ){
		RenderUtil.ClearScreen( );
		game.Render( );
		Window.Render( );
	}
	private void CleanUp( ){
		Window.Dispose( );
	}
	public static void main( String[] args ){
		Window.CreateWindow( screenWidth, screenHeight, screenTitle );
		MainComponent game = new MainComponent( );
		game.Start( );
	}
}