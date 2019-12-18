package rtype;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import rtype.entity.BonusBooster;
import rtype.entity.BonusCrystalOrb;
import rtype.entity.BonusLightningOrb;
import rtype.entity.BonusMagneticOrb;
import rtype.entity.BonusRapidShootOrb;
import rtype.entity.CrystalOrb;
import rtype.entity.LightningOrb;
import rtype.entity.MagneticOrb;
import rtype.entity.PlayerShip;
import rtype.entity.RapidFireOrb;
import rtype.entity.Text;

public class BonusDesc {

	private Layer layer = new Layer(); 
	private Layer nullLayer = new Layer();
	private Prototyp prototyp;
	boolean bonusOn = true;
	
	int ORIGIN_X = -55;
	int ORIGIN_Y = 170;
	int interspaceY = 60;
	int interspaceX = 100;
	
	public BonusDesc(Prototyp prototyp)
	{
		this.prototyp = prototyp;
		
		KeyListener space = new KeyListener()
		{
				public void onKeyUp()
				{
					bonusOn = false;
					Prototyp.timer.resume();
				}
		};
		EventManager.instance().addListener(Keyboard.KEY_SPACE,space);		
		
		int pointY = ORIGIN_Y;
		int pointX = ORIGIN_X;
		
		Vector2f immobile = new Vector2f(0,0);
		
		
		
		PlayerShip lightning = new PlayerShip();
		LightningOrb lorb = new LightningOrb(lightning);
		
		PlayerShip rapid = new PlayerShip();
		RapidFireOrb rforb = new RapidFireOrb(rapid);
		
		PlayerShip magnetic = new PlayerShip();
		MagneticOrb morb = new MagneticOrb(magnetic);
		
		PlayerShip crystal = new PlayerShip();
		CrystalOrb corb = new CrystalOrb(crystal);
		
		PlayerShip booster = new PlayerShip();
		
		BonusLightningOrb lBonus = new BonusLightningOrb();
        BonusRapidShootOrb rBonus = new BonusRapidShootOrb();
        BonusMagneticOrb mBonus = new BonusMagneticOrb();
        BonusCrystalOrb cBonus = new BonusCrystalOrb();
        BonusBooster bBonus = new BonusBooster();
        
        Text commandLabel = new Text("One more thing...");
        Text lightningLabel = new Text("Lightning Orb :");
        Text rapidLabel =     new Text("RapidFire Orb :");
        Text magneticLabel =  new Text("Magnetic  Orb :");
        Text crystalLabel =   new Text("Crystal   Orb :");
        Text boosterLabel =   new Text("Booster   Orb :");
        
        
		commandLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
		pointX = ORIGIN_X - interspaceX*2;
        pointY = ORIGIN_Y;
        
        pointY-=interspaceY;
        lightningLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        rapidLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        magneticLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        crystalLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        boosterLabel.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
		pointX = ORIGIN_X + 50 ;
        pointY = ORIGIN_Y;
		
        pointY-=interspaceY;
        lBonus.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        rBonus.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        mBonus.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        cBonus.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointY-=interspaceY;
        bBonus.spawn(new Vector2f(pointX,pointY), immobile, layer);
        
        pointX = ORIGIN_X + interspaceX+ 50;
        pointY = ORIGIN_Y;
        
        pointY-=interspaceY;
        lightning.spawn(new Vector2f(pointX,pointY), immobile, nullLayer);
        lorb     .spawn(new Vector2f(pointX-400,pointY), immobile, layer);
        
        pointY-=interspaceY;
        rapid    .spawn(new Vector2f(pointX,pointY), immobile, nullLayer);
        rforb    .spawn(new Vector2f(pointX-400,pointY), immobile, layer);
        
        pointY-=interspaceY;
        magnetic .spawn(new Vector2f(pointX,pointY), immobile, nullLayer);
        morb     .spawn(new Vector2f(pointX-400,pointY), immobile, layer);
        
        pointY-=interspaceY;
        crystal  .spawn(new Vector2f(pointX,pointY), immobile, nullLayer);
        corb     .spawn(new Vector2f(pointX-400,pointY), immobile, layer);
		
        pointY-=interspaceY;
        booster  .spawn(new Vector2f(pointX+60,pointY), immobile, nullLayer);
        booster.addBooster(layer);
		booster.addBooster(layer);

		
		

	}
	
	public void play()
	{
		//prototyp.timer.pause();
    	
		//NastyHack, I'm sorry
		
		// No more madness...
		
    	while (bonusOn)
    	{
    		Prototyp.heartBeat();
            layer.update();
    		
    		prototyp.render();
    		layer.render();
    		
    		Display.update();
    		
    		if (prototyp.exitRequested())
    		{
    			bonusOn = false;
    			prototyp.gameOff = true;
    		}
    		EventManager.instance().checkEvents();	
    	}
    	EventManager.instance().clear();
    	
	}
}
