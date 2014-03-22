package com.base.engine;

public class psQuaternion {
	private float x;
	private float y;
	private float z;
	private float w;
	public float GetX() {
		return x;
	}
	public void SetX(float x) {
		this.x = x;
	}
	public float GetY() {
		return y;
	}
	public void SetY(float y) {
		this.y = y;
	}
	public float GetZ() {
		return z;
	}
	public void SetZ(float z) {
		this.z = z;
	}
	public float GetW() {
		return w;
	}
	public void SetW(float w) {
		this.w = w;
	}
	public psQuaternion(float x, float y, float z, float w){
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	public float Length( ){
		return ( float )Math.sqrt(x * x + y * y + z * z + w * w);
	}
	public psQuaternion Normalize( ){
		float length = Length( );
		x /= length;
		y /= length;
		z /= length;
		w /= length;
		return this;
	}
	public psQuaternion Conjugate( ){
		return new psQuaternion( -x, -y, -z, w );
	}
	public psQuaternion Multiply( psQuaternion r ){
		float w_ = w * r.GetW( ) - x * r.GetX( ) - y * r.GetY( ) - z * r.GetZ( );
		float x_ = x * r.GetW( ) + w * r.GetX( ) + y * r.GetZ( ) - z * r.GetY( );
		float y_ = y * r.GetW( ) + w * r.GetY( ) + z * r.GetX( ) - x * r.GetZ( );
		float z_ = z * r.GetW( ) + w * r.GetZ( ) + x * r.GetY( ) - y * r.GetX( );
		return new psQuaternion( x_, y_, z_, w_ );
	}
	public psQuaternion Multiply( Vector3f r ){
		float w_ = -x * r.GetX( ) - y * r.GetY( ) - z * r.GetZ( );
		float x_ =  w * r.GetX( ) + y * r.GetZ( ) - z * r.GetY( );
		float y_ =  w * r.GetY( ) + z * r.GetX( ) - x * r.GetZ( );
		float z_ =  w * r.GetZ( ) + x * r.GetY( ) - y * r.GetX( );
		return new psQuaternion( x_, y_, z_, w_ );
	}
}