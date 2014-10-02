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

import rtype.Layer;
import rtype.Prototyp;
import rtype.Texture;

public abstract class Entity implements IEntity
{
//	This is used by the crystal Orb
	public float distanceFromOrb = 0;
	
	public int original_width;
	public int original_height;
	
	private float ratio = 1f;
	public float width ;
	public float height ;
	
	public float rotation = 0;
	protected float rotationSpeed = 0;
	
	protected float damage = 1;
	protected float life = 1;
	protected Explosion ex = null;
	
	public void setRatio(float newRatio)
	{
		this.ratio = newRatio;
		this.width = this.original_width * ratio;
		this.height = this.original_height * ratio;
	}
	
	public Vector2f speed = new Vector2f();
	public Vector2f position = new Vector2f();
	

	protected Texture texture;
	public int type;
	
	
	protected float textureUp = 1;
	protected float textureDown = 0;
	protected float textureLeft = 0;
	protected float textureRight = 1;
	
	protected Layer layer = null;

	protected void init()
	{
		this.texture = Prototyp.textureLoader.getTexture(this.type);
		this.original_width = this.texture.getTextureWidth();
		this.original_height = this.texture.getTextureHeight();
		this.width = this.original_width * ratio;
		this.height = this.original_height * ratio;
		
	}
	
	public void spawn(Vector2f position,Vector2f speed,Layer layer )
	{
		this.position.x = position.x;
		this.position.y = position.y;
		
		this.speed.y = speed.y;
		this.speed.x = speed.x;
		
		this.layer = layer;
		this.layer.add(this);
	}
	
	public void spawn(Vector2f position,Vector2f speed, float rotationSpeed,Layer layer  )
	{
		spawn(position,speed,layer);
		this.rotationSpeed = rotationSpeed;
	}
	
	public void unSpawn()
	{
		layer.remove(this);
	}
	
	public void draw()
	{
		GL11.glLoadIdentity();            
		GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
		GL11.glRotatef(this.rotation,0f,0f,1f);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getTextureId() );
		
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
	
	
	
	protected Vector2f interpolate(Vector2f old_position,Vector2f speed)
	{
		old_position.x = old_position.x + tick * speed.x;
		old_position.y = old_position.y + tick * speed.y;
		
		return old_position;
	}
	
	static public void dumpEntity(Entity ent)
	{
		
			Logger.log("--------------------------");
			Logger.log("px"+ent.position.x);
			Logger.log("py"+ent.position.y);
			Logger.log("dx"+ent.speed.x);
			Logger.log("dy"+ent.speed.y);
			Logger.log("width"+ent.original_width);
			Logger.log("height"+ent.original_height);
			Logger.log("--------------------------");
		
	}
	
	public void flipYAxis()
	{
		float tmp = textureLeft;
		this.textureLeft = textureRight;
		textureRight = tmp;		
	}
	
	public void flipXAxis()
	{
		float tmp = textureUp;
		this.textureUp = textureDown;
		textureDown = tmp;		
	}
	
	public void update()
	{
		interpolate(position,speed);
		this.rotation += rotationSpeed * tick ;
		if (this.position.x - this.width > (Prototyp.SCREEN_WIDTH / 2) || this.position.x + this.width < - (Prototyp.SCREEN_WIDTH / 2))
		{
			unSpawn();
			if (Logger.isLogActivate) Logger.log(this.getClass().getName()+" died");
			return;
		}
	}
	
	
	private static EnemyPiece ep1 = null;
	private static EnemyPiece ep2 = null;
	private static EnemyPiece ep3 = null;
	private static EnemyPiece ep4 = null;
	public static Vector2f speedNull = new Vector2f(0,0);
	private static Vector2f speedUp = new Vector2f(30,20);
	private static Vector2f speedUp2 = new Vector2f(60,35);
	private static Vector2f speedDown = new Vector2f(30,-23);
	private static Vector2f speedDown2 = new Vector2f(60,-24);
	
	private static final int ROTATION_SPEED = 260;
	
	// Return true is the current Entity is destroyed.
	public boolean collided(Entity entity)
	{
		if (entity == this)
			return false;
		
		this.life -= entity.damage;
		if (life < 0)
		{
			this.unSpawn();
			
			ex = new Explosion(Prototyp.random.nextInt(2)+IEntity.EXPLOSION1);
			ex.spawn(this.position,this.speed,Prototyp.random.nextInt(ROTATION_SPEED),Prototyp.frontground);
			
			ep1 = new EnemyPiece(Prototyp.random.nextInt(8)+IEntity.ENEMY_PIECE_1);
			ep1.spawn(this.position,speedUp2,Prototyp.random.nextInt(ROTATION_SPEED),Prototyp.frontground);
			
			ep2 = new EnemyPiece(Prototyp.random.nextInt(8)+IEntity.ENEMY_PIECE_1);
			ep2.spawn(this.position,speedDown,Prototyp.random.nextInt(ROTATION_SPEED),Prototyp.frontground);
			
			ep3 = new EnemyPiece(Prototyp.random.nextInt(8)+IEntity.ENEMY_PIECE_1);
			ep3.spawn(this.position,new Vector2f(entity.speed.x,this.speed.y),Prototyp.random.nextInt(ROTATION_SPEED),Prototyp.frontground);
			
			ep4 = new EnemyPiece(Prototyp.random.nextInt(8)+IEntity.ENEMY_PIECE_1);
			ep4 .spawn(this.position,new Vector2f(entity.speed.x,-this.speed.y),Prototyp.random.nextInt(ROTATION_SPEED),Prototyp.frontground);
			 
			return true;
		}
		return false;
	}
	
	protected float tick;
	private float frozenTickCounter=0;
	private float freezeDuration = 0;
	private float freezeSpeed = 0;
	private float freezingPercentage = 0;
	
	// This part has been added
	public void updateTick()
	{
		if (freezing)
		{
			
			if (frozen)
			{
				// You may wonder why I multiply by 10.
				// It is because on very "fast" computer, fps
				// may be so high and tick so small that
				// frozenTickCounter get no updated and
				// unfreeze never happen
				frozenTickCounter += tick  * 10;

				if (frozenTickCounter > freezeDuration)
				{
					frozen = false;
					frozenTickCounter = 0;
					// Have to start to defroze
					freezeSpeed = -freezeSpeed;
				}
				else
				{
					tick = 0;
				}
			}
			else
			{
				// Update tick according to freeze Speed
				// Note this can be the froze or defroze process...
				freezingPercentage += Prototyp.tick * freezeSpeed;
				tick = Prototyp.tick - freezingPercentage * Prototyp.tick;
				if (tick < 0 )
				{
					this.frozen = true;		
					this.unSpawn();
					this.spawn(this.position,this.speed,Prototyp.bullets);
					//this.life = 50;

				}
				if (tick > Prototyp.tick)
				{
					freezingPercentage = 0;
					this.freezing = false;

				}
			}
		}
		else
			tick = Prototyp.tick;
			
	}
	
	protected boolean frozen = false;
	protected boolean freezing = false;
	
	public void freeze(float freezeDuration, float freezeSpeed)
	{
		this.freezeDuration = freezeDuration;
		this.freezeSpeed = freezeSpeed;
		this.freezing = true ;
	}

	public Layer getLayer() {
		return layer;
	}

	public void setLayer(Layer layer) {
		this.layer = layer;
	}

}

