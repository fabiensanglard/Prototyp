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

public class SpaceTrash extends Entity
{

	public SpaceTrash(int type)
	{
		this.type = type;
		init();
		this.setRatio(0.5f);
		
	}

	public void draw()
	{
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
	   	super.draw();
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
	}
}
