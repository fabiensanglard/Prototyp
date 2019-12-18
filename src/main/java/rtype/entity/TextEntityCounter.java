package rtype.entity;

import rtype.Prototyp;

public class TextEntityCounter extends Text {

	public TextEntityCounter()
	{
		super("");
	}
	
	public TextEntityCounter(String string) {
		super(string);
	
	}

	public void update()
	{
		super.update();
		int entitiesCounter = 0;
	    entitiesCounter += Prototyp.bullets.entities.size();
	    entitiesCounter += Prototyp.enemies.entities.size();
		entitiesCounter += Prototyp.fx.entities.size();
		entitiesCounter += Prototyp.background.entities.size();
		entitiesCounter += Prototyp.frontground.entities.size();
		
		setString("Entities #:"+entitiesCounter);
	}
}
