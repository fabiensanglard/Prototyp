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

import org.lwjgl.util.vector.Vector2f;

public class GLUTILS
{

	public static Vector2f makeNormalForPoints(Vector2f p1, Vector2f p2)
	{
		float diffY = p2.y - p1.y;
		float diffX = p2.x - p1.x;
		
		return new Vector2f(-diffY/diffX,1);
	}

	public static void rotateAroundZ(Vector2f vector, float angleRadian)
	{
		
		double cosAngle = Math.cos(angleRadian);
		double sinAngle = Math.sin(angleRadian);
		float savecValue =vector.x; 
		vector.x = (float) (vector.x * cosAngle - vector.y * sinAngle);
		vector.y = (float) (savecValue * sinAngle + vector.y * cosAngle) ;
		
	}
	
	public static float degresToRadians(float degres)
	{
		return (float)(degres * 2 * Math.PI / 360);
	}
	
	public static float radiansToDegres(float radians)
	{
		return (float)( 360 * radians / (2 * Math.PI));
	}
}
