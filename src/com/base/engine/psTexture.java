package com.base.engine;
import static org.lwjgl.opengl.GL11.*;
/**
 * Created by Admin on 3/25/14.
 */
public class psTexture {
    private int textureID;

    public psTexture(int id){
        this.textureID = id;
    }

    public void Bind( ){
        glBindTexture( GL_TEXTURE_2D, textureID );
    }

    public int GetID( ){
        return textureID;
    }
}