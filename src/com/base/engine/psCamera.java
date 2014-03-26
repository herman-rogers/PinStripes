package com.base.engine;

import org.lwjgl.input.Keyboard;

/**
 * Created by Admin on 3/22/14.
 */
public class psCamera {
    public  static final Vector3f yAxis = new Vector3f( 0,0,0 );
    private Vector3f position;
    private Vector3f forward;
    private Vector3f up;

    public psCamera( ){
        this( new Vector3f( 0,0,0), new Vector3f( 0,0,1 ), new Vector3f( 0,1,0 ) );
    }

    public psCamera( Vector3f position, Vector3f forward, Vector3f up ){
        this.position = position;
        this.forward = forward;
        this.up = up;
    }

    //THIS IS FOR TESTING, CAN BE REMOVED
    public void Input( ){
        float moveAmount = ( float )( 10 * psTime.GetDelta( ) );
        float rotationAmount = ( float )( 1 * psTime.GetDelta( ) );
        if( psInput.GetKey( Keyboard.KEY_W ) ){
            Move( getForward( ), moveAmount );
        }
        if(psInput.GetKey( Keyboard.KEY_S ) ){
            Move(getForward( ), -moveAmount);
        }
        if(psInput.GetKey( Keyboard.KEY_A ) ){
            Move( GetLeft( ), moveAmount);
        }
        if(psInput.GetKey( Keyboard.KEY_D ) ){
            Move( GetRight( ), moveAmount);
        }
        if( psInput.GetKey( Keyboard.KEY_UP ) ){
            RotateX( -rotationAmount );
        }
        if( psInput.GetKey( Keyboard.KEY_DOWN ) ){
            RotateX( rotationAmount );
        }
        if( psInput.GetKey( Keyboard.KEY_LEFT ) ){
            RotateY( -rotationAmount );
        }
        if( psInput.GetKey( Keyboard.KEY_RIGHT ) ){
            RotateY( rotationAmount );
        }
    }

    public void Move( Vector3f direction, float amount ){
        position = position.Add( direction.Multiply( amount ) );
    }

    public void RotateY( float angle ){
        Vector3f horizontalAxis = yAxis.Cross( forward );
        horizontalAxis.Normalize( );
        forward.Rotate( angle, yAxis );
        forward.Normalize( );
        up = forward.Cross( horizontalAxis );
        up.Normalize( );
    }

    public void RotateX( float angle ){
        Vector3f horizontalAxis = yAxis.Cross( forward );
        horizontalAxis.Normalize( );
        forward.Rotate( angle, horizontalAxis );
        forward.Normalize( );
        up = forward.Cross( horizontalAxis );
        up.Normalize( );
    }

    public Vector3f GetLeft( ){
        Vector3f left = forward.Cross( up );
        left.Normalize( );
        return left;
    }

    public Vector3f GetRight( ){
        Vector3f right = up.Cross( forward );
        right.Normalize( );
        return right;
    }

    public Vector3f getPosition( ) {
        return position;
    }

    public void setPosition( Vector3f position ) {
        this.position = position;
    }

    public Vector3f getForward( ) {
        return forward;
    }

    public void setForward( Vector3f forward ) {
        this.forward = forward;
    }

    public Vector3f getUp( ){
        return up;
    }

    public void setUp( Vector3f up ) {
        this.up = up;
    }
}
