/*
 *
 * Created: Jun  7 2006
 *
 * Copyright (C) 1999-2000 Fabien Sanglard
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */


// 
package rtype;

// Mostly commented out because LWJGL do not support DEVil anymore :( !

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
//import org.lwjgl.devil.IL;
//import org.lwjgl.devil.ILU;
import org.lwjgl.opengl.GL11;

import rtype.entity.IEntity;
import rtype.entity.Logger;

public class DevilTextureLoader //implements ITextureLoader 
{
	/*
	private static Texture[] textures = new Texture[1024];
	private static Texture[][] animations = new Texture[1024][1024];
	
	public DevilTextureLoader () 
	{
		try
		{
		  IL.create();
          ILU.create();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 // (non-Javadoc)
	 // @see rtype.ITextureLoader#init()
	 
	public void init()
	{
		
		textures[IEntity.PLANET] = loadTexture("Data/Stage1Layer1.png",550,473,512,512);
		textures[IEntity.SPACE_TRASH_1] = loadTexture("Data/Stage1Layer1.png",256,438,256,256); // Big huge spaceship with 5 reactors
		textures[IEntity.SPACE_TRASH_2] = loadTexture("Data/Stage1Layer1.png",257,700,256,256); 
		textures[IEntity.SPACE_TRASH_3] = loadTexture("Data/Stage1Layer1.png",373,956,64,64); // Round trash
		textures[IEntity.SPACE_TRASH_4] = loadTexture("Data/Stage1Layer1.png",492,102,256,128); // Sattelite trash
		textures[IEntity.STAR_1] = loadTexture("Data/Stage1Layer1.png",612,1011,8,8);
		textures[IEntity.STAR_2] = loadTexture("Data/Stage1Layer1.png",649,990,16,32);
		textures[IEntity.STAR_3] = loadTexture("Data/Stage1Layer1.png",674,994,16,16);
		textures[IEntity.STAR_4] = loadTexture("Data/Stage1Layer1.png",716,1004,16,16);
		textures[IEntity.STAR_5] = loadTexture("Data/Stage1Layer1.png",738,990,32,32);
		textures[IEntity.STAR_6] = loadTexture("Data/Stage1Layer1.png",780,994,16,16);
		textures[IEntity.SCANLINE] = loadTexture("Data/Scanlines.png",0,0,32,32);
		textures[IEntity.BULLET] = loadTexture("Data/BulletSet1.png",32,00,32,32);
		textures[IEntity.FORCEBLAST] = loadTexture("Data/forceblast.png",0,0,128,64);
		textures[IEntity.ORB_BEAM] = loadTexture("Data/GlowBullets.png",32,0,32,32);
		textures[IEntity.ENEMY_BULLET] = loadTexture("Data/BulletSet1.png",0,0,32,32);
		textures[IEntity.ENEMY_PIECE_1] = loadTexture("Data/SpaceTrash.png",00,00,32,32);
		textures[IEntity.ENEMY_PIECE_2] = loadTexture("Data/SpaceTrash.png",32,00,32,32);
		textures[IEntity.ENEMY_PIECE_3] = loadTexture("Data/SpaceTrash.png",64,00,32,32);
		textures[IEntity.ENEMY_PIECE_4] = loadTexture("Data/SpaceTrash.png",96,00,32,32);
		textures[IEntity.ENEMY_PIECE_5] = loadTexture("Data/SpaceTrash.png",00,32,32,32);
		textures[IEntity.ENEMY_PIECE_6] = loadTexture("Data/SpaceTrash.png",32,32,32,32);
		textures[IEntity.ENEMY_PIECE_7] = loadTexture("Data/SpaceTrash.png",64,32,32,32);
		textures[IEntity.ENEMY_PIECE_8] = loadTexture("Data/SpaceTrash.png",96,32,32,32);
		textures[IEntity.BULLET_RAPID_FIRE] = loadTexture("Data/BulletSet1.png",00,64,32,32);
		textures[IEntity.BULLET_MULTI_SHOOT] = loadTexture("Data/BulletSet1.png",0,32,32,32);
		textures[IEntity.BULLET_BASE] = loadTexture("Data/BulletSet1.png",32,32,32,32);
		textures[IEntity.SPACE_TRASH_5] = loadTexture("Data/Stage1Layer1.png",148,721,64,64); // Extreme left trash
		
		animations[IEntity.PLAYER_SHIP] = loadAnimation("Data/PlayerShip.png",4,4,128,128);
		animations[IEntity.GREEN_ORB] = loadAnimation("Data/Orb1.png",8,2,64,64);
		animations[IEntity.BLUE_ORB] = loadAnimation("Data/Orb1.png",8,2,64,64,0,128);
		animations[IEntity.PINK_ORB] = loadAnimation("Data/Orb1.png",8,2,64,64,0,256);
		animations[IEntity.RED_ORB] = loadAnimation("Data/Orb1.png",8,2,64,64,0,384);
		animations[IEntity.FORCE_CHARGE] = loadAnimation("Data/forcecharge.png",8,4,64,64);
		animations[IEntity.PLAYER_SPEED] = loadAnimation("Data/PlayerSpeed.png",8,4,64,64);
		animations[IEntity.EXPLOSION1] = loadAnimation("Data/Explosion2.png",8,4,64,64);
		animations[IEntity.EXPLOSION2] = loadAnimation("Data/ExplosionSmall.png",8,4,128,128);
		animations[IEntity.LADYBIRD]   = loadAnimation("Data/ladybird.png",4,4,64,64);
		animations[IEntity.FIRE_BALL]   = loadAnimation("Data/FireBall.png",4,4,128,128);
		animations[IEntity.FONT]   = loadAnimation("Data/Fonts.png",16,16,32,32);
		animations[IEntity.BULLET_HIT_YELLOW] = loadAnimation("Data/BulletHit.png",4,2,32,32);
		animations[IEntity.BULLET_HIT_GREEN]  = loadAnimation("Data/BulletHit2.png",4,2,32,32);
		animations[IEntity.BULLET_HIT_BLUE]   = loadAnimation("Data/BulletHit3.png",4,2,32,32);
		animations[IEntity.BIT_UPGRADE]   = loadAnimation("Data/BitUpgrade.png",4,2,32,32);
		animations[IEntity.BONUS_MULTI_SHOOT_ORB]        = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,00);
		animations[IEntity.BONUS_LIGHTNING_ORB]          = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,64);
		animations[IEntity.BONUS_RAPID_SHOOT_ORB]  = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,128);
		animations[IEntity.BONUS_GRAVITY_ORB]    = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,192);
		animations[IEntity.BONUS_SPEED]       = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,256);
		animations[IEntity.BONUS_CRYSTAL_ORB]= loadAnimation("Data/PowerUp1.png",8,1,64,64,00,320);
		animations[IEntity.BONUS_BOOSTER] = loadAnimation("Data/PowerUp1.png",8,1,64,64,00,384);
		animations[IEntity.IMPLOSION]   = loadAnimation("Data/Implosion.png",8,4,64,64);
		animations[IEntity.MISSILE]   = loadAnimation("Data/HomingMissile.png",4,4,64,64);
		animations[IEntity.SMOKE]   = loadAnimation("Data/SmokePuff.png",4,4,32,32);
	}
	
	// (non-Javadoc)
	// @see rtype.ITextureLoader#getTexture(int)
	 
	public  Texture getTexture(int textureID)
	{
		return textures[textureID];
	}
	
	 // (non-Javadoc)
	 // @see rtype.ITextureLoader#getAnimation(int)
	 
	public  Texture[] getAnimation(int animationID)
	{
		return animations[animationID];
	}
	
	private  Texture loadTexture(String path,int xOffset, int yOffSet, int textWidth, int textHeight) {
		System.out.println("Loading texture:"+path);
		int ilType = 0;
        int glType = 0;
        
        IntBuffer image = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        IL.ilGenImages(image);
        IL.ilBindImage(image.get(0));
        IL.ilLoadImage(path);

        int bytesPerPixel = IL.ilGetInteger(IL.IL_IMAGE_BYTES_PER_PIXEL);
        ByteBuffer scratch = ByteBuffer.allocateDirect(textWidth * textHeight  * bytesPerPixel);
        
        if(bytesPerPixel == 3) 
        {
            ilType = IL.IL_RGB;
            glType = GL11.GL_RGB;
        }
        else 
        	if(bytesPerPixel == 4) 
        	{
        	
	            ilType = IL.IL_RGBA;
	            glType = GL11.GL_RGBA;
	        }
        
        
        IL.ilCopyPixels(xOffset,yOffSet,0,textWidth,textHeight, 1,ilType, IL.IL_BYTE, scratch);
        
        // Create A IntBuffer For Image Address In Memory
        IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL11.glGenTextures(buf); // Create Texture In OpenGL

         // Create Nearest Filtered Texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,glType,textWidth,textHeight, 0,glType, GL11.GL_UNSIGNED_BYTE, scratch);

        Texture newTexture = new Texture();
        newTexture.textureId = buf.get(0);     // Return Image Addresses In Memory
        newTexture.textureHeight = textHeight;
        newTexture.textureWidth = textWidth;
        
        return newTexture;
    }

	private  Texture[] loadAnimation(String path,int cols, int rows, int textWidth, int textHeight) 
	{
		return loadAnimation(path,cols,rows,textWidth,textHeight,0,0);
	}

	private  Texture[] loadAnimation(String path,int cols, int rows, int textWidth, int textHeight, int xOffSet, int yOffSet) 
	{
		System.out.println("Loading animation:"+path);
		Texture[] toReturntextures = new Texture[cols*rows];
		
		int ilType = 0;
        int glType = 0;
        
        IntBuffer image = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        IL.ilGenImages(image);
        IL.ilBindImage(image.get(0));
        IL.ilLoadImage(path);

        int bytesPerPixel = IL.ilGetInteger(IL.IL_IMAGE_BYTES_PER_PIXEL);
        ByteBuffer scratch = ByteBuffer.allocateDirect(textWidth * textHeight  * bytesPerPixel);
        if(bytesPerPixel == 3) 
        {
            ilType = IL.IL_RGB;
            glType = GL11.GL_RGB;
        }
        else 
        	if(bytesPerPixel == 4) 
        	{
        	
	            ilType = IL.IL_RGBA;
	            glType = GL11.GL_RGBA;
	        }
        
        		
		for (int i=0;i< rows ; i++)
			for (int j=0;j< cols ; j++)
			{
				IL.ilCopyPixels(xOffSet+j*textWidth,yOffSet+i*textHeight,0,textWidth,textHeight, 1,ilType, IL.IL_BYTE, scratch);
		        
		        // Create A IntBuffer For Image Address In Memory
		        IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
		        GL11.glGenTextures(buf); // Create Texture In OpenGL

		         // Create Nearest Filtered Texture
		        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
		        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
		        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,glType,textWidth,textHeight, 0,glType, GL11.GL_UNSIGNED_BYTE, scratch);

		        Texture newTexture = new Texture();
		        newTexture.textureId = buf.get(0);     // Return Image Addresses In Memory
		        newTexture.textureHeight = textHeight;
		        newTexture.textureWidth = textWidth;
		        
		        toReturntextures[i*cols+j] = newTexture;
		        toReturntextures[i*cols+j].textureHeight = textHeight;
		        toReturntextures[i*cols+j].textureWidth = textWidth;
			}
		
		return toReturntextures;
	}
	*/
}