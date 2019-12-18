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

public abstract class Bonus extends AnimatedEntity
{
	public static final int BONUS_COUNT = 5 ;
	
	public Bonus(int type)
	{
		this.type = type;
		init();
		this.animationSpeed = 15;
		this.setRatio(0.25f);
	}
	
	public void draw()
	{
		animationCursor += animationSpeed * tick  ;
       	animationCursor %= animationTextures.length;
   
       	GL11.glLoadIdentity();            
		GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
	
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[(int)animationCursor].getTextureId() );
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
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

	@Override
	public void update()
	{
		interpolate(position,speed);
		if (this.position.x - this.width > (Prototyp.SCREEN_WIDTH / 2) || this.position.x + this.width < - (Prototyp.SCREEN_WIDTH / 2))
		{
			unSpawn();
			if (Logger.isLogActivate) Logger.log(this.getClass().getName()+" died");
			return;
		}
	}
	
	public abstract void boostPlayerShip(PlayerShip ship);

	Implosion imp = null;
	@Override
	public boolean collided(Entity entity)
	{
		// This bonus should only collid with playerShip 
		this.boostPlayerShip((PlayerShip)entity);
		imp = new Implosion();
		imp.spawn(this.position,this.speed,Prototyp.fx);
		this.unSpawn();
		
		return true;
		
	}

	
}
