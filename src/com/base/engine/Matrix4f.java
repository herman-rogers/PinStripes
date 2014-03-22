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

    public Matrix4f initTranslation( float x, float y, float z ){
        matrix[0][0] = 1; matrix[0][1] = 0; matrix[0][2] = 0; matrix[0][3] = x;
        matrix[1][0] = 0; matrix[1][1] = 1; matrix[1][2] = 0; matrix[1][3] = y;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = 1; matrix[2][3] = z;
        matrix[3][0] = 0; matrix[3][1] = 0; matrix[3][2] = 0; matrix[3][3] = 1;
        return this;
    }

    public Matrix4f initRotation( float x, float y, float z ){
        Matrix4f rx = new Matrix4f( );
        Matrix4f ry = new Matrix4f( );
        Matrix4f rz = new Matrix4f( );
        x = ( float )Math.toRadians( x );
        y = ( float )Math.toRadians( y );
        z = ( float )Math.toRadians( z );
        rx.matrix[0][0] = 1; rx.matrix[0][1] = 0; rx.matrix[0][2] = 0; rx.matrix[0][3] = 0;
        rx.matrix[1][0] = 0; rx.matrix[1][1] = ( float )Math.cos( x ); rx.matrix[1][2] = -( float )Math.sin( x ); rx.matrix[1][3] = 0;
        rx.matrix[2][0] = 0; rx.matrix[2][1] = ( float )Math.sin( x ); rx.matrix[2][2] = ( float )Math.cos( x ); rx.matrix[2][3] = 0;
        rx.matrix[3][0] = 0; rx.matrix[3][1] = 0; rx.matrix[3][2] = 0; rx.matrix[3][3] = 1;

        ry.matrix[0][0] = ( float )Math.cos( y ); ry.matrix[0][1] = 0; ry.matrix[0][2] = -( float )Math.sin( y ); ry.matrix[0][3] = 0;
        ry.matrix[1][0] = 0; ry.matrix[1][1] = 1; ry.matrix[1][2] = 0; ry.matrix[1][3] = 0;
        ry.matrix[2][0] = ( float )Math.sin( y ); ry.matrix[2][1] = 0; ry.matrix[2][2] = ( float )Math.cos( y ); ry.matrix[2][3] = 0;
        ry.matrix[3][0] = 0; ry.matrix[3][1] = 0; ry.matrix[3][2] = 0; ry.matrix[3][3] = 1;

        rz.matrix[0][0] = ( float )Math.cos( z ); rz.matrix[0][1] = -( float )Math.sin( z ); rz.matrix[0][2] = 0; rz.matrix[0][3] = 0;
        rz.matrix[1][0] = ( float )Math.sin( z ); rz.matrix[1][1] = ( float )Math.cos( z ); rz.matrix[1][2] = 0; rz.matrix[1][3] = 0;
        rz.matrix[2][0] = 0; rz.matrix[2][1] = 0; rz.matrix[2][2] = 1; rz.matrix[2][3] = 0;
        rz.matrix[3][0] = 0; rz.matrix[3][1] = 0; rz.matrix[3][2] = 0; rz.matrix[3][3] = 1;
        matrix = rz.Multiply( ry.Multiply( rx ) ).GetMatrix( );
        return this;
    }

    public Matrix4f initScale( float x, float y, float z ){
        matrix[0][0] = x; matrix[0][1] = 0; matrix[0][2] = 0; matrix[0][3] = 0;
        matrix[1][0] = 0; matrix[1][1] = y; matrix[1][2] = 0; matrix[1][3] = 0;
        matrix[2][0] = 0; matrix[2][1] = 0; matrix[2][2] = z; matrix[2][3] = 0;
        matrix[3][0] = 0; matrix[3][1] = 0; matrix[3][2] = 0; matrix[3][3] = 1;
        return this;
    }

    public Matrix4f Multiply( Matrix4f matrixToMultiply ){
		Matrix4f result = new Matrix4f( );
		for( int i = 0; i < 4; i++ ){
			for( int j = 0; j < 4; j++ ){
				result.Set( i, j, matrix[i][0] * matrixToMultiply.Get( 0, j ) +
							 matrix[i][1] * matrixToMultiply.Get( 1, j ) +
							 matrix[i][2] * matrixToMultiply.Get( 2, j ) +
							 matrix[i][3] * matrixToMultiply.Get( 3, j ) );
			}
		}
		return result;
	}
}
