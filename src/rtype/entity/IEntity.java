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


public interface IEntity
{
	public int PLANET = 0; 
	public int PLAYER_SHIP = 1;
	public int GREEN_ORB = 2;
	public int SCANLINE =3;
	public int BULLET = 4;
	public int FORCEBLAST = 5;
	public int SPACE_TRASH_1 = 6;
	public int SPACE_TRASH_2 = 7;
	public int SPACE_TRASH_3 = 8;
	public int SPACE_TRASH_4 = 9;
	public int FIRE_BALL = 10;
	public int STAR_1 = 11;
	public int STAR_2 = 12;
	public int STAR_3 = 13;
	public int STAR_4 = 14;
	public int STAR_5 = 15;
	public int STAR_6 = 16;
	public int FORCE_CHARGE = 15;
	public int PLAYER_SPEED = 16;
	public int LADYBIRD = 17;	
	public int EXPLOSION1 = 18;
	public int EXPLOSION2 = 19;
	public int ORB_BEAM = 20 ;
	public int FONT = 21 ;
	public int ENEMY_BULLET = 22 ;
	public int BULLET_HIT_YELLOW = 23 ;
	public int BULLET_HIT_GREEN = 24 ;
	public int BULLET_HIT_BLUE = 25 ;
	public int BLUE_ORB = 26;
	public int PINK_ORB = 27;
	public int RED_ORB = 28;
	public int ENEMY_PIECE_1 = 29;
	public int ENEMY_PIECE_2 = 30;
	public int ENEMY_PIECE_3 = 31;
	public int ENEMY_PIECE_4 = 32;
	public int ENEMY_PIECE_5 = 33;
	public int ENEMY_PIECE_6 = 34;
	public int ENEMY_PIECE_7 = 35;
	public int ENEMY_PIECE_8 = 36;
	public int BIT_UPGRADE = 37;
	public int IMPLOSION = 38;
	
	public int BONUS_BOOSTER = 39;
	public int BONUS_LIGHTNING_ORB = 40;
	public int BONUS_GRAVITY_ORB = 41;
	public int BONUS_RAPID_SHOOT_ORB = 42;
	public int BONUS_CRYSTAL_ORB = 43;
	public int BONUS_MULTI_SHOOT_ORB = 44;
	public int BONUS_SPEED = 45;
	public int BONUS_HOMING_MISSILE = 46;
	// Leaving space for futur bonus
	
	public int X  = 63;
	public int BULLET_RAPID_FIRE = 64;
	public int BULLET_MULTI_SHOOT = 65;
	public int BULLET_BASE = 66;
	public int MISSILE = 67;
	public int SMOKE = 68;
	//public int PLANET2 = 69;
	public int SPACE_TRASH_5 = 70;
}
