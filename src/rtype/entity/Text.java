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

import rtype.Prototyp;

public class Text extends AnimatedEntity
{
	private String string = null;
	public void setString(String string)
	{
		this.string = string;
		chars = this.string.toCharArray();
	}
	
	public static final int LEFT_TO_RIGHT = 0;
	public static final int RIGHT_TO_LEFT = 1;
	private int mode = LEFT_TO_RIGHT;
	public void setMode(int mode)
	{
		this.mode = mode;
	}
	
	private char[] chars ;
	public Text(String string)
	{
		this.setString(string);
		
		this.type = FONT;
		init();
		setRatio(0.4f);
	}
	
	public void draw()
	{
		GL11.glLoadIdentity();            
       	GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
       	GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
		
		if (mode == LEFT_TO_RIGHT)
		{
			for (int i=0;i<chars.length;i++)
			{
				drawChar(i);
				GL11.glTranslatef(this.width+1,0,0);
			}
		}
		if (mode == RIGHT_TO_LEFT)
		{
			for (int i=chars.length-1; i>=0;i--)
			{
				drawChar(i);
				GL11.glTranslatef(-this.width+1,0,0);
			}
		}
	
	}
	
	public void update()
	{
		
	}
	
	private void drawChar(int i)
	{
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[chars[i]].getTextureId() );
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
	
}
