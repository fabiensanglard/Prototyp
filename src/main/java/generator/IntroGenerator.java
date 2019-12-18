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

package generator;

import org.lwjgl.util.vector.Vector2f;

import rtype.Prototyp;
import rtype.entity.IEntity;
import rtype.entity.Planet;
import rtype.entity.SpaceTrash;
import rtype.entity.Star;

public class IntroGenerator extends IGenerator
{

	
	public IntroGenerator() {
		super();
		// TODO Auto-generated constructor stub
	}

	private static final float b_s = 7f;
	
	public void generateEntities()
	{

	        Planet planet= new Planet(IEntity.PLANET);
	        planet.spawn(new Vector2f(320,0),new Vector2f(b_s*-1,0),Prototyp.background);
	        
	        SpaceTrash trash = new SpaceTrash(IEntity.SPACE_TRASH_5);
	        trash.rotation = 90;
	        trash.spawn(new Vector2f(-20,100),new Vector2f(b_s*-6,0),Prototyp.frontground);

	        trash = new SpaceTrash(IEntity.SPACE_TRASH_5);	      
	        trash.spawn(new Vector2f(100,115),new Vector2f(b_s*-5,0),10f,Prototyp.frontground);

	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.rotation = 90;
	        trash.spawn(new Vector2f(-20,-100),new Vector2f(b_s*-7.5f,0),2f,Prototyp.frontground);

	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.spawn(new Vector2f(100,-115),new Vector2f(b_s*-4.5f,0),2f,Prototyp.background);

	        trash = new SpaceTrash(IEntity.SPACE_TRASH_1);
	        trash.spawn(new Vector2f(-200,200),new Vector2f(b_s*-4.5f,0),Prototyp.frontground);

	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.spawn(new Vector2f(-100,100),new Vector2f(b_s*-4.5f,0),-4f,Prototyp.background);
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.spawn(new Vector2f(100,115),new Vector2f(b_s*-4.5f,0),3f,Prototyp.frontground);
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.spawn(new Vector2f(50,0),new Vector2f(b_s*-4.5f,0),Prototyp.background);
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.spawn(new Vector2f(-200,-200),new Vector2f(b_s*-4.5f,0),-2f,Prototyp.frontground);
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.rotation = 200;
	        trash.spawn(new Vector2f(300,-250),new Vector2f(b_s*-4.5f,0),2f,Prototyp.background);
	    	
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.flipYAxis();
	        trash.rotation = 20;
	        trash.spawn(new Vector2f(300,-100),new Vector2f(b_s*-4.5f,0),Prototyp.background);
	    	
	        
	        
	    	//    /*
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_1);
	        trash.spawn(new Vector2f(5,8),new Vector2f(-70.3f,0),2f,Prototyp.frontground);

	        Star star = new Star(IEntity.STAR_1);
	        star.spawn(new Vector2f(60f,-100),new Vector2f(-34.3f,0),Prototyp.background);
	       
	        
	        star = new Star(IEntity.STAR_2);
	        star.spawn(new Vector2f(60f,-120),new Vector2f(-30.3f,0),Prototyp.frontground);
	        
	        
	        star = new Star(IEntity.STAR_3);
	        star.spawn(new Vector2f(60f,-140),new Vector2f(-25.3f,0),Prototyp.background);
	        
	        
	        star = new Star(IEntity.STAR_4);
	        star.spawn(new Vector2f(360f,-160),new Vector2f(-20.3f,0),Prototyp.frontground);
	        
	        
	        star = new Star(IEntity.STAR_5);
	        star.spawn(new Vector2f(360f,-180),new Vector2f(-15.3f,0),Prototyp.background);
	        
	        
	        star = new Star(IEntity.STAR_6);
	        star.spawn(new Vector2f(360f,-200),new Vector2f(-50.3f,0),Prototyp.frontground);
	        
	        
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_2);
	        trash.spawn(new Vector2f(250,20),new Vector2f(-40.3f,0),Prototyp.background);

	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.spawn(new Vector2f(350,-100),new Vector2f(-25.3f,0),2,Prototyp.frontground);
	        
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.spawn(new Vector2f(300,150),new Vector2f(-27.3f,0),-2,Prototyp.frontground);
	        
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.spawn(new Vector2f(420,50),new Vector2f(-30.3f,0),Prototyp.frontground);
	  
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_3);
	        trash.spawn(new Vector2f(380f,-50),new Vector2f(-34.3f,0),Prototyp.frontground);
	        
	        trash = new SpaceTrash(IEntity.SPACE_TRASH_4);
	        trash.spawn(new Vector2f(225,-2),new Vector2f(-60.3f,0),Prototyp.background);
	        //*/

	        setDone();
		}
}
