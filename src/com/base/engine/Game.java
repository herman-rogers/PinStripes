package com.base.engine;

import org.lwjgl.input.Keyboard;

public class Game {
	private psMesh mesh;
    private psShader shader;
    private psMaterial material;
    private psTransform transform;
    private psCamera camera;
    //Point Lights
    psPointLight testPointLightOne = new psPointLight( new psBaseLight( new Vector3f( 30, 144, 255 ), 1.0f ),
            new psAttenuation( 0, 0, 1 ),
            new Vector3f( -2.0f, 0.0f, 5.0f ), 20.0f );
    psPointLight testPointLightTwo = new psPointLight( new psBaseLight( new Vector3f( 244, 164, 96 ), 1.0f ),
            new psAttenuation( 0, 0, 1 ),
            new Vector3f( 2.0f, 0.0f, 7.0f ), 20.0f );
    //Spot Light
    psSpotLight testSpotLight = new psSpotLight( new psPointLight( new psBaseLight( new Vector3f( 244, 164, 96 ), 3.0f ),
            new psAttenuation( 1, 1, 1 ),
            new Vector3f( 2.0f, 0.0f, 7.0f ), 30.0f ), new Vector3f( 1,1,1 ), 0.7f );

	public Game( ){
//        mesh = new psMesh( "teddybear.obj" );
        material = new psMaterial( new psTexture( "stone.png" ), new Vector3f( 139, 119, 101 ), 0.1f, 8 );
        shader = psPhongShader.getInstance( );
        camera = new psCamera( );
        Platform( );
        transform = new psTransform( );
        transform.SetProjection(70.0f, Window.GetWidth(), Window.GetHeight(), 0.1f, 1000);
        transform.setCamera(camera);
        psPhongShader.setAmbientLight( new Vector3f( 0.3f, 0.3f, 0.3f ) );
//        psPhongShader.SetDirectionalLight(new psDirectionalLight(
//                new psBaseLight(new Vector3f(1.0f, 1.0f, 1.0f), 2.0f),
//                new Vector3f(0, -1, 5)));

        psPhongShader.SetPointLight( new psPointLight[] {testPointLightOne, testPointLightTwo } );
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
        mesh = new psMesh( vertices, indices, true );
    }

    private void Platform( ){
        float fieldDepth = 10.0f;
        float fieldWidth = 10.0f;
        psVertex[] vertices = new psVertex[] { new psVertex( new Vector3f( -fieldWidth, 0.0f, -fieldDepth ), new Vector2f( 0.0f, 0.0f ) ),
                new psVertex( new Vector3f( -fieldWidth, 0.0f, fieldDepth * 3 ), new Vector2f( 0.0f, 1.0f ) ),
                new psVertex( new Vector3f( fieldWidth * 3, 0.0f, -fieldDepth ),new Vector2f( 1.0f, 0.0f ) ),
                new psVertex( new Vector3f( fieldWidth * 3, 0.0f, fieldDepth * 3 ), new Vector2f( 1.0f, 1.0f ) ) };
        int indices[] = { 0,1,2,
                         2, 1, 3 };
        mesh = new psMesh( vertices, indices, true );
    }

    boolean tempToggle = true;

	public void Input( ){
        camera.Input( );
        if( psInput.GetKeyDown( Keyboard.KEY_SPACE ) && tempToggle ){
            System.out.println( "LIGHT ON" );
            testSpotLight = new psSpotLight( new psPointLight( new psBaseLight( new Vector3f( 244, 164, 96 ), 3.0f ),
                    new psAttenuation( 1, 1, 1 ),
                    new Vector3f( 2.0f, 0.0f, 7.0f ), 30.0f ), new Vector3f( 1,1,1 ), 0.7f );
            psPhongShader.SetSpotLights( new psSpotLight[]{ testSpotLight } );
            tempToggle = false;
        }
        else if( psInput.GetKeyDown( Keyboard.KEY_SPACE ) && !tempToggle ){
            System.out.println( "Light OFF" );
            testSpotLight = new psSpotLight( new psPointLight( new psBaseLight( new Vector3f( 0, 0, 0 ), 3.0f ),
                    new psAttenuation( 1, 1, 1 ),
                    new Vector3f( 2.0f, 0.0f, 7.0f ), 30.0f ), new Vector3f( 1,1,1 ), 0.7f );
            psPhongShader.SetSpotLights( new psSpotLight[]{ testSpotLight } );
            tempToggle = true;
        }
	}

    float temp = 0.0f; //testing for uniforms, can be removed

	public void Update( ){
        temp += psTime.GetDelta( );
        float sineTemp = ( float )Math.sin( temp );
        transform.SetTranslation( 0, -5, 5 );
        transform.SetScale( 1.5f, 1.5f, 1.5f );
        testSpotLight.getPointLight( ).setPosition( camera.getPosition( ) );
        testSpotLight.setDirection( camera.getForward( ) );
//        transform.SetRotation(0, sineTemp * 180, 0); //to rotate scene object
        testPointLightOne.setPosition( new Vector3f( 1, -0.5f, 8.0f * ( float )( sineTemp + 1.0/2.0 ) + 10 ) );
        testPointLightTwo.setPosition( new Vector3f( 10, -0.5f,8.0f * ( float )( Math.cos( temp ) + 1.0/2.0 ) + 10 ) );

	}

	public void Render( ){
        psRenderUtil.SetClearColor( psTransform.getCamera( ).getPosition( ).Divide( 2048f ) );
		shader.bind( );
        shader.UpdateUniforms(transform.GetTransformation(), transform.GetProjectedTransformation(), material);
        mesh.Draw( );
	}
}
