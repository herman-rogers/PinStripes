package com.base.engine;
import org.newdawn.slick.opengl.TextureLoader;
import java.io.File;
import java.io.FileInputStream;

import static org.lwjgl.opengl.GL11.*;
/**
 * Created by Admin on 3/25/14.
 */
public class psTexture {
    private int textureID;

    public psTexture( String fileName ){
        this( LoadTexture( fileName ) );
    }

    public psTexture(int id){
        this.textureID = id;
    }

    public void Bind( ){
        glBindTexture( GL_TEXTURE_2D, textureID );
    }

    public int GetID( ){
        return textureID;
    }

    private static int LoadTexture( String fileName ){
        String[] splitArray = fileName.split( "\\." );
        String extension = splitArray[ splitArray.length - 1 ];
        try{
            int textureID = TextureLoader.getTexture( extension, new FileInputStream(
                            new File ( "./resources/textures/" + fileName ) ) ).getTextureID( );
            return textureID;
        } catch ( Exception e ){
            e.printStackTrace( );
            System.exit( 1 );
        }
        return 0;
    }
}