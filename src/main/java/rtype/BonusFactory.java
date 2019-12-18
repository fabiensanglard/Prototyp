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

package rtype;

import rtype.entity.BonusCrystalOrb;
import rtype.entity.BonusRapidShootOrb;
import rtype.entity.BonusBooster;
import rtype.entity.BonusLightningOrb;
import rtype.entity.BonusMagneticOrb;
import rtype.entity.IEntity;
import rtype.entity.Bonus;

public class BonusFactory
{

	public static Bonus createBonus(int type)
	{
		Bonus b = null;
		switch(type)
		{
			case IEntity.BONUS_BOOSTER : b=  new BonusBooster() ; break; 
			case IEntity.BONUS_LIGHTNING_ORB : b=  new BonusLightningOrb() ; break;
			case IEntity.BONUS_GRAVITY_ORB : b=  new BonusMagneticOrb() ; break;
			case IEntity.BONUS_RAPID_SHOOT_ORB : b=  new BonusRapidShootOrb() ; break;
			case IEntity.BONUS_CRYSTAL_ORB : b=  new BonusCrystalOrb() ; break;
			
		}
		return b;
	}
}
