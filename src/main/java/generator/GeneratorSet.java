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

import java.util.ArrayList;
import java.util.Iterator;

import rtype.Prototyp;

public class GeneratorSet
{
	ArrayList<IGenerator> generators = new ArrayList<IGenerator>();
	float timeAccumulator = 0;
	
	public void addGenerator(IGenerator gen)
	{
		generators.add(gen);
	}
	
	public void removeGenerator(IGenerator gen)
	{
		generators.remove(gen);
	}
	
	public void generate()
	{
		timeAccumulator += Prototyp.tick;
		
		IGenerator generator = null;
		for (Iterator<IGenerator> iterator = generators.iterator(); iterator.hasNext() ;)
		{
			generator = iterator.next();
			
			if (timeAccumulator > generator.getDelay())
				generator.generateEntities();
			
			if (generator.isDone())
				iterator.remove();
		}
		
	}
	
	public boolean contains(IGenerator gen)
	{
		return (this.generators.indexOf(gen) > 0);
	}
}
