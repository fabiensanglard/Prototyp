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
import rtype.Texture;

public abstract class AnimatedEntity extends Entity
{

	protected Texture[] animationTextures;
	
	public void init()
	{
		this.animationTextures = Prototyp.textureLoader.getAnimation(this.type);
		this.original_width = animationTextures[0].getTextureWidth();
		this.original_height = animationTextures[0].getTextureHeight();
	
	}
	
	public abstract void draw();
	
	// This field set if player is charging a blast
	public boolean displayAnimation;
	
	// This field hold the blast charge..
	protected float animationCursor;
	
	protected float animationSpeed = 4.4f;
	

	public void startAnimation()
	{
		this.displayAnimation = true;
		
	}
	public void stopAnimation()
	{
		this.displayAnimation = false;
		this.animationCursor = 0;
		
	}
}

