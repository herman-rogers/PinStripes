package com.base.engine;

public class Vector2f {
	private float x;
	private float y;
	public Vector2f( float x, float y ){
		this.x = x;
		this.y = y;
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
	public float Length( ){
		return ( float )Math.sqrt( x * x + y * y);
	}
	public float Dot( Vector2f r ){
		return x * r.GetX() + y * r.GetY();
	}
	public Vector2f Normalize( ){
		float length = Length( );
		x /= length;
		y /= length;
		return this;
	}
	public Vector2f Rotate( float angle ){
		double radians 	= ( double )Math.toRadians( angle );
		double cosine 	= ( double )Math.cos( radians );
		double sine		= Math.sin( radians );
		return new Vector2f( ( float )( x * cosine - y * sine), ( float )( x * sine + y * cosine) );
	}
	public Vector2f Add( Vector2f r ){
		return new Vector2f( x + r.GetX( ), y + r.GetY( ) );
	}
	public Vector2f Add( float r ){
		return new Vector2f( x + r, y + r );
	}
	public Vector2f Subtract( Vector2f r ){
		return new Vector2f( x - r.GetX( ), y - r.GetY( ) );
	}
	public Vector2f Subtract( float r ){
		return new Vector2f( x - r, y - r );
	}
	public Vector2f Multiply( Vector2f r ){
		return new Vector2f( x * r.GetX( ), y * r.GetY( ) );
	}
	public Vector2f Multiply( float r ){
		return new Vector2f( x * r, y * r );
	}
	public Vector2f Divide( Vector2f r ){
		return new Vector2f( x / r.GetX( ), y / r.GetY( ) );
	}
	public Vector2f Divide( float r ){
		return new Vector2f( x / r, y / r );
	}
	public String toString( ){
		return "(" + x + " " + y + ")";
	}
}
