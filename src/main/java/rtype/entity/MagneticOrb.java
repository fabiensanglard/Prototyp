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

import java.util.ArrayList;

import rtype.Prototyp;

public class MagneticOrb extends Orb
{
	
	
	boolean attracting = false;
	
	public MagneticOrb(PlayerShip player)
	{
		this.playerShip = player;
		this.type = RED_ORB;
		init();
		animationSpeed = 20f;
		chargeAnimationSpeed = 0.9f;
		setRatio(0.35f);
		
	}


	@Override
	public void fire(float chargePercentage)
	{
		attractionDuration = 10 * chargePercentage;
		attracting = true;
	}

	// Duration for attaction process
	float attractionDuration = 10f;
	float attractionTickCounter = 0;
	float attractionStrengh = 300f;
	
	// Once attraction has started, the orb is not supposed to
	// move anymore...
	boolean startedAttraction = false;
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
		if (attracting)
		{
			
			if (attractionTickCounter > attractionDuration)
			{
				attractionTickCounter = 0;
				attracting = false;
				startedAttraction = false;
			}
			else
			{
				calculateDistanceFromShip();
				if (startedAttraction || distanceFromShip > 250 )
				{
					startedAttraction = true;
					ArrayList<Entity> enemies = Prototyp.enemies.entities;
					Entity enemy = null;
					float diffX =0;
					float diffY =0;
					float diffXcarre = 0;
					float diffYcarre = 0;
					float distanceFromOrb = 0;
					attractionTickCounter += tick ;
					for (int i = 0 ; i < enemies.size() ; i++)
					{
						enemy = enemies.get(i);
						
						diffX = this.position.x - enemy.position.x ;
						diffY = this.position.y - enemy.position.y ;
						
						diffXcarre = diffX * diffX;
						if (diffXcarre > 40000)
							continue;
						
						diffYcarre = diffY * diffY;
						if (diffYcarre > 40000)
							continue;
						
						distanceFromOrb = (float)Math.sqrt(diffXcarre  + diffYcarre);
						
						if (distanceFromOrb < 200)
						{
							diffX *= attractionStrengh * 1/distanceFromOrb * tick ;
							diffY *= attractionStrengh * 1/distanceFromOrb * tick ;
						
							enemy.speed.x += diffX;
							enemy.speed.y += diffY;
						}
					}
				}
				else
				{
					speed.x = -xDiffWithPlayerShip * 2;
					speed.y = -yDiffWithPlayerShip * 2;
					interpolate(position,speed);
					updateOrbAngle();
				}
			}
		}
		else
			super.update();
	}	
}
