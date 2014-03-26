package com.base.engine;

public class psVertex {
	public static final int SIZE = 8;
	private Vector3f position;
    private Vector2f textureCoordinate;
    private Vector3f normal;

	public psVertex( Vector3f position ){
        this( position, new Vector2f( 0,0 ) );
	}

    public psVertex( Vector3f position, Vector2f textureCoordinate ){
        this( position, textureCoordinate, new Vector3f( 0,0,0 ) );
    }

    public psVertex( Vector3f position, Vector2f textureCoordinate, Vector3f normal ){
        this.position = position;
        this.textureCoordinate = textureCoordinate;
        this.normal = normal;
    }

	public Vector3f GetPos( ){
		return position;
	}

	public void SetPos( Vector3f position ){
		this.position = position;
	}

    public Vector2f GetTextureCoordinate( ){
        return textureCoordinate;
    }

    public void SetTextureCoordinate( Vector2f textureCoordinate ){
        this.textureCoordinate = textureCoordinate;
    }

    public Vector3f GetNormal() {
        return normal;
    }

    public void SetNormal(Vector3f normal) {
        this.normal = normal;
    }
}
