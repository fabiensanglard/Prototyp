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

package rtype.entity;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import rtype.Prototyp;

public class Blaster extends Weapon
{
	public Blaster(PlayerShip player)
	{
		this.playerShip = player;
		this.type = FORCE_CHARGE;
		init();
		animationSpeed = 28.4f;
		setRatio(0.55f);
	
	}
	
	public void draw()
	{
		if (this.displayChargeAnimation)
       	{
			GL11.glColor4f(0,1,1,1f);
			
       		animationCursor += animationSpeed * tick  ;
	       	animationCursor %= animationTextures.length;
	
	       	GL11.glLoadIdentity();            
			GL11.glTranslatef(playerShip.position.x+width+13,playerShip.position.y+3,Prototyp.DEFAULT_Z);                     	
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[(int)animationCursor].getTextureId() );
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glTexCoord2f(textureRight,textureUp); //Upper right
				GL11.glVertex2f(width, -height);
				
				GL11.glTexCoord2f(textureLeft,textureUp); //Upper left			
				GL11.glVertex2f(-width, -height);        
		   
				GL11.glTexCoord2f(textureLeft,textureDown); //Lower left
				GL11.glVertex2f(-width,height);
					
				GL11.glTexCoord2f(textureRight,textureDown); // Lower right
				GL11.glVertex2f(width,height);	
			}
	       	GL11.glEnd();
	       	
	    	GL11.glColor4f(1f,1f,1f,1f);
       	}
	}

	public void startChargingAnimation()
	{
		this.displayChargeAnimation = true;
		
	}

	public void stopChargingAnimation()
	{
		this.displayChargeAnimation = false;
		this.animationCursor = 0;
	}

	public void fire(float chargePercentage)
	{
		ForceBlast fb = new ForceBlast(chargePercentage*1.3f);
		fb.spawn(playerShip.position,new Vector2f(770,0),Prototyp.bullets);
		
		//Prototyp.bullets.add(fb);
		
	}
	
	
}
