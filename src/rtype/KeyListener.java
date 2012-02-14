package rtype;

import org.lwjgl.input.Keyboard;

import rtype.entity.Orb;

public class KeyListener {

	private int keyMonitored;
	private boolean keyMonitoredWasPressed;
	
	public void setKeyMonitored(int keyMonitored) {
		this.keyMonitored = keyMonitored;
	}
	
	public  void  onKeyDown(){};	// This is triggered exactly when the key is pressed.
	public  void  keyPressed(){};	// This is triggered will the key is being pressed.
	public  void  onKeyUp(){};		// This is triggered exactly when the key is released.
	
	public void checkKey()
	{
		if(Keyboard.isKeyDown(keyMonitored)) 
        {
        	if(keyMonitoredWasPressed)
        		keyPressed();
        	else
        	{
        		keyMonitoredWasPressed = true;
        		onKeyDown();
        	}
        	
        }
        else
        	if (keyMonitoredWasPressed)
        	{
        		onKeyUp();
        		keyMonitoredWasPressed = false;
        	}
	}
	
}
