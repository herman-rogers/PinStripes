package com.base.engine;

public class Matrix4f {
	private float[][] matrix;
	public Matrix4f( ){
		matrix = new float[4][4];
	}
	public float[][] GetMatrix( ){
		return matrix;
	}
	public void SetMatrix( float[][] matrix ){
		this.matrix = matrix;
	}
	public float Get( int x, int y ){
		return matrix[x][y];
	}
	public void Set( int x, int y, float value ){
		matrix[x][y] = value;
	}
	public Matrix4f InitIdentity( ){
		matrix[0][0] = 1; matrix[0][1] = 0; matrix[0][2] = 0; matrix[0][3] = 0;
		matrix[1][0] = 0; matrix[1][1] = 1; matrix[1][2] = 0; matrix[1][3] = 0;
		matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1; matrix[2][3] = 0;
		matrix[3][0] = 0; matrix[3][1] = 0; matrix[3][2] = 0; matrix[3][3] = 1;
		return this;
	}
	public Matrix4f Multiply( Matrix4f r ){
		Matrix4f result = new Matrix4f( );
		for( int i = 0; i < 4; i++ ){
			for( int j = 0; j < 4; j++ ){
				r.Set( i, j, matrix[i][0] * r.Get( 0, j ) +
							 matrix[i][1] * r.Get( 1, j ) +
							 matrix[i][2] * r.Get( 2, j ) +
							 matrix[i][3] * r.Get( 3, j ) );
			}
		}
		return result;
	}
}
