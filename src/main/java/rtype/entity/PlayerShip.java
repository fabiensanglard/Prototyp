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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.xml.sax.ext.DeclHandler;

import rtype.EventManager;
import rtype.KeyListener;
import rtype.Layer;
import rtype.Prototyp;


public class PlayerShip extends AnimatedEntity
{
	public float powerAccumulator = 0;
	public Orb orb = null ;
	
	public int hiscore=1;
	
    private int fire1Key ;
	private int leftKey ;
	private int rightKey ;
	private int fire2Key ;
	private int upKey ;
	private int downKey ;
	
	float POWER_SPEED = 2f;
	public float power = 0;
	public static float MAX_POWER = 4f;
	Blaster concentrateAnimation = new Blaster(this);
	
	BitUpgrade booster1 = null;
	BitUpgrade booster2 = null;
	
	private final static float MAX_SPEED = 230;
	private final static float ACCELLERATION = 1000;
	
	private float DEFAULT_DECELERATION = 700;
	private float x_deceleration = 0;
	private float y_deceleration = 0;
	
	private PlayerSpeed accelerateEntrity = new PlayerSpeed(this);
	
	public PlayerShip()
	{
		this(Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_SPACE,Keyboard.KEY_X);
	}
	
	public PlayerShip(int up, int down,int left, int right, int fire1, int fire2)
	{
		upKey = up;
		downKey = down;
		leftKey = left;
		rightKey = right;
		fire1Key = fire1;
		fire2Key = fire2;
		
		
		concentrateAnimation.spawn(new Vector2f(0,0),new Vector2f(0,0),Prototyp.fx);
		accelerateEntrity.spawn(new Vector2f(0,0),new Vector2f(0,0),Prototyp.fx);
		this.type = PLAYER_SHIP;
		init();
		//setRatio(0.17f);
		setRatio(0.25f);
		//this.setOrb(new LightningOrb(this));
		addEventListeners();
		
	}
	


	public void setOrb(Orb orb)
	{
		if (this.orb != null && this.orb.type == orb.type)
			return;
		if (this.orb != null )
			this.orb.unSpawn();
		this.orb = orb;
		this.orb.spawn(new Vector2f(-430,0),new Vector2f(0,0),Prototyp.bullets);
	}
	
	public void addBooster()
	{
		addBooster(Prototyp.bullets);
	}
	
	public void addBooster(Layer layer)
	{
		if (booster1 == null)
		{
			booster1 = new BitUpgrade(this);
			booster1.spawn(new Vector2f(5,20),new Vector2f(500,0),layer);
			return;
		}
		if (booster2 == null)
		{
			booster2 = new BitUpgrade(this);
			booster2.spawn(new Vector2f(5,-20),new Vector2f(500,0),layer);
			return;
		}
	}
	
	public void draw()
	{
		
		
		GL11.glLoadIdentity();    
		GL11.glPushMatrix();
		GL11.glPopMatrix();
		
		GL11.glTranslatef(position.x,position.y,Prototyp.DEFAULT_Z);                     // Translate Into/Out Of The Screen By z
		int animationFrame = (int) ((speed.y*4)/MAX_SPEED);
		animationFrame += 4;
		
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.animationTextures[animationFrame].getTextureId() );
		//GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glColor4f(1,1,1,1f);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(textureRight,textureUp); //Upper right
			GL11.glVertex2f(width, -height);
			
			GL11.glTexCoord2f(textureLeft,textureUp); //Upper left			
			GL11.glVertex2f(-width, -height);        
	   
			GL11.glTexCoord2f(textureLeft,textureDown); //Lower left
			GL11.glVertex2f(-width,height);
				
			GL11.glTexCoord2f(textureRight,textureDown); // Lower right
			GL11.glVertex2f(width,height);	
		}
       	GL11.glEnd();
       	
       	
       	//accelerateEntrity .draw();
       	//concentrateAnimation.draw();

	}
	
	
	
	public boolean isAnimationStarted()
	{
		return this.displayAnimation;
	}

	
	public void addEventListeners() {

		KeyListener fire2KeyEvent = new KeyListener()
		{
			public  void  onKeyDown(){};
			public  void  keyPressed()
			{
        		if (orb != null)
        			orb.setMove(Orb.ADJUDTING);
			};
			public  void  onKeyUp()
			{
				if (orb != null)
        			orb.setMove(Orb.STICKED);
			};
		};
	   EventManager.instance().addListener(fire2Key, fire2KeyEvent);
	   
	   
	   
	   KeyListener upKeyEvent = new KeyListener()
	   {
		   public  void  keyPressed()
			{
			   speed.y += ACCELLERATION * tick; 
	        	if (speed.y > MAX_SPEED)
	        		speed.y = MAX_SPEED;
			};
			
			public  void  onKeyUp()
			{
				y_deceleration = -DEFAULT_DECELERATION;
			};
	   };
	   EventManager.instance().addListener(upKey, upKeyEvent);
	   
	   KeyListener downKeyEvent = new KeyListener()
	   {
		   public  void  onKeyDown()
		   {
			   
		   };
		   public  void  keyPressed()
			{
			   speed.y -= ACCELLERATION * tick; 
	        	if (speed.y < -MAX_SPEED)
	        		speed.y = -MAX_SPEED;
			};
		   public  void  onKeyUp()
			{
			   y_deceleration = DEFAULT_DECELERATION;
			};  
	   };
	   EventManager.instance().addListener(downKey, downKeyEvent);
	   
	   
	   KeyListener leftKeyEvent = new KeyListener()
	   {
		   public  void  onKeyDown()
		   {
			   
		   };
		   public  void  keyPressed()
			{
			   speed.x -= ACCELLERATION * tick; 
	        	if (speed.x < -MAX_SPEED)
	        		speed.x = -MAX_SPEED;
			};
		   public  void  onKeyUp()
			{
			   x_deceleration = DEFAULT_DECELERATION;
			};  
	   };
	   EventManager.instance().addListener(leftKey, leftKeyEvent);
	   
	   KeyListener rightKeyEvent = new KeyListener()
	   {
		   public  void  onKeyDown()
		   {
			   
		   };
		   public  void  keyPressed()
			{
			   speed.x += ACCELLERATION * tick; 
	        	if (speed.x > MAX_SPEED)
	        		speed.x = MAX_SPEED;
	        	accelerateEntrity.startAnimation();
			};
		   public  void  onKeyUp()
			{
			   accelerateEntrity.stopAnimation();
       		   x_deceleration = -DEFAULT_DECELERATION;
			};  
	   };
	   EventManager.instance().addListener(rightKey, rightKeyEvent);
	   
	   
	   KeyListener fire1KeyEvent = new KeyListener()
	   {
		   public  void  onKeyDown()
		   {
			   concentrateAnimation.fire(0.1f);
			   concentrateAnimation.startChargingAnimation();
	       		if (orb != null)
	       			orb.startChargingAnimation();

	       		
	       		if (booster1 != null)
	       			booster1.fire(0);
	       		
	       		if (booster2 != null)
	       			booster2.fire(0);
		   };
		   public  void  keyPressed()
			{
			   	power += POWER_SPEED   * Prototyp.tick;
       			if (power > MAX_POWER)
       				power = MAX_POWER;
			};
		   public  void  onKeyUp()
			{
		       		// Fire blast if power is high enought...
		       		if (power > (MAX_POWER /10))
		       		{
		       			concentrateAnimation.fire(power/MAX_POWER);
		       			if (orb != null)
		       				orb.fire(power/MAX_POWER);
		       		
		       		}
		       		power = 0;
		       		concentrateAnimation.stopChargingAnimation();
		       		
		       		if (orb != null)
		       			orb.stopChargingAnimation();
			};  
	   };
	   EventManager.instance().addListener(fire1Key, fire1KeyEvent);
	}

	@Override
	public boolean collided(Entity entity)
	{
		return false;
	}
	
	@Override
	protected Vector2f interpolate(Vector2f old_position,Vector2f speed)
	{
		old_position.x = old_position.x + tick * speed.x;
		
		if (old_position.x > Prototyp.SCREEN_WIDTH /2)
			old_position.x = Prototyp.SCREEN_WIDTH /2;
		else
			if (old_position.x < -Prototyp.SCREEN_WIDTH /2)
				old_position.x = -Prototyp.SCREEN_WIDTH /2;
		
		
		old_position.y = old_position.y + tick * speed.y;
		
		if (old_position.y > Prototyp.SCREEN_HEIGHT /2)
			old_position.y = Prototyp.SCREEN_HEIGHT /2;
		else
			if (old_position.y < -Prototyp.SCREEN_HEIGHT /2)
				old_position.y = -Prototyp.SCREEN_HEIGHT /2;
		
		return old_position;
	}
	
	@Override	
	public void update()
	{
		this.interpolate(position,speed);
		
		float old_speed_x = speed.x;
		speed.x += x_deceleration * tick;
		if (old_speed_x * speed.x < 0)
		{
			x_deceleration = 0;
			speed.x = 0;
		}
		
		
		float old_speed_y = speed.y;
		speed.y += y_deceleration * tick;
		if (old_speed_y * speed.y < 0)
		{
			y_deceleration = 0;
			speed.y = 0;
		}
		
		
	}
}
