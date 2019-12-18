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

public class CrystalOrb extends Orb
{
	
	
	boolean freezing = false;
	
	public CrystalOrb(PlayerShip player)
	{
		this.playerShip = player;
		this.type = GREEN_ORB;
		init();
		animationSpeed = 20f;
		chargeAnimationSpeed = 0.9f;
		setRatio(0.35f);
		
	}


	@Override
	public void fire(float chargePercentage)
	{
		
		freezing = true;
		freezingRange = DEFAULT_FREEZING_RANGE * chargePercentage ; 

	}

	// Duration for attaction process
	float freezingDuration = 2;
	float freezeTickCounter = 0;
	private static final float DEFAULT_FREEZING_RANGE = 250;
	private float freezingRange = 0;
	
	// Once attraction has started, the orb is not supposed to
	// move anymore...
	boolean startedFreezeProcess = false;
	private ArrayList<Entity> enemiesToFreeze = null;
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
		if (freezing)
		{
			
			if (freezeTickCounter > freezingDuration)
			{
				freezeTickCounter = 0;
				freezing = false;
				startedFreezeProcess = false;
				enemiesToFreeze = null;
			}
			else
			{
				calculateDistanceFromShip();
				if (startedFreezeProcess || distanceFromShip > 250 )
				{
					startedFreezeProcess = true;
					if (enemiesToFreeze == null)
						buildListOfEnemiesToFreeze();
					
					freezeTickCounter += tick ;
					for (int i = 0 ; i < enemiesToFreeze.size() ; i++)
					{
						enemiesToFreeze.get(i).freeze(0.3f,0.6f);
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


	private void buildListOfEnemiesToFreeze()
	{
		// Used freezingRange to populate 
		enemiesToFreeze = new ArrayList();
		Entity enemy = null;
		float diffX =0;
		float diffY =0;
		
		ArrayList<Entity> enemies = Prototyp.enemies.entities;
		for (int i = 0 ; i < enemies.size() ; i++)
		{
			
			enemy = enemies.get(i);
			
			diffX = this.position.x - enemy.position.x ;
			diffY = this.position.y - enemy.position.y ;
			
			enemy.distanceFromOrb = (float)Math.sqrt(diffX * diffX + diffY * diffY);

			if (enemy.distanceFromOrb < freezingRange)
			{
				insertInDescDistanceFromOrb(enemy);
			}
			
		}
		
	}

	private boolean toInserted = false;
	private void insertInDescDistanceFromOrb(Entity enemy)
	{	
		toInserted = false;
		int i = 0;
		while (i < enemiesToFreeze.size() && !toInserted)
		{
			if (enemiesToFreeze.get(i).distanceFromOrb > enemy.distanceFromOrb)
				toInserted = true;
			else
				i++;
		}
		enemiesToFreeze.add(i,enemy);
	}	
}
