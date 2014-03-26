package com.base.engine;

/**
 * Created by Admin on 3/26/14.
 */
public class psSpotLight {
    private psPointLight pointLight;
    private Vector3f direction;
    private float cutoff;

    public psSpotLight( psPointLight pointLight, Vector3f direction, float cutoff ){
        this.pointLight = pointLight;
        this.direction = direction.Normalize( );
        this.cutoff = cutoff;
    }

    public psPointLight getPointLight() {
        return pointLight;
    }

    public void setPointLight(psPointLight pointLight) {
        this.pointLight = pointLight;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction.Normalize( );
    }

    public float getCutoff() {
        return cutoff;
    }

    public void setCutoff(float cutoff) {
        this.cutoff = cutoff;
    }
}
