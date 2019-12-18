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

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import rtype.Collision;
import rtype.Prototyp;

public class LightningOrb extends Orb
{
	protected static float DEFAULT_DISTANCE_FROM_SHIP_WHEN_FIRING = DEFAULT_DISTANCE_FROM_SHIP + 70;
	
	
	public LightningOrb(PlayerShip player)
	{
		this.playerShip = player;
		this.type = BLUE_ORB;
		init();
		animationSpeed = 20f;
		chargeAnimationSpeed = 0.9f;
		setRatio(0.35f);
		this.damage = 1000;
		
	}

	
	static float coefDirecteur = 0;
	
	static float min_ordonneeOrigine = 0;
	static float max_ordonneeOrigine = 0;
	
	float min_y = 0;
	float max_y = 0;
	
	
	private float bulletsToFire = 0;
	private float bulletTimeCounter = 0;
	private float bulletFireRate = 40;

	
	
	private static final float MAX_BULLETS = 1;
	private static final float FIRE_RATE_LIMIT = 2;
	
	public void fire(float chargePercentage)
	{
		this.bulletsToFire = 2;//(int)MAX_BULLETS * chargePercentage;
		//distanceFromShipRequested = DEFAULT_DISTANCE_FROM_SHIP_WHEN_FIRING ;
		
		
	}
	
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
				fireBeam();
				fireBeam();
				//fireBeam();
			}
			if (bulletsToFire < 0 )
			{
				//distanceFromShipRequested = DEFAULT_DISTANCE_FROM_SHIP;

			}
		}
	}
	
	private static float BEAM_HEIGHT_MODULO = 20;
	private static float MIN_BEAM_HEIGHT = 50;
	
	public void fireBeam()
	{
		Vector2f[] p = new Vector2f[5];
		p[0] = new Vector2f(0,0);
		p[1] = new Vector2f(130,0);
		p[2] = new Vector2f(350,0);
		p[3] = new Vector2f(850,0);
		p[4] = new Vector2f(1950,0);
		
		
		OrbBeam ob = new OrbBeam(this,p,2f,true,0.3f,0.8f,1,0.85f,20,MIN_BEAM_HEIGHT);
		ob.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		
		Vector2f[] p2 = new Vector2f[5];
		p2[0] = new Vector2f(0,0);
		p2[1] = new Vector2f(130,0);
		p2[2] = new Vector2f(370,0);
		p2[3] = new Vector2f(870,0);
		p2[4] = new Vector2f(1970,0);
		
		OrbBeam ob2 = new OrbBeam(this,p,3f,false,0.3f,0.8f,1f,0.85f,20,MIN_BEAM_HEIGHT);
		ob2.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		
		Vector2f[] p3 = new Vector2f[4];
		p3[0] = new Vector2f(0,0);
		p3[1] = new Vector2f(200,0);
		p3[2] = new Vector2f(840,0);
		p3[3] = new Vector2f(940,0);
		
		OrbBeam ob3 = new OrbBeam(this,p3,3f,Prototyp.random.nextInt(2) == 0 ? false : true,1f,1f,1f,0.85f,15,MIN_BEAM_HEIGHT-10);
		ob3.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		
		Vector2f[] p4 = new Vector2f[4];
		p4[0] = new Vector2f(0,0);
		p4[1] = new Vector2f(200,0);
		p4[2] = new Vector2f(440,0);
		p4[3] = new Vector2f(940,0);
		
		OrbBeam ob4 = new OrbBeam(this,p3,55f,Prototyp.random.nextInt(2) == 0 ? false : true,1f,1f,1f,0.3f,15,MIN_BEAM_HEIGHT-40);
		ob4.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		
		/*
		Vector2f[] p5 = new Vector2f[4];
		p5[0] = new Vector2f(0,0);
		p5[1] = new Vector2f(200,0);
		p5[2] = new Vector2f(440,0);
		p5[3] = new Vector2f(940,0);
		
		OrbBeam ob5 = new OrbBeam(this,p3,25f,Prototyp.random.nextInt(2) == 0 ? false : true,1f,1f,1f,0.4f,0,MIN_BEAM_HEIGHT-40);
		ob5.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		*/
		
		
		Vector2f[] p6 = new Vector2f[4];
		p6[0] = new Vector2f(0,0);
		p6[1] = new Vector2f(200,0);
		p6[2] = new Vector2f(440,0);
		p6[3] = new Vector2f(940,0);
		
		OrbBeam ob6 = new OrbBeam(this,p3,20f,Prototyp.random.nextInt(2) == 0 ? false : true,1f,1f,1f,0.8f,0,MIN_BEAM_HEIGHT-40);
		ob6.spawn(this.position,new Vector2f(-75.3f,0),Prototyp.fx);
		
		
		min_y = Math.min(ob.getMinNormalWidth(),ob2.getMinNormalWidth());
	    max_y = Math.max(ob.getMaxNormalWidth(),ob2.getMaxNormalWidth());
		
	    coefDirecteur = (float) Math.tan(rotationRadians);
	    
	   
	    
	    min_ordonneeOrigine = (position.y + min_y) - coefDirecteur * position.x;
	    max_ordonneeOrigine = (position.y + max_y) - coefDirecteur * position.x;
	   
	    
	    
		//Detect collision here...  	
			ArrayList<Entity> enemyArray = Prototyp.enemies.entities;
			Entity currentEnemy = null;
			
			if (this.rotationRadians < Math.PI)
			{
				
				if (this.rotationRadians   < Math.PI/2 )
				{
					
					for (int j= 0 ; j < enemyArray.size(); j++ )
					{
						currentEnemy = enemyArray.get(j);
						if (currentEnemy.position.x > this.position.x && currentEnemy.position.y > this.position.y)
						{
							if (betweenTwoLines(currentEnemy))
							if (currentEnemy.collided(this))
								j--;
						}
					}
				}
				else
				{
					for (int j= 0 ; j < enemyArray.size(); j++ )
					{
						currentEnemy = enemyArray.get(j);
						if (currentEnemy.position.x < this.position.x && currentEnemy.position.y > this.position.y)
						{
							if (betweenTwoLines(currentEnemy))
							if (currentEnemy.collided(this))
								j--;
						}
					}
				}
			}
			else // rotation > PI
			{
				if (this.rotationRadians  > 3 * Math.PI/2 )
				{
					for (int j= 0 ; j < enemyArray.size(); j++ )
					{
						currentEnemy = enemyArray.get(j);
						if (currentEnemy.position.x > this.position.x && currentEnemy.position.y < this.position.y)
						{
							if (betweenTwoLines(currentEnemy))
							if (currentEnemy.collided(this))
								j--;
						}
					}
				}
				else
				{
					for (int j= 0 ; j < enemyArray.size(); j++ )
					{
						currentEnemy = enemyArray.get(j);
						if (currentEnemy.position.x < this.position.x && currentEnemy.position.y < this.position.y)
						{
							if (betweenTwoLines(currentEnemy))
							if (currentEnemy.collided(this))
								j--;
						}
					}
				}
			}
	    
		
	}

	public boolean betweenTwoLines(Entity entity)
	{
		return 
		(entity.position.y < (coefDirecteur*entity.position.x + max_ordonneeOrigine)  &&
		 entity.position.y > (coefDirecteur*entity.position.x + min_ordonneeOrigine)
		) ;
	}
}
