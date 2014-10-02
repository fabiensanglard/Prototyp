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

import rtype.Prototyp;

public abstract class Orb extends Weapon
{

	public FireBall fb = new FireBall(this);
	protected static float DEFAULT_DISTANCE_FROM_SHIP = 60;
	protected float distanceFromShipRequested = DEFAULT_DISTANCE_FROM_SHIP;
	
	
	
	@Override
	public void startChargingAnimation()
	{
		this.displayChargeAnimation = true;
		this.fb.startAnimation();

	}

	@Override
	public void stopChargingAnimation()
	{
		displayChargeAnimation = false;
		chargeAnimationCursor = 0;
		this.fb.stopAnimation();

	}

	@Override
	public void draw()
	{
       		animationCursor += animationSpeed * tick  ;
	       	animationCursor %= animationTextures.length;
	
	       	GL11.glLoadIdentity();            
			//GL11.glTranslatef(playerShip.position.x+playerShip.width+100,playerShip.position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
	       	GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
	       	GL11.glRotatef(rotation,0f,0f,1f);
	       	GL11.glColor4f(1,1,1,1);
	       	GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[(int)animationCursor].getTextureId() );
			
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
	      
	       
	       	
			if (this.displayChargeAnimation)
	       	{
				chargeAnimationCursor += chargeAnimationSpeed * tick  ;
				if (chargeAnimationCursor >= animationTextures.length)
					chargeAnimationCursor = animationTextures.length - 1;
				
				float alphaPercentage = chargeAnimationCursor/(animationTextures.length - 1) ;
				alphaPercentage *= 2.5;
				
				if (alphaPercentage > 0.6f)
					alphaPercentage = 0.6f ;
				else if (alphaPercentage < 0.15 )
				{
					alphaPercentage = 0;
				}
				
				Prototyp.fadeAlpha = alphaPercentage ;
				
				// Draw fireball above that shit
				//fb.tick = tick;
				//fb.draw(alphaPercentage*5);
				
	       	}
			else
				Prototyp.fadeAlpha = 0;
	}

	public boolean collided(Entity entity)
	{
		return false;
	}
	
	protected double rotationRadians = 0;
	protected void updateOrbAngle()
	{
		float xDiff = position.x - playerShip.position.x;
		float yDiff = position.y-playerShip.position.y;
		rotationRadians  = Math.atan(yDiff/xDiff);
		if (xDiff < 0)
			rotationRadians +=Math.PI ;

		if (rotationRadians < 0)
			rotationRadians = 2 * Math.PI + rotationRadians;
		
		//if (xDiff < 0)
		//	this.rotationRadians += 2 * Math.PI;
		this.rotation = GLUTILS.radiansToDegres((float)rotationRadians);
		//if (xDiff < 0)
		//	this.rotation += 180 ;
		
		this.rotation = (float)this.rotation;
	}

	float xDiffWithPlayerShip = 0;
	float yDiffWithPlayerShip = 0;
	float distanceFromShip = 0;
	private int mode = STICKED;
	public void calculateDistanceFromShip()
	{
		xDiffWithPlayerShip = playerShip.position.x - position.x ;
		yDiffWithPlayerShip =  playerShip.position.y - position.y  ;
		distanceFromShip = (float)Math.sqrt(xDiffWithPlayerShip*xDiffWithPlayerShip + yDiffWithPlayerShip*yDiffWithPlayerShip);
	}
	
	
	static final float VELOCITY = 1f;
	public static final int STICKED = 0;
	public static final int ADJUDTING = 1;
	
	@Override
	public void update()
	{
		calculateDistanceFromShip();
		if (mode == ADJUDTING || distanceFromShip > distanceFromShipRequested)
		{
			
			if (distanceFromShip > distanceFromShipRequested)
			{
				speed.x = xDiffWithPlayerShip    * VELOCITY ;
				speed.y = yDiffWithPlayerShip    * VELOCITY;
			}
			else
			{
				speed.x = 0;
				speed.y = 0;
			}
			interpolate(position,speed);
			updateOrbAngle();
		}
		else
		{
			interpolate(position,playerShip.speed);
		}
	}

	public void setMove(int mode)
	{
		this.mode  = mode;
		
	}
	
	
	
}
