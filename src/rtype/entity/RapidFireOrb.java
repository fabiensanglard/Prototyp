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

public class RapidFireOrb extends Orb
{
	
	protected static float DEFAULT_DISTANCE_FROM_SHIP_WHEN_FIRING = DEFAULT_DISTANCE_FROM_SHIP + 70;
	
	public RapidFireOrb(PlayerShip player)
	{
		this.playerShip = player;
		this.type = PINK_ORB;
		init();
		animationSpeed = 20f;
		chargeAnimationSpeed = 0.9f;
		setRatio(0.35f);
		
	}

	private float bulletsToFire = 0;
	private float bulletTimeCounter = 0;
	private float bulletFireRate = 30;
	private static final float MAX_BULLETS = 100;
	private static final float FIRE_RATE_LIMIT = 2;
	
	public void fire(float chargePercentage)
	{
		this.bulletsToFire = (int)MAX_BULLETS * chargePercentage;
		distanceFromShipRequested = DEFAULT_DISTANCE_FROM_SHIP_WHEN_FIRING ;
	}

	RapidFireBullet bu = null;
	private static float BULLETS_SPEED = 1000;
	@Override
	public void update()
	{
		super.update();
		
		if (bulletsToFire > 0 )
		{
			bulletTimeCounter+= bulletFireRate * tick;
			if (bulletTimeCounter > FIRE_RATE_LIMIT)
			{
				bulletsToFire--;
				bulletTimeCounter = 0 ;
				bu = new RapidFireBullet(this.rotation);
				bu.spawn(this.position,new Vector2f((float)(Math.cos(rotationRadians))*BULLETS_SPEED,(float)(Math.sin(rotationRadians))*BULLETS_SPEED),Prototyp.bullets);
			}
			if (bulletsToFire < 0 )
			{
				distanceFromShipRequested = DEFAULT_DISTANCE_FROM_SHIP;
			}
		}
	}
}
