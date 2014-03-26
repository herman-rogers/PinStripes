package com.base.engine;

public class Game {
	private psMesh mesh;
    private psShader shader;
    private psMaterial material;
    private psTransform transform;
    private psCamera camera;

	public Game( ){
		mesh = new psMesh( );
//        mesh = psResourceLoader.LoadMesh( "teddybear.obj" );
        material = new psMaterial( psResourceLoader.LoadTexture( "brick1.png" ), new Vector3f( 139, 119, 101 ), 1, 8 );
        shader = psPhongShader.getInstance( );
        camera = new psCamera( );
        Pyramid( );
        transform = new psTransform( );
        transform.SetProjection(70.0f, Window.GetWidth(), Window.GetHeight(), 0.1f, 1000);
        transform.setCamera(camera);
        psPhongShader.setAmbientLight( new Vector3f( 0.3f, 0.3f, 0.3f ) );
//        psPhongShader.SetDirectionalLight(new psDirectionalLight(
//                new psBaseLight(new Vector3f(1.0f, 1.0f, 1.0f), 2.0f),
//                new Vector3f(0, 0, 3)));
        psPointLight testPointOne = new psPointLight( new psBaseLight( new Vector3f( 30, 144, 255 ), 0.1f ),
                                                                       new psAttenuation( 0, 0, 1 ),
                                                                       new Vector3f( -2.0f, 0.0f, 3.0f ) );
        psPointLight testPointTwo = new psPointLight( new psBaseLight( new Vector3f( 244, 164, 96 ), 0.1f ),
                                                                       new psAttenuation( 0, 0, 1 ),
                                                                       new Vector3f( 2.0f, 0.0f, 3.0f ) );
        psPhongShader.SetPointLight( new psPointLight[] { testPointOne, testPointTwo } );

	}

    //Example of how to render models programmatically, can be removed
    private void Pyramid( ){
        psVertex[] vertices = new psVertex[] { new psVertex( new Vector3f( -1.0f, -1.0f, 0.5773f ), new Vector2f( 0.0f, 0.0f ) ),
                                               new psVertex( new Vector3f( 0.0f, -1.0f, -1.15475f ), new Vector2f( 0.5f, 0.0f ) ),
                                               new psVertex( new Vector3f( 1.0f, -1.0f, 0.5773f ),new Vector2f( 1.0f, 0 ) ),
                                               new psVertex( new Vector3f( 0.0f, 1.0f, 0.0f ), new Vector2f( 0.5f, 1.0f ) ) };
        int[] indices = new int[] { 0, 3, 1,
                1, 3, 2,
                2, 3, 0,
                1, 2, 0 };
        mesh.AddVertices( vertices, indices, true );
    }

	public void Input( ){
        camera.Input( );
	}

    float temp = 0.0f; //testing for uniforms, can be removed

	public void Update( ){
        temp += psTime.GetDelta( );
        float sineTemp = ( float )Math.sin( temp );
        transform.SetTranslation( 0, 0, 3 );
        transform.SetRotation(0, sineTemp * 180, 0); //to rotate scene object
        transform.SetScale( 0.8f, 0.8f, 0.8f );
	}

	public void Render( ){
        psRenderUtil.SetClearColor( psTransform.getCamera( ).getPosition( ).Divide( 2048f ) );
		shader.bind( );
        shader.UpdateUniforms(transform.GetTransformation(), transform.GetProjectedTransformation(), material);
        mesh.Draw( );
	}
}
