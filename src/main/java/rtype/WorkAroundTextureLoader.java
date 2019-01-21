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

package rtype;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import rtype.entity.IEntity;


public class WorkAroundTextureLoader implements ITextureLoader
{
    private static Texture[] textures = new Texture[1024];
    private static Texture[][] animations = new Texture[1024][1024];


    public WorkAroundTextureLoader()
    {
    }

    /* (non-Javadoc)
     * @see rtype.ITextureLoader#init()
     */
    public void init()
    {
        textures[IEntity.PLANET] = loadTexture("/Data/Stage1Layer1.png",510,510,512,512);

        textures[IEntity.SPACE_TRASH_1] = loadTexture("/Data/Stage1Layer1.png",256,438,256,256); // Big huge spaceship with 5 reactors
        textures[IEntity.SPACE_TRASH_2] = loadTexture("/Data/Stage1Layer1.png",257,700,256,256);
        textures[IEntity.SPACE_TRASH_3] = loadTexture("/Data/Stage1Layer1.png",373,956,64,64); // Round trash
        textures[IEntity.SPACE_TRASH_4] = loadTexture("/Data/Stage1Layer1.png",492,102,256,128); // Sattelite trash
        textures[IEntity.STAR_1] = loadTexture("/Data/Stage1Layer1.png",612,1011,8,8);
        textures[IEntity.STAR_2] = loadTexture("/Data/Stage1Layer1.png",649,990,16,32);
        textures[IEntity.STAR_3] = loadTexture("/Data/Stage1Layer1.png",674,994,16,16);
        textures[IEntity.STAR_4] = loadTexture("/Data/Stage1Layer1.png",716,1004,16,16);
        textures[IEntity.STAR_5] = loadTexture("/Data/Stage1Layer1.png",738,990,32,32);
        textures[IEntity.STAR_6] = loadTexture("/Data/Stage1Layer1.png",780,994,16,16);

        textures[IEntity.BULLET] = loadTexture("/Data/BulletSet1.png",32,00,32,32);

        textures[IEntity.ORB_BEAM] = loadTexture("/Data/GlowBullets.png",32,0,32,32);
        textures[IEntity.ENEMY_BULLET] = loadTexture("/Data/BulletSet1.png",0,0,32,32);
        textures[IEntity.ENEMY_PIECE_1] = loadTexture("/Data/SpaceTrash.png",00,00,32,32);
        textures[IEntity.ENEMY_PIECE_2] = loadTexture("/Data/SpaceTrash.png",32,00,32,32);
        textures[IEntity.ENEMY_PIECE_3] = loadTexture("/Data/SpaceTrash.png",64,00,32,32);
        textures[IEntity.ENEMY_PIECE_4] = loadTexture("/Data/SpaceTrash.png",96,00,32,32);
        textures[IEntity.ENEMY_PIECE_5] = loadTexture("/Data/SpaceTrash.png",00,32,32,32);
        textures[IEntity.ENEMY_PIECE_6] = loadTexture("/Data/SpaceTrash.png",32,32,32,32);
        textures[IEntity.ENEMY_PIECE_7] = loadTexture("/Data/SpaceTrash.png",64,32,32,32);
        textures[IEntity.ENEMY_PIECE_8] = loadTexture("/Data/SpaceTrash.png",96,32,32,32);
        textures[IEntity.BULLET_RAPID_FIRE] = loadTexture("/Data/BulletSet1.png",00,64,32,32);
        textures[IEntity.BULLET_MULTI_SHOOT] = loadTexture("/Data/BulletSet1.png",0,32,32,32);
        textures[IEntity.BULLET_BASE] = loadTexture("/Data/BulletSet1.png",32,32,32,32);
        textures[IEntity.SPACE_TRASH_5] = loadTexture("/Data/Stage1Layer1.png",148,721,64,64); // Extreme left trash

        textures[IEntity.FORCEBLAST] = loadTexture("/Data/forceblast.png",0,0,128,64);

        //textures[IEntity.SCANLINE] = loadTexture("Data/Scanlines.png",0,0,32,32);

        animations[IEntity.PLAYER_SHIP] = loadAnimation("/Data/PlayerShip.png",4,4,128,128);
        animations[IEntity.GREEN_ORB] = loadAnimation("/Data/Orb1.png",8,2,64,64);
        animations[IEntity.BLUE_ORB] = loadAnimation("/Data/Orb1.png",8,2,64,64,0,128);
        animations[IEntity.PINK_ORB] = loadAnimation("/Data/Orb1.png",8,2,64,64,0,256);
        animations[IEntity.RED_ORB] = loadAnimation("/Data/Orb1.png",8,2,64,64,0,384);
        animations[IEntity.FORCE_CHARGE] = loadAnimation("/Data/forceCharge.png",8,4,64,64);
        animations[IEntity.PLAYER_SPEED] = loadAnimation("/Data/PlayerSpeed.png",8,4,64,64);
        animations[IEntity.EXPLOSION1] = loadAnimation("/Data/Explosion2.png",8,4,64,64);
        animations[IEntity.EXPLOSION2] = loadAnimation("/Data/ExplosionSmall.png",8,4,128,128);
        animations[IEntity.LADYBIRD]   = loadAnimation("/Data/Ladybird.png",4,4,64,64);
        animations[IEntity.FIRE_BALL]   = loadAnimation("/Data/Fireball.png",4,4,128,128);
        animations[IEntity.FONT]   = loadAnimation("/Data/Fonts.png",16,16,32,32);
        animations[IEntity.BULLET_HIT_YELLOW] = loadAnimation("/Data/BulletHit.png",4,2,32,32);
        animations[IEntity.BULLET_HIT_GREEN]  = loadAnimation("/Data/BulletHit2.png",4,2,32,32);
        animations[IEntity.BULLET_HIT_BLUE]   = loadAnimation("/Data/BulletHit3.png",4,2,32,32);
        animations[IEntity.BIT_UPGRADE]   = loadAnimation("/Data/BitUpgrade.png",4,2,32,32);
        animations[IEntity.BONUS_MULTI_SHOOT_ORB]        = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,00);
        animations[IEntity.BONUS_LIGHTNING_ORB]          = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,64);
        animations[IEntity.BONUS_RAPID_SHOOT_ORB]  = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,128);
        animations[IEntity.BONUS_GRAVITY_ORB]    = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,192);
        animations[IEntity.BONUS_SPEED]       = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,256);
        animations[IEntity.BONUS_CRYSTAL_ORB]= loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,320);
        animations[IEntity.BONUS_BOOSTER] = loadAnimation("/Data/PowerUp1.png",8,1,64,64,00,384);
        animations[IEntity.IMPLOSION]   = loadAnimation("/Data/Implosion.png",8,4,64,64);
        animations[IEntity.MISSILE]   = loadAnimation("/Data/HomingMissile.png",4,4,64,64);
        animations[IEntity.SMOKE]   = loadAnimation("/Data/SmokePuff.png",4,4,32,32);
    }

    /* (non-Javadoc)
     * @see rtype.ITextureLoader#getTexture(int)
     */
    public  Texture getTexture(int textureID)
    {
        return textures[textureID];
    }

    /* (non-Javadoc)
     * @see rtype.ITextureLoader#getAnimation(int)
     */
    public  Texture[] getAnimation(int animationID)
    {
        return animations[animationID];
    }

    Map<String, BufferedImage> imageCache = new HashMap<String, BufferedImage>();

    // Offset are in term off pixel, not byte, the image loader figure out alone what is the bytesPerPixel
    private  Texture loadTexture(String path,int xOffSet, int yOffSet, int textWidth, int textHeight) {


        BufferedImage buffImage = null;
        try
        {
            if (imageCache.get(path) != null)
                buffImage = (BufferedImage)imageCache.get(path);
            else
            {
                System.out.println("Loading image:"+path);
                buffImage = ImageIO.read(this.getClass().getResource(path));

                //February 2nd, 2011: Fix for JAVA 1.6 thanks to sonicWonder for the fix
                byte[] data = ((DataBufferByte) buffImage.getRaster().getDataBuffer()).getData();
                switch (buffImage.getType())
                {
                    case BufferedImage.TYPE_4BYTE_ABGR:  convertFromARGBToBGRA(data);break;
                    case BufferedImage.TYPE_3BYTE_BGR:  convertFromBGRToRGB(data);   break;
                    default: System.out.println("Unknown type:"+buffImage.getType()); break;
                }
                //End February 2nd, 2011: Fix for JAVA 1.6 thanks to sonicWonder for the fix
                imageCache.put(path, buffImage);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        int bytesPerPixel = buffImage.getColorModel().getPixelSize() / 8;

        ByteBuffer scratch = ByteBuffer.allocateDirect(textWidth*textHeight*bytesPerPixel).order(ByteOrder.nativeOrder());


        DataBufferByte dataBufferByte = ((DataBufferByte) buffImage.getRaster().getDataBuffer());

        for (int i = 0 ; i < textHeight ; i++)
            scratch.put(dataBufferByte.getData(),(xOffSet+(yOffSet+i)*buffImage.getWidth())*bytesPerPixel, textWidth * bytesPerPixel);

        scratch.rewind();


        // Create A IntBuffer For Image Address In Memory
        IntBuffer buf = ByteBuffer.allocateDirect(4).order(ByteOrder.nativeOrder()).asIntBuffer();
        GL11.glGenTextures(buf); // Create Texture In OpenGL

        // Create Nearest Filtered Texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,GL11.GL_RGBA,textWidth,textHeight, 0,GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, scratch);

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
        Texture[] toReturntextures = new Texture[cols*rows];



        for (int i=0;i< rows ; i++)
            for (int j=0;j< cols ; j++)
            {
                toReturntextures[i*cols+j] = loadTexture(path,j*textWidth+xOffSet,i*textHeight+yOffSet,textWidth,textHeight);
            }

        return toReturntextures;
    }

    //February 2nd, 2011: Fix for JAVA 1.6 thanks to sonicWonder for the fix
    private static void convertFromARGBToBGRA(final byte[] data) {
        for (int i = 0; i <  data.length; i += 4)
        {
			/*
			final int a = data[i] & 0x000000FF;
			final int r = data[i + 1] & 0x000000FF;
			final int g = data[i + 2] & 0x000000FF;
			final int b = data[i + 3] & 0x000000FF;
			data[i] = (byte) b;
			data[i + 1] = (byte) g;
			data[i + 2] = (byte) r;
			data[i + 3] = (byte) a;
			*/

            // A small optimisation

            data[i] ^= data[i + 3];
            data[i+3] ^= data[i];
            data[i] ^= data[i + 3];

            data[i + 1] ^= data[i + 2];
            data[i + 2] ^= data[i + 1];
            data[i + 1] ^= data[i + 2];

        }
    }

    private static void convertFromBGRToRGB(final byte[] data)
    {
        for (int i = 0; i < data.length; i += 3)
        {
			/*
			final int b = data[i] & 0xFF;
			final int g = data[i + 1] & 0xFF;
			final int r = data[i + 2] & 0xFF;
			data[i] = (byte) r;
			data[i + 1] = (byte) g;
			data[i + 2] = (byte) b;
			*/

            // A small optimisation
            data[i] ^= data[i + 2];
            data[i + 2 ] ^= data[i ];
            data[i] ^= data[i + 2];

        }
    }
    //End February 2nd, 2011: Fix for JAVA 1.6 thanks to sonicWonder for the fix
}