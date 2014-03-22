package com.base.engine;

public class psVertex {
	public static final int SIZE = 3;
	private Vector3f pos;
	public psVertex( Vector3f pos ){
		this.pos = pos;
	}
	public Vector3f GetPos( ){
		return pos;
	}
	public void SetPos( Vector3f pos ){
		this.pos = pos;
	}
}
