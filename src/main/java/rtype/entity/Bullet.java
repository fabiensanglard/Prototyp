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

public class Bullet extends Entity
{
	protected Bullet()
	{
		
	}
	
	public Bullet(int type)
	{
		this.type = type;
		
		this.setRatio(0.5f);
		//this.rotation = -90;
		this.life = 20;
		init();
	}
	
	static BulletHit hit = null;
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
}
