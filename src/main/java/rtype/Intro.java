package rtype;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import rtype.entity.IEntity;
import rtype.entity.Planet;
import rtype.entity.Text;

public class Intro {

	private Layer layer; 
	private Prototyp prototyp;
	boolean introOn = true;
	
	public Intro(Prototyp prototyp)
	{
		this.prototyp = prototyp;
		
		Planet planet= new Planet(IEntity.PLANET);
	    planet.spawn(new Vector2f(320,0),new Vector2f(7f*-1,0),Prototyp.background);
		
		layer = new Layer();
		
		Text title = new Text (" . Prototyp .");
		Text commandLabel = new Text("- Commands -");
		Text commandLabel0 = new Text("P            : Pause.");
		Text commandLabel1 = new Text("F1           : Start homing missile.");
		Text commandLabel2 = new Text("F2           : Start enemy waves.");
		Text commandLabel3 = new Text("X            : Detach/Move Orb.");
		Text commandLabel4 = new Text("Arrow key    : Move.");
		Text commandLabel5 = new Text("Space        : Fire ( maintain to charge).");
		Text commandLabel6 = new Text("Press SPACE to Start !!");
		Vector2f immobile = new Vector2f(0,0);
		
		int pointY = 240;
		int pointX = -270;
		int interspace = 20;
		
		title.spawn(new Vector2f(pointX+160,pointY -=interspace*4), immobile, layer);
		commandLabel.spawn(new Vector2f(pointX+200,pointY -=interspace*4), immobile, layer);
		commandLabel0.spawn(new Vector2f(pointX,pointY -=interspace*2), immobile, layer);
		commandLabel1.spawn(new Vector2f(pointX,pointY -=interspace), immobile, layer);
		commandLabel2.spawn(new Vector2f(pointX,pointY -=interspace), immobile, layer);
		commandLabel3.spawn(new Vector2f(pointX,pointY -=interspace), immobile, layer);
		commandLabel4.spawn(new Vector2f(pointX,pointY -=interspace), immobile, layer);
		commandLabel5.spawn(new Vector2f(pointX,pointY -=interspace), immobile, layer);
		commandLabel6.spawn(new Vector2f(pointX+150,pointY -=interspace*3), immobile, layer);

		
		KeyListener space = new KeyListener()
		{
				public void onKeyUp()
				{
					introOn = false;
					Prototyp.timer.resume();
				}
		};
		
		EventManager.instance().addListener(Keyboard.KEY_SPACE,space);
	}
	
	public void play()
	{
    	Prototyp.timer.pause();

    	while (introOn)
    	{
    		
    		prototyp.update();
            
    		prototyp.render();
    		layer.render();
    		
    		Display.update();
    		
    		if (prototyp.exitRequested())
    		{
    			introOn = false;
    			prototyp.gameOff = true;
    		}
    		
    		EventManager.instance().checkEvents();
    	}

    	EventManager.instance().clear();
	}
}
