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

public class ForceBlast extends Entity
{
	public ForceBlast(float chargePercentage)
	{
		this.type = FORCEBLAST;
		init();
		
		this.setRatio(chargePercentage/1.8f);
		this.damage = 300 * chargePercentage;
		this.life = 300*chargePercentage;
	}

	@Override
	public void draw()
	{
		// TODO Auto-generated method stub
		GL11.glColor4f(1,1,1,0.85f);
		
		super.draw();
		GL11.glColor4f(1,1,1,1);
	}
	
	
}
