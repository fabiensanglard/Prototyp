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

import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import rtype.BonusFactory;
import rtype.Prototyp;

public class LadyBird extends Enemy
{
	private int BONUS_RANGE = 600;
	private int BONUS_LIMIT = 1;
	
	
	public LadyBird()
	{
		this.type = LADYBIRD;
		init();
		animationSpeed = 15f;
		setRatio(0.45f);
		flipYAxis();
		this.fireSpeed = 0.05f;
		lastFireCounter = Prototyp.random.nextInt();
		animationCursor = System.currentTimeMillis() % (this.animationTextures.length -1) ;
		this.life = 1;
	}
	
	
	public void draw()
	{
		animationCursor += animationSpeed * tick  ;
       	animationCursor %= animationTextures.length;

       	GL11.glLoadIdentity();     
       	GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[(int)animationCursor].getTextureId() );

		
		if (freezing)
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
		else
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

	public void update()
	{
		super.update();
		this.lastFireCounter += this.fireSpeed * tick ;
		if (this.lastFireCounter > 1)
		{
			this.lastFireCounter = 0;
			this.fire();
		}
	}
	
	public void fire()
	{
		// Fire a bullet to player's ship
		Vector2f directionToplayer = new Vector2f(Prototyp.player1.position.x-this.position.x,Prototyp.player1.position.y-this.position.y);
		directionToplayer.normalise();
		directionToplayer.x *= 50;
		directionToplayer.y *= 50;
		
		EnemyBullet b = new EnemyBullet();
		
		b.spawn(this.position,directionToplayer,Prototyp.enemies);
		//Entity.dumpEntity(this);
	}

	Bonus bonus = null;
	private Bonus presetBonus = null;
	
	@Override
	public boolean collided(Entity entity)
	{
		if ( super.collided(entity))
		{
			if (presetBonus != null)
				bonus = presetBonus;
			
			if (bonus == null && Prototyp.random.nextInt(BONUS_RANGE) <= BONUS_LIMIT)
				bonus = BonusFactory.createBonus(Prototyp.random.nextInt(Bonus.BONUS_COUNT)+BONUS_BOOSTER);

			if (bonus != null)
				bonus.spawn(this.position,this.speed,Prototyp.bonus);
			
			return true;
		}
		
		return false;
	}


	public Bonus getPresetBonus() {
		return presetBonus;
	}


	public void setPresetBonus(Bonus presetBonus) {
		this.presetBonus = presetBonus;
	}
	
	
	
}
