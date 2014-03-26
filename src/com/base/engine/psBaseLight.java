package com.base.engine;

/**
 * Created by Admin on 3/25/14.
 */
public class psBaseLight {
    private Vector3f color;
    private float intensity;

    public psBaseLight( Vector3f color, float intensity ){
        this.color = color;
        this.intensity = intensity;
    }

    public float getIntensity( ){
        return intensity;
    }

    public void setIntensity( float intensity ) {
        this.intensity = intensity;
    }

    public Vector3f getColor( ){
        return color;
    }

    public void SetColor( Vector3f color ) {
        this.color = color;
    }
}
