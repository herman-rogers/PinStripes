package com.base.engine;

/**
 * Created by Admin on 3/25/14.
 */
public class psMaterial {
    private psTexture texture;
    private Vector3f color;
    private float specularIntensity;
    private float specularExponent;

    public psMaterial( psTexture texture ){
        this( texture, new Vector3f( 1,1,1 ) );
    }

    public psMaterial( psTexture texture, Vector3f color ){
        this( texture, color, 2, 32 );
    }

    public psMaterial( psTexture texture, Vector3f color, float specularIntensity, float specularExponent ){
        ConvertRGBValues( color );
        this.texture = texture;
        this.color = color;
        this.specularIntensity = specularIntensity;
        this.specularExponent = specularExponent;
    }

    public psTexture getTexture() {
        return texture;
    }

    public void setTexture(psTexture texture) {
        this.texture = texture;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public float getSpecularIntensity() {
        return specularIntensity;
    }

    public void setSpecularIntensity(float specularIntensity) {
        this.specularIntensity = specularIntensity;
    }

    public float getSpecularExponent() {
        return specularExponent;
    }

    public void setSpecularExponent(float specularExponent) {
        this.specularExponent = specularExponent;
    }

    void ConvertRGBValues( Vector3f colorsToConvert ){
        colorsToConvert.SetX( colorsToConvert.GetX( ) / 255 );
        colorsToConvert.SetY(colorsToConvert.GetY() / 255);
        colorsToConvert.SetZ(colorsToConvert.GetZ() / 255);
    }
}
