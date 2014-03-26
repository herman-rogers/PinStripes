package com.base.engine;

/**
 * Created by Admin on 3/25/14.
 */
public class psDirectionalLight {
    private psBaseLight base;
    private Vector3f direction;

    public psDirectionalLight(psBaseLight base, Vector3f direction){
        this.base = base;
        this.direction = direction.Normalize( );
    }
    public psBaseLight getBase() {
        return base;
    }

    public void setBase(psBaseLight base) {
        this.base = base;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

}
