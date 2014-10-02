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

import rtype.Layer;
import rtype.Prototyp;

public class BitUpgrade extends Weapon
{

	PlayerShip playerShip = null;
	Vector2f postionRelativeToPlayerShip = new Vector2f(0,0);
	
	
	public BitUpgrade(PlayerShip playerShip)
	{
		this.type = BIT_UPGRADE;
		this.playerShip = playerShip;
		init();
		animationSpeed = 30f;
		setRatio(0.40f);		
		
	}
	
	@Override
	public void draw()
	{
		animationCursor += animationSpeed * tick  ;
       	animationCursor %= animationTextures.length -1 ;
		
		GL11.glLoadIdentity();            
       	GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
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
	}
	
	
	float xDiff = 0;
	float yDiff = 0;
	float distanceFromShip = 0;
	static final float VELOCITY = 2;
	@Override
	public void update()
	{
		
		// Modify speed
		xDiff = (playerShip.position.x + postionRelativeToPlayerShip.x) - position.x;
		yDiff = (playerShip.position.y + postionRelativeToPlayerShip.y) - position.y;
		distanceFromShip = (float)Math.sqrt(xDiff*xDiff + yDiff*yDiff);
		if (distanceFromShip > 10)
		{
			speed.x = xDiff    * VELOCITY;
			speed.y = yDiff    * VELOCITY;
		}
		else
		{
			speed.x = 0;
			speed.y = 0;
		}
		interpolate(position,speed);
		this.rotation += rotationSpeed * tick ;
		
	}

	
	@Override
	public boolean collided(Entity entity)
	{
		// Make undestructible
		return false;
	}
	
	@Override
	public void spawn(Vector2f position,Vector2f spawningPlace,Layer layer )
	{
		this.postionRelativeToPlayerShip.x = position.x;
		this.postionRelativeToPlayerShip.y = position.y;	
		this.position.x = spawningPlace.x;
		this.position.y = spawningPlace.y;
		this.layer = layer;
		this.speed.y = speed.y;
		this.speed.x = speed.x;
		this.layer.add(this);
	}

	@Override
	public void startChargingAnimation()
	{	
	}

	@Override
	public void stopChargingAnimation()
	{
	}

	@Override
	public void fire(float chargePercentage)
	{
		if (Prototyp.enemies.entities.size() != 0)
   		{
   			HomingMissile m = new HomingMissile(Prototyp.enemies,(float)Math.PI / 1000); 
   			m.spawn(position,new Vector2f(400,0),Prototyp.bullets);
   		}
		
	}
	
	
}
