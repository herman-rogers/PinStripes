package com.base.engine;

/**
 * Created by Admin on 3/21/14.
 */
public class psTransform {
    private static psCamera camera;
    private static float zNear;
    private static float zFar;
    private static float width;
    private static float height;
    private static float fov;
    private Vector3f translation;
    private Vector3f rotation;
    private Vector3f scale;

    public psTransform( ){
        translation = new Vector3f( 0,0,0 );
        rotation = new Vector3f( 0,0,0 );
        scale = new Vector3f( 1,1,1 );
    }

    public Matrix4f GetTransformation(){
        Matrix4f translationMatrix = new Matrix4f( ).InitTranslation(translation.GetX(),
                translation.GetY(),
                translation.GetZ());
        Matrix4f rotationMatrix = new Matrix4f( ).InitRotation(rotation.GetX(),
                rotation.GetY(),
                rotation.GetZ());
        Matrix4f scaleMatrix = new Matrix4f( ).InitScale(scale.GetX(),
                scale.GetY(),
                scale.GetZ());
        return translationMatrix.Multiply( rotationMatrix.Multiply( scaleMatrix ) );
    }

    public Matrix4f GetProjectedTransformation( ){
        Matrix4f transformationMatrix = GetTransformation( );
        Matrix4f projectionMatrix = new Matrix4f( ).InitProjection( fov, width, height, zNear, zFar );
        Matrix4f cameraRotation = new Matrix4f( ).InitCamera(  camera.getForward( ), camera.getUp( ) );
        Matrix4f cameraTranslation = new Matrix4f( ).InitTranslation( -camera.getPosition( ).GetX( ),
                                                                      -camera.getPosition( ).GetY( ),
                                                                      -camera.getPosition( ).GetZ( ) );
        return projectionMatrix.Multiply( cameraRotation.Multiply( cameraTranslation.Multiply( transformationMatrix ) ) );
    }

    public Vector3f GetTranslation() {
        return translation;
    }

    public void SetProjection( float fov, float width, float height, float zNear, float zFar ){
        psTransform.fov = fov;
        psTransform.width = width;
        psTransform.height = height;
        psTransform.zFar = zFar;
        psTransform.zNear = zNear;
    }

    public static psCamera getCamera() {
        return camera;
    }

    public static void setCamera(psCamera camera) {
        psTransform.camera = camera;
    }

    public void SetTranslation(Vector3f translation) {
        this.translation = translation;
    }

    public void SetTranslation(float x, float y, float z) {
        this.translation = new Vector3f( x, y, z );
    }

    public Vector3f GetRotation(){
        return rotation;
    }

    public void SetRotation(Vector3f rotation) {
        this.rotation = rotation;
    }

    public void SetRotation(float x, float y, float z){
        this.rotation = new Vector3f( x, y , z );
    }

    public Vector3f GetScale() {
        return scale;
    }

    public void SetScale(Vector3f scale) {
        this.scale = scale;
    }

    public void SetScale(float x, float y, float z){
        this.scale = new Vector3f( x , y , z );
    }
}
