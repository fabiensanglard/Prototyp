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

public class RapidFireBullet extends Bullet
{
	public RapidFireBullet(float rotation)
	{
		this.rotation = rotation;
		this.type = BULLET_RAPID_FIRE;
		this.setRatio(0.5f);
		//this.rotation = -90;
		this.life = 20;
		init();
	}
	
	public boolean collided(Entity entity)
	{
		this.life -= entity.damage;
		if (life < 0)
		{
			this.unSpawn();
			hit = new BulletHit(IEntity.BULLET_HIT_YELLOW);
			hit.spawn(this.position,new Vector2f(0,0),Prototyp.fx);
			return true;
		}
		return false;
	}	
	
	
	private float ghostAccumulator = 0;
	private float ghostGenSpeed = 800f;
	private static final float GHOST_LIMIT = 5;
	Vector2f ghostPosition = new Vector2f();
	private RapidFireBulletGhost ghost = null;
	
	@Override
	public void update()
	{
		interpolate(position,speed);
		
		ghostAccumulator += ghostGenSpeed * tick;
		if (ghostAccumulator > GHOST_LIMIT)
		{
			ghost = new RapidFireBulletGhost(this.rotation);
			ghost.spawn(this.position,Prototyp.DEFAULT_SCROLLING_SPEED,Prototyp.fx);
			ghostAccumulator=0;
		}
		if (this.position.x - this.width > (Prototyp.SCREEN_WIDTH / 2) || this.position.x + this.width < - (Prototyp.SCREEN_WIDTH / 2))
		{
			unSpawn();
			if (Logger.isLogActivate) Logger.log(this.getClass().getName()+" died");
			return;
		}
	}

}
