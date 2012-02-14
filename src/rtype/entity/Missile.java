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

public class Missile extends AnimatedEntity
{
	
	
	
	public Missile()
	{
		this.type = MISSILE;
		init();
		this.animationSpeed = 40;	
		setRatio(0.25f);
		this.life = 1;
	}
	
	public void draw()
	{

		animationCursor += animationSpeed * tick  ;
       	animationCursor %= animationTextures.length;

       	GL11.glLoadIdentity();            
		GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
	
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[(int)animationCursor].getTextureId() );
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		
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

	Smoke smoke = null;
	Vector2f smokePosition = new Vector2f();
	
	private float smokeAccumulator = 0;
	private float smokeGenSpeed = 130f;
	private static final float SMOKE_LIMIT = 5f;
	
	@Override
	public void update()
	{
		
		
		smokeAccumulator += smokeGenSpeed * tick;
		if (smokeAccumulator > SMOKE_LIMIT)
		{
			smoke = new Smoke();
			//smoke.setRatio(smokeAccumulator/SMOKE_LIMIT );
			smokePosition.x = position.x -15 ;
			//smokePosition.x = (Prototyp.random.nextInt(1) == 0)? smokePosition.x: -smokePosition.x;
			smokePosition.y =  Prototyp.random.nextInt(5);
			smokePosition.y = (Prototyp.random.nextInt(2) == 0)? smokePosition.y: -smokePosition.y;
			smokePosition.y += position.y -5;
			smoke.spawn(smokePosition,Prototyp.DEFAULT_SCROLLING_SPEED,Prototyp.fx);
			smokeAccumulator=0;
		}
		
		super.update();
	}
	
	@Override
	public boolean collided(Entity entity)
	{
		this.life -= entity.damage;
		if (life < 0)
		{
			this.unSpawn();
			ex = new Explosion(Prototyp.random.nextInt(2)+IEntity.EXPLOSION1);
			ex.spawn(this.position,speedNull,Prototyp.frontground);
			return true;
		}
		return false;
	}	
}