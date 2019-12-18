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

import generator.EnemyWave;
import generator.GeneratorSet;
import generator.HomingMissileGenerator;
import generator.IGenerator;
import generator.IntroGenerator;
import generator.LadyBirdGenerator;
import generator.StarGenerator;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import org.lwjgl.Sys;
//import org.lwjgl.devil.IL;
//import org.lwjgl.devil.ILU;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Timer;
import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.vector.Vector2f;

import rtype.entity.Bonus;
import rtype.entity.Entity;
import rtype.entity.Explosion;
import rtype.entity.HomingMissile;
import rtype.entity.Text;
import rtype.entity.IEntity;
import rtype.entity.LadyBird;
import rtype.entity.Planet;
import rtype.entity.PlayerShip;
import rtype.entity.SpaceTrash;
import rtype.entity.Star;
import rtype.entity.TextEntityCounter;
/* TODO 
 	- Fix texture loading, DEVil is not yet usable with OS X or Linux platform
    - Intro with musique would be great to add 
    - Add distorsion effect for magnetic orb
    - Implement link between orb and ship
    - Add smartBomb: I am thinking of a rain of homing missiles
 */

public class Prototyp {

	// Init parameters
	static public final int SCREEN_WIDTH = 800;
	static public final int SCREEN_HEIGHT = 600; //480
	private static final boolean FULL_SCREEN = false;
	
	// 
	boolean gameOff = false;
	
	
	// Default values for sprites
	public static float  DEFAULT_Z = 0;
	public static final Vector2f DEFAULT_SCROLLING_SPEED = new Vector2f(-5,0); 

	// This is the player's sprite
    public static PlayerShip player1 = null;

    // Set of layers, drawn in a different order ( see render method)
    static public Layer bullets = new Layer();
    static public Layer enemies = new Layer();
    static public Layer fx = new Layer();
    static public Layer bonus = new Layer();
    static public Layer background = new Layer();
    static public Layer frontground = new Layer();
    static public Layer text = new Layer();
    
    // Random object, used by every object that need randomness during the game
    public final static Random random = new Random(System.currentTimeMillis());

    static boolean useDevil = false;
    
    // Variables used to calculate fps
    private int frames;
    static float deltas = 0;
    static float fps;
    
    // Text object used to display fps, score and entity total count
	private Text textFPS;
	private Text textHisCore;
	private TextEntityCounter entitiesCount ;
	
	static Timer timer = new Timer();
	static float lastTime = timer.getTime();
	
	// Measure time elapsed since last frame renderer
	// This is the heart variable of the engine
	public static float tick;
	
	// Generators generate entity for the game
	public static GeneratorSet generator = null;
	
	// Texture loader is used to load and server textures to entities on init 
	public static ITextureLoader textureLoader = null;
	
	// Starting point
    public static void main(String args[]) {  
    	
    	if (args.length > 0)
    		useDevil = "useDevil".equals(args[0]);
        Prototyp l8 = new Prototyp();
        l8.run();
    }
    
    public Prototyp()
    {
    	init();
    	initGL();
    }

    // Main loop
    public void run() {

    	
    	
    	Intro intro = new Intro(this);
    	intro.play();
    	
    	BonusDesc bonusDesc = new BonusDesc(this);
    	bonusDesc.play();
    	
    	
    	addBasicEntities();
    	
    	player1.addEventListeners();
    	
    	Bonus b = BonusFactory.createBonus(IEntity.BONUS_LIGHTNING_ORB);
    	b.spawn(new Vector2f(player1.position.x+100,player1.position.y), DEFAULT_SCROLLING_SPEED, bonus);
    	
    	
    	addControlKeys();
    	
    	
        while (!gameOff) 
        {
           	heartBeat();
            getEntries();
            update();
            checkCollisons(); 
            render();

            frames++;
            if (frames == 50)
            {
            	fps = frames/deltas;
            	frames = 0;
            	deltas = 0 ;
            	textFPS.setString("fps:"+(int)fps);
            }
            Display.update();
           // Display.sync(10);
            generator.generate();
           
        }
        Display.destroy();
    }



private void createWindow(int screenWidth, int screenHeight, boolean fullscreen) throws Exception {
    	    
	
	    if (!fullscreen) // create windowed mode
	    	Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
	    else
    	{
    		Display.setFullscreen(true);
            try
            {
            	DisplayMode dm[] = org.lwjgl.util.Display.getAvailableDisplayModes(320, 240, -1, -1, -1, -1, 60, 85);
                org.lwjgl.util.Display.setDisplayMode(dm, new String[] {
                    "width=" + screenWidth, "height=" + screenHeight, "freq=85", 
                    "bpp=" + Display.getDisplayMode().getBitsPerPixel()
                });
            }
            catch(Exception e)
            {
                Sys.alert("Error", "Could not start full screen, switching to windowed mode");
                Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
            }           
    	}

        Display.create();
    }




	 private void addBasicEntities() {
		 player1 = new PlayerShip(); // Calling default constructor, if you want to chanage them: PlayerShip(Use Keyboard.KEY_UP,Keyboard.KEY_DOWN,Keyboard.KEY_LEFT,Keyboard.KEY_RIGHT,Keyboard.KEY_SPACE,Keyboard.KEY_X)
	        player1.spawn(new Vector2f(-150,-100),new Vector2f(0,0),bullets);
	        player1.addBooster();
	        
	        textFPS = new Text("");
	        textFPS.spawn(new Vector2f(SCREEN_WIDTH/2-100,SCREEN_HEIGHT/2-20),new Vector2f(0,0),text);
	
	        textHisCore = new Text("HISCORE:");
	        textHisCore.spawn(new Vector2f(-SCREEN_WIDTH/2+20,SCREEN_HEIGHT/2 - 20),new Vector2f(0,0),text);
	        
	        entitiesCount = new TextEntityCounter();
	        entitiesCount.spawn(new Vector2f(-SCREEN_WIDTH/2+20,-SCREEN_HEIGHT/2 + 20),new Vector2f(0,0),text);
		
	}


	public static void heartBeat() {
		 Timer.tick();
         tick = timer.getTime() - lastTime;
         deltas+= tick;
         lastTime = timer.getTime();
		
	}


	void update()
	{
	    bullets.update();
	    enemies.update();
	    fx.update();
	    background.update();
	    bonus.update();
		frontground.update();	
		text.update();
	}



	private void checkCollisons()
	{
		// Check bullets with enemies
		ArrayList<Entity> bulletsArray = bullets.entities;
		ArrayList<Entity> enemiesArray = enemies.entities;
		Entity currentBullet = null;
		Entity currentEnemy = null;
		for (int i = 0; i < bulletsArray.size() ; i++)
		{
			
			
			for (int j= 0 ; j < enemiesArray.size(); j++ )
			{
				if (j < 0)
					continue;
				if (i<0)
					break;
				currentBullet = bulletsArray.get(i);
				currentEnemy = enemiesArray.get(j);
				
				if (Collision.boxBoxOverlap(currentBullet,
											currentEnemy
											))
				{
					player1.hiscore++;
					
					if (currentBullet.collided(currentEnemy))
					i--;
					
					if (currentEnemy.collided(currentBullet))
					j--;
				}
				
			}
		}
		textHisCore.setString("HISCORE:"+player1.hiscore);
		
		// Check players with bonuses
		ArrayList<Entity> bonusArray = bonus.entities;
		Entity currentBonus = null;
		for (int j= 0 ; j < bonusArray.size(); j++ )
		{
			currentBonus = bonusArray.get(j);
			if(
				Collision.boxBoxOverlap( player1,
										 currentBonus
										)
					
			  )
			{
				if (currentBonus.collided(player1))
					j--;
				player1.collided(currentBonus);
			}
		}
	}
	
	
	// Init engine
	private void init() 
    {
    	try
    	{
    		Mouse.setGrabbed(false);
	    	
    		createWindow(SCREEN_WIDTH, SCREEN_HEIGHT, FULL_SCREEN) ;
	    	
	        
	        
	        
	        
	        createOffScreenBuffer();
	        
	        //Display.setVSyncEnabled(true);
	        
	        //TODO This is the generators
	        generator = new GeneratorSet();
	        generator.addGenerator(new StarGenerator());
	        generator.addGenerator(new IntroGenerator());
	        generator.addGenerator(new EnemyWave(100,EnemyWave.rate));
	        generator.addGenerator(new EnemyWave(150,EnemyWave.rate/2));
	        
      	    
	        generator.addGenerator(new EnemyWave(-100,EnemyWave.rate + 10));
	        generator.addGenerator(new EnemyWave(-150,EnemyWave.rate/2+ 10));
	        
	        generator.addGenerator(new EnemyWave(0,EnemyWave.rate + 20));
	        generator.addGenerator(new EnemyWave(50,EnemyWave.rate/2 + 20));
	        
	        generator.addGenerator(new LadyBirdGenerator(EnemyWave.rate/2 + 30));
	        

    	}
    	catch(Exception e)
    	{
    		e.printStackTrace(System.out);
    	}
    }

    private void initGL() {
    	

        GL11.glEnable(GL11.GL_TEXTURE_2D); // Enable Texture Mapping
        
        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
        GL11.glClearDepth(1.0f); // Depth Buffer Setup
        GL11.glDisable(GL11.GL_DEPTH_TEST); // Enables Depth Testing
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDepthMask(false); 
        GL11.glMatrixMode(GL11.GL_PROJECTION); // Select The Projection Matrix
        GL11.glLoadIdentity(); // Reset The Projection Matrix


        GLU.gluOrtho2D(-(int)SCREEN_WIDTH/2,(int)SCREEN_WIDTH/2,(int)-SCREEN_HEIGHT/2,(int)SCREEN_HEIGHT/2);
       
        GL11.glMatrixMode(GL11.GL_MODELVIEW); 

        if (useDevil) 
 	        ;//textureLoader = new DevilTextureLoader();
        else
        {
        	textureLoader = new WorkAroundTextureLoader();	
        }
        textureLoader.init();
    }
	
    
    private static boolean P_KEY_PRESSED = false;
    
    // LadyBirds
    private static boolean P_KEY_F1 = false;
    private IGenerator ladyBirdGenerator = new LadyBirdGenerator(30);
    
    //HomingMissiles
    private static boolean P_KEY_F2 = false;
    private IGenerator homingGenerator = new HomingMissileGenerator();
    
    private static Text pause = null;
    
    private void addControlKeys()
    {
    	 KeyListener pauseKeyEvent = new KeyListener()
  	   {			
  			public  void  onKeyDown()
  			{
  				if (timer.isPaused())
        		{
        			timer.resume();
        			pause.unSpawn();
        		}
        		else
        		{
        		    pause = new Text("GAME PAUSED");
        			//pause.setRatio(1.5f);
        			pause.spawn(new Vector2f(-50,0),new Vector2f(0,0),frontground);
        			timer.pause();
        		}
  			};
  	   };
  	   EventManager.instance().addListener(Keyboard.KEY_P, pauseKeyEvent);
  	   
  	   
  	  KeyListener homingMissileKeyEvent = new KeyListener()
	   {			
			public  void  onKeyDown()
			{
				if (generator.contains(homingGenerator))
        			generator.removeGenerator(homingGenerator);
        		else
        			generator.addGenerator(homingGenerator);
			};
	   };
	   EventManager.instance().addListener(Keyboard.KEY_F1, homingMissileKeyEvent);
	   
	   KeyListener enemiesKeyEvent = new KeyListener()
	   {			
			public  void  onKeyDown()
			{
				if (generator.contains(ladyBirdGenerator))
        			generator.removeGenerator(ladyBirdGenerator);
        		else
        			generator.addGenerator(ladyBirdGenerator);
			};
	   };
	   EventManager.instance().addListener(Keyboard.KEY_F2, enemiesKeyEvent);
    }
    
    private void getEntries() 
    {
        if (exitRequested())
        	gameOff = true;
        
        EventManager.instance(). checkEvents();
       
    }
    
    boolean exitRequested() {
    	return (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Display.isCloseRequested()) ;
	}


	// This method render current scene state
    void render() 
    {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);          // Clear The Screen And The Depth Buffer        
   
    	background.render();
    	
    	// Make backGround darker
    	{
    		fadeAlpha = 0.65f;
    		fadeScreen(false);
    		fadeAlpha = 0;
    	}
    	
    	enemies.render();
    	
        bonus.render();
        
        fx.render();
        
        // This make a copy of the screen in a texture
        // It is used later for deformations effects
        saveScreen();
        
        applyDistorsions();
        
        bullets.render();
        
    	frontground.render();
    	
    	text.render();
    	
    	fadeScreen(true);
    	
    	//this.drawPowerBar();
    	//this.renderScanLines();
    }
    
    private void saveScreen()
    {
        GL11.glLoadIdentity();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D,SCREEN_TEXTURE_ID);
    	GL11.glCopyTexSubImage2D(GL11.GL_TEXTURE_2D,0, 0, 0, 0, 0,(int)SCREEN_WIDTH,(int)SCREEN_HEIGHT);
    	
    }
    

    // This is called when player charge power
    /* ----------------------------------------------
       -											-
       -											-
       -											-
       -											-              
       -				    ---						-
       -				    - -						-
       -				    ---	 					-
       -											-
       -											-
       -											-
       -											-
       -											-       
       ----------------------------------------------
    */
    private void applyDistorsions()
	{
	
    	if (player1 == null)
    		return;
    	
    	float chargeP = player1.power/PlayerShip.MAX_POWER ;
 
    
    	float R_WIDTH = 350 * chargeP;
    	float R_HEIGHT = 200 * chargeP;
    	float MIDDLE_X_ON_SCREEN = R_WIDTH/2* chargeP;
    	
    	float R_POSITION_X = player1.position.x+25;
    	float R_POSITION_Y = player1.position.y-R_HEIGHT/2;
    	
    	float TEXT_OUT_X_LOW = (R_POSITION_X+(SCREEN_WIDTH/2f))/1024f ;
    	float TEXT_OUT_X_HIGH = (R_POSITION_X+R_WIDTH+(SCREEN_WIDTH/2f))/1024;
    	float TEXT_OUT_Y_LOW = (R_POSITION_Y+(SCREEN_HEIGHT/2f))/1024;
    	float TEXT_OUT_Y_HIGH = (R_POSITION_Y+R_HEIGHT+(SCREEN_HEIGHT/2f))/1024;
    	
    	float TEXT_IN_X_LOW = (R_POSITION_X+R_WIDTH/2+(SCREEN_WIDTH/2f))/1024f ;
    	float TEXT_IN_X_HIGH = (R_POSITION_X+R_WIDTH/2 + 1+(SCREEN_WIDTH/2f))/1024;
    	float TEXT_IN_Y_LOW = (R_POSITION_Y+R_HEIGHT/2 +(SCREEN_HEIGHT/2f))/1024f ;
    	float TEXT_IN_Y_HIGH = (R_POSITION_Y+R_HEIGHT/2 + 1+(SCREEN_HEIGHT/2f))/1024;
    	
    	float SCR_OUT_X_LOW = R_POSITION_X ;
    	float SCR_OUT_X_HIGH = R_POSITION_X+R_WIDTH;
    	float SCR_OUT_Y_LOW = R_POSITION_Y;
    	float SCR_OUT_Y_HIGH = R_POSITION_Y+R_HEIGHT;
    	
    	float SCR_IN_X_LOW = R_POSITION_X+MIDDLE_X_ON_SCREEN  ;
    	float SCR_IN_X_HIGH = R_POSITION_X+MIDDLE_X_ON_SCREEN + 1;
    	float SCR_IN_Y_LOW = R_POSITION_Y+R_HEIGHT/2;
    	float SCR_IN_Y_HIGH = R_POSITION_Y + R_HEIGHT/2 + 1;

    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
    	GL11.glColor4f(1,1,1,0.75f);
    	GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(TEXT_IN_X_HIGH,TEXT_IN_Y_HIGH); 			
			GL11.glVertex2f(SCR_IN_X_HIGH, SCR_IN_Y_HIGH); // Lower right

			GL11.glTexCoord2f(TEXT_OUT_X_HIGH,TEXT_OUT_Y_HIGH); 			
			GL11.glVertex2f(SCR_OUT_X_HIGH,SCR_OUT_Y_HIGH);       //Lower left 
			
			GL11.glTexCoord2f(TEXT_OUT_X_HIGH,TEXT_OUT_Y_LOW); 
			GL11.glVertex2f(SCR_OUT_X_HIGH,SCR_OUT_Y_LOW); //upper left

			GL11.glTexCoord2f(TEXT_IN_X_HIGH,TEXT_IN_Y_LOW); 
			GL11.glVertex2f(SCR_IN_X_HIGH,SCR_IN_Y_LOW); // //upper right
		}
       	GL11.glEnd();

       	GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(TEXT_IN_X_HIGH,TEXT_IN_Y_LOW); 			
			GL11.glVertex2f(SCR_IN_X_HIGH, SCR_IN_Y_LOW); // Lower right

			GL11.glTexCoord2f(TEXT_OUT_X_HIGH,TEXT_OUT_Y_LOW); 			
			GL11.glVertex2f(SCR_OUT_X_HIGH,SCR_OUT_Y_LOW);       //Lower left 
			
			GL11.glTexCoord2f(TEXT_OUT_X_LOW,TEXT_OUT_Y_LOW); 
			GL11.glVertex2f(SCR_OUT_X_LOW,SCR_OUT_Y_LOW); //upper left

			GL11.glTexCoord2f(TEXT_IN_X_LOW,TEXT_IN_Y_LOW); 
			GL11.glVertex2f(SCR_IN_X_LOW,SCR_IN_Y_LOW); // //upper right
		}
       	GL11.glEnd();

       	GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(TEXT_IN_X_LOW,TEXT_IN_Y_LOW); 			
			GL11.glVertex2f(SCR_IN_X_LOW, SCR_IN_Y_LOW); // Lower right

			GL11.glTexCoord2f(TEXT_OUT_X_LOW,TEXT_OUT_Y_HIGH); 			
			GL11.glVertex2f(SCR_OUT_X_LOW,SCR_OUT_Y_HIGH);       //Lower left 
			
			GL11.glTexCoord2f(TEXT_OUT_X_HIGH,TEXT_OUT_Y_HIGH); 
			GL11.glVertex2f(SCR_OUT_X_HIGH,SCR_OUT_Y_HIGH); //upper left

			GL11.glTexCoord2f(TEXT_IN_X_HIGH,TEXT_IN_Y_HIGH); 
			GL11.glVertex2f(SCR_IN_X_HIGH,SCR_IN_Y_HIGH); // //upper right
		}
       	GL11.glEnd();

       	GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(TEXT_IN_X_LOW,TEXT_IN_Y_LOW); 			
			GL11.glVertex2f(SCR_IN_X_LOW, SCR_IN_Y_LOW); // Lower right

			GL11.glTexCoord2f(TEXT_OUT_X_LOW,TEXT_OUT_Y_LOW); 			
			GL11.glVertex2f(SCR_OUT_X_LOW,SCR_OUT_Y_LOW);       //Lower left 
			
			GL11.glTexCoord2f(TEXT_OUT_X_LOW,TEXT_OUT_Y_HIGH); 
			GL11.glVertex2f(SCR_OUT_X_LOW,SCR_OUT_Y_HIGH); //upper left

			GL11.glTexCoord2f(TEXT_IN_X_LOW,TEXT_IN_Y_HIGH); 
			GL11.glVertex2f(SCR_IN_X_LOW,SCR_IN_Y_HIGH); // //upper right
		}
       	GL11.glEnd();
      	//GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
      	GL11.glColor4f(1,1,1,1);
	}

    
	public static float fadeAlpha = 0;
	public void fadeScreen(boolean draw_fb) // draw_fb is a quick and dirty fix, fb should be in a specificl layer "postFadeEffect" or something
	{
		
		if (fadeAlpha > 0.1)
		{
			
			GL11.glLoadIdentity();
			GL11.glTranslatef(0,0,Prototyp.DEFAULT_Z);
			GL11.glColor4f(0,0,0,fadeAlpha/1.2f);
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			//GL11.glColor4f(0.5f,0.5f,0.5f,0.5f);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);
			GL11.glBegin(GL11.GL_QUADS);
			{
				GL11.glVertex2f(Prototyp.SCREEN_WIDTH/2, -Prototyp.SCREEN_HEIGHT/2);		
				GL11.glVertex2f(-Prototyp.SCREEN_WIDTH/2, -Prototyp.SCREEN_HEIGHT/2);        
				GL11.glVertex2f(-Prototyp.SCREEN_WIDTH/2,Prototyp.SCREEN_HEIGHT/2);
				GL11.glVertex2f(Prototyp.SCREEN_WIDTH/2,Prototyp.SCREEN_HEIGHT/2);
			}
	       	GL11.glEnd();
	       	GL11.glColor4f(1,1,1,1);
	       	GL11.glEnable(GL11.GL_TEXTURE_2D);  
	       	GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE);
		}
		
		if (player1 != null && player1.orb != null && player1.orb.fb!= null && player1.orb.fb.displayAnimation && draw_fb)
		{
			player1.orb.fb.updateTick();
			player1.orb.fb.draw(fadeAlpha*2);
		}
	}
	

    // Not used, would need more work
	private void drawPowerBar()
	{
		GL11.glLoadIdentity();
		 GL11.glTranslatef(0,0,Prototyp.DEFAULT_Z);
		GL11.glColor4f(0,0,1,1f);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0,-0 );
			GL11.glVertex2f(0,-0 -100);
			GL11.glVertex2f(0 + player1.power*10/4 ,0 );
			GL11.glVertex2f(0 + player1.power*10/4 ,0 -100 );
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);                     
		GL11.glColor4f(1,1,1,1f);
	}

	// Not used, didn't like the effect
	private void renderScanLines()
	{
    	GL11.glLoadIdentity();  
    	GL11.glTranslatef(0,0,Prototyp.DEFAULT_Z);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
    	GL11.glBindTexture(GL11.GL_TEXTURE_2D,textureLoader.getTexture(IEntity.SCANLINE).getTextureId());
    	GL11.glColor4f(1,1,1,1);
    	
    	GL11.glBegin(GL11.GL_QUADS);
	    	GL11.glTexCoord2f(20,0); //Upper right
			GL11.glVertex2f(SCREEN_WIDTH/4, -SCREEN_HEIGHT/4);
			
			GL11.glTexCoord2f(0,0); //Upper left			
			GL11.glVertex2f(-SCREEN_WIDTH/4, -SCREEN_HEIGHT/4);        
	   
			GL11.glTexCoord2f(0,20); //Lower left
			GL11.glVertex2f(-SCREEN_WIDTH/4,SCREEN_HEIGHT/4);
				
			GL11.glTexCoord2f(20,20); // Lower right
			GL11.glVertex2f(SCREEN_WIDTH/4,SCREEN_HEIGHT/4 );	
    		
	    GL11.glEnd();
	}
    
	// This is the address where screen copy is stored
	public static int SCREEN_TEXTURE_ID = 0;
	private void createOffScreenBuffer()
	{
		int bytesPerPixel = 3;
		ByteBuffer scratch = ByteBuffer.allocateDirect(1024* 1024  * bytesPerPixel);
		IntBuffer buf = ByteBuffer.allocateDirect(12).order(ByteOrder.nativeOrder()).asIntBuffer();
		GL11.glGenTextures(buf); // Create Texture In OpenGL
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, buf.get(0));
		SCREEN_TEXTURE_ID = buf.get(0);
		
		int glType = GL11.GL_RGB;
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
	    GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
	    GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0,glType,1024,1024, 0,glType, GL11.GL_UNSIGNED_BYTE, scratch);

	}
    
}
