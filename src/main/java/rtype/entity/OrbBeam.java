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
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;


import rtype.Prototyp;

public class OrbBeam extends Entity
{

	float percentage = 0;
	LightningOrb orb = null;
	private ArrayList meltingPoints = new ArrayList();
	float  fThickness = 4;
	private float disaperanceSpeed = 1.7f;// 0.3f;//     
	private float maxNormalWidth = 0;
	private float minNormalWidth = 0;
	private float SEQ_IN_BEAM = 8;
	private static Vector2f controlPoint[] = new Vector2f[3];
	
	public OrbBeam(LightningOrb orb, Vector2f[] p,float fThickness,boolean bWay,float r,float g,float b,float a, float modulo, float minimalArcHeight)
	{
		this.disaperanceSpeed = a * disaperanceSpeed;
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		this.fThickness = fThickness;
		this.type = ORB_BEAM;
		init();
		this.orb = orb;
		this.position.x = orb.position.x;
		this.position.y = orb.position.y;
		this.rotation = orb.rotation;
		this.damage = 1000;
		
		Vector2f p1 ;
		Vector2f p2 ;
		int i,k;
		
		
		for(i=0, k=1; i<(int)p.length-1; i++, k++)
		{
			p1=p[i];
			p2=p[k];
			meltingPoints.add(0,p1);
			Vector2f norm = GLUTILS.makeNormalForPoints(p1,p2);
			if (Logger.isLogActivate) Logger.log("norm"+norm);
			
			
			int ran = (int) (Prototyp.random.nextInt() % modulo + minimalArcHeight);
			
			
			Vector2f mid= bWay ?  
				new Vector2f(p1.x + ((p2.x-p1.x)*0.5f) + norm.x *  ran,
							 p1.y + ((p2.y-p1.y)*0.5f) + norm.y *  ran)
				:
				new Vector2f(p1.x  + ((p2.x - p1.x )*0.5f) - norm.x * ran,
							 p1.y  + ((p2.y - p1.y )*0.5f) - norm.y * ran
						);
			
			if (bWay)
			{
				if (norm.y * ran > this.maxNormalWidth)
					this.maxNormalWidth = norm.y * ran;
			}
			else
			{
				if (- norm.y * ran < this.minNormalWidth)
					this.minNormalWidth = - norm.y * ran;
			}	
			bWay = !bWay;
			
			float f = 1.0f/(float)(SEQ_IN_BEAM+1);
			float s= f;
			
			
			
			controlPoint[0]=p1;
			controlPoint[2]=mid;
			controlPoint[1]=p2;
			
			
			for(int n=0; n<SEQ_IN_BEAM; n++)
			{
				meltingPoints.add(0,calculatePointOnCurve(controlPoint,s));
				s+=f;
			}
			
		}
		
	}

	private static Vector2f calculatePointOnCurve(Vector2f[] c, float distance)
	{
		float a = 1;
		float b = 0;
		float step = distance;
		Vector2f p = new Vector2f();
		a-=step;
		b=1-a;
		p.x = c[0].x*a*a + c[2].x*2*a*b + c[1].x*b*b;
		p.y = c[0].y*a*a + c[2].y*2*a*b + c[1].y*b*b;
		return p;
	}
	
	float r = 0.3f;
	float g =  0.8f;
	float b = 1;
	float a=1;
	//float alphaToDisplay = a;
	
	public void draw()
	{
		if (a < 0)
		{
			layer.remove(this);
			if (Logger.isLogActivate) Logger.log("OrbBeam removed itself from the layer");
			return;
		}
		
			a -= disaperanceSpeed * tick ;
			
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.texture.getTextureId() );
	       	GL11.glLoadIdentity();            
			GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z); 
			GL11.glRotatef(rotation,0f,0f,1f);
			
	       	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	       	//GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

			Vector2f p,p2,p3,n,n2;
			
			
			GL11.glColor4f(r,g,b,a);
			
			GL11.glBegin(GL11.GL_QUADS);
			{

				for(int i=0; i<(int)meltingPoints.size()-2; i++)
				{
					
					p =  (Vector2f)meltingPoints.get(i);
					p2 = (Vector2f)meltingPoints.get(i+1);
					p3 = (Vector2f)meltingPoints.get(i+2);
					n=   (Vector2f) GLUTILS.makeNormalForPoints(p ,p2).scale(fThickness);
					n2=  (Vector2f) GLUTILS.makeNormalForPoints(p2,p3).scale(fThickness);

					if (i == meltingPoints.size()-3)
					{
						
						GL11.glColor4f(r,g,b,0);
						//System.out.println(0);
					}
					GL11.glTexCoord2f(15/32.0f,1);
					GL11.glVertex2f(p2.x+n2.x,p2.y+n2.y);
					
					GL11.glTexCoord2f(15/32.0f,0);
					GL11.glVertex2f(p2.x-n2.x,p2.y-n2.y);
					
					if (i == meltingPoints.size()-3)
					{
						GL11.glColor4f(r,g,b,a);
						//System.out.println(a);
					}	

					GL11.glTexCoord2f(15/32.0f,0);
					GL11.glVertex2f(p.x-n.x,p.y-n.y);
					
					GL11.glTexCoord2f(15/32.0f,1);
					GL11.glVertex2f(p.x+n.x,p.y+n.y);
					

					
					
					
					
					
	
				}
			}
	       	GL11.glEnd();
	       	
	       	GL11.glColor4f(1f,1f,1f,1f);		
	}
	
	public boolean collided(Entity entity)
	{
		return false;
	}

	public float getMaxNormalWidth()
	{
		return maxNormalWidth;
	}

	public float getMinNormalWidth()
	{
		return minNormalWidth;
	}
	
	
}
