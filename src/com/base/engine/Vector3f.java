package com.base.engine;

public class Vector3f {
	private float x;
	private float y;
	private float z;
	public Vector3f( float x, float y, float z ){
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public float GetX( ){
		return x;
	}
	public void SetX( float x ){
		this.x = x;
	}
	public float GetY( ){
		return y;
	}
	public void SetY( float y ){
		this.y = y;
	}
	public float GetZ( ){
		return z;
	}
	public void SetZ( float z ){
		this.z = z;
	}
	public float Length( ){
		return ( float )Math.sqrt( x * x + y * y + z * z );
	}
	public float Dot( Vector3f r ){
		return x * r.GetX( ) + y * r.GetY( ) + z * r.GetZ( );
	}

	public Vector3f Cross( Vector3f r ){
		float _x = y * r.GetZ( ) - z * r.GetY( );
		float _y = z * r.GetX( ) - x * r.GetZ( );
		float _z = x * r.GetY( ) - y * r.GetX( );
		return new Vector3f( _x, _y, _z );
	}

	public Vector3f Normalize( ){
		float length = Length( );
		return new Vector3f( x / length, y / length, z / length );
	}

	public Vector3f Rotate( float angle, Vector3f axis ){
        float sinHalfAngle = ( float )Math.sin( Math.toRadians( angle / 2 ) );
        float cosHalfAngle = ( float )Math.cos( Math.toRadians( angle /2 ) );
        float rotationX = axis.GetX( ) * sinHalfAngle;
        float rotationY = axis.GetY( ) * sinHalfAngle;
        float rotationZ = axis.GetZ( ) * sinHalfAngle;
        float rotationW = cosHalfAngle;
        psQuaternion rotation = new psQuaternion( rotationX, rotationY, rotationZ, rotationW );
        psQuaternion conjugate = rotation.Conjugate( );
        psQuaternion w = rotation.Multiply( this ).Multiply( conjugate );
        x = w.GetX( );
        y = w.GetY( );
        z = w.GetZ( );
        return this;
	}

	public Vector3f Add( Vector3f r ){
		return new Vector3f( x + r.GetX( ), y + r.GetY( ), z + r.GetZ( ) );
	}
	public Vector3f Add( float r ){
		return new Vector3f( x + r, y + r, z + r );
	}
	public Vector3f Subtract( Vector3f r ){
		return new Vector3f( x - r.GetX( ), y - r.GetY( ), z - r.GetZ( ) );
	}
	public Vector3f Subtract( float r ){
		return new Vector3f( x - r, y - r, z - r );
	}
	public Vector3f Multiply( Vector3f r ){
		return new Vector3f( x * r.GetX( ), y * r.GetY( ), z * r.GetZ( ) );
	}
	public Vector3f Multiply( float r ){
		return new Vector3f( x * r, y * r, z * r );
	}
	public Vector3f Divide( Vector3f r ){
		return new Vector3f( x / r.GetX( ), y / r.GetY( ), z / r.GetZ( ) );
	}
	public Vector3f Divide( float r ){
		return new Vector3f( x / r, y / r, z / r );
	}
}
