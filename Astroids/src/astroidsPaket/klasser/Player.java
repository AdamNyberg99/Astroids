package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Player implements gameObjects{

	private double x;
	private double y;
	private double velX = 0;
	private double velY = 0;
	private double aX = 0;
	private double aY = 0;
	private double direction = 0;
	private double rotationSpeed = 0;
	private double latestBurstDirection = 0;
	private double scale = 0.13;
	private BufferedImage player;
	private Rectangle playerRec;
	private astroidsPaket.klasser.Rectangle hitBox;
	private astroidsPaket.klasser.Rectangle sten;
	private Color color = Color.WHITE;
	private boolean explode = false;
	private boolean thrust = false;
	private BufferedImage[] explotionArray;
	private BufferedImage exAnimationSheet;
	private BufferedImage[] thrustArray;
	private BufferedImage thruAnimationSheet;
	private BufferedImage explosionPic;
	private double exAnimationPos = 0;
	private double thruAnimationPos = 0;
	
	public Player(double x, double y) throws IOException {
		this.x = x;
		this.y = y;
		
		BufferedImageLoader loader = new BufferedImageLoader();
		this.exAnimationSheet = loader.loadImage("src/astroidsPaket/bilder/explosion.png");
		SpriteSheet arrayLoader = new SpriteSheet(exAnimationSheet);
		this.player = loader.loadImage("src/astroidsPaket/bilder/Ship.png");	
		this.explotionArray = arrayLoader.generateArray(16, 1, 128, 128);
		
		this.thruAnimationSheet = loader.loadImage("src/astroidsPaket/bilder/thrustSpriteSheet.png");
		SpriteSheet arrayLoader2 = new SpriteSheet(thruAnimationSheet);
		this.thrustArray = arrayLoader2.generateArray(8, 1, 813, 1037);

		
		this.hitBox = new astroidsPaket.klasser.Rectangle((int) x, (int) y, (int) (player.getWidth()*scale), (int) (player.getHeight()*scale));
		this.sten = new astroidsPaket.klasser.Rectangle(100, 100,300, 300);

	}

	/**
	 * Uppdaterar player, uppdaterar saker som: x,y -koordinaterna samt positionen i animations arrayer. Kollar även om  och ser till att
	 * Spelaren är inom spelplanen
	 */
	@Override
	public void tick() {
		if(explode) {
			exAnimationPos+=1;
			
			if(exAnimationPos >= 16) {
				exAnimationPos = 0;
			}
		}
		if(thrust) {
			thruAnimationPos+= 0.14;
			
			if(thruAnimationPos >= 8) {
				thruAnimationPos = 0;
			}
		}
		
		if(x < 0) {
			x = 0;
			velX=0;

		} else if(x > Game.width*Game.scale - player.getHeight()*scale) {
			x = (Game.width*Game.scale) - (player.getHeight()*scale);
			velX=0;
		} else if(y <0) {
			y= 0;
			velY=0;
		} else if(y  > Game.height * Game.scale-player.getHeight()*scale) {
			y = Game.height *Game.scale -player.getHeight()*scale;
			velY=0;
		} else {
			move();
		}
		this.hitBox = new astroidsPaket.klasser.Rectangle((int) x, (int) y, (int) (player.getWidth()*scale), (int) (player.getHeight()*scale));
		hitBox.rotate(Math.toRadians(-direction));
		color = Color.WHITE;
	}
	
	/**
	 * Målar spelaren, genom att placera bilden i punkt x,y och rotera målaren med affineTransform
	 */
	@Override
	public void render(Graphics g) {
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.scale(scale, scale);
		at.rotate(Math.toRadians(direction), player.getWidth() / 2, player.getHeight() / 2);

		g.setColor(color);
		Graphics2D g2d = (Graphics2D) g;
		if (explode) {
			g.drawImage(explotionArray[(int)exAnimationPos],(int) x, (int) y , null);
		} else if (thrust) {
			g2d.drawImage(thrustArray[(int)thruAnimationPos], at, null);

			//g.drawImage(thrustArray[animationPos], (int) x, (int) y, null);
		} else {
			g2d.drawImage(player, at, null);

			/*hitBox.draw(g);
			if (hitBox.intersect(sten)) {
				g.setColor(Color.GREEN);
			} else {
				g.setColor(Color.WHITE);
			}
			sten.draw(g);*/ 
		}
		
		
		
	}

	/**
	 * Sköter spelarens rörelse
	 */
	@Override
	public void move() {
		x -= velX;
		y -= velY;
		velX += aX;
		velY += aY;
		velX = velX*0.995;
		velY = velY*0.995;
		direction += rotationSpeed;
	}
	
	@Override
	public double getX() {
		return  x;
	}

	@Override
	public double getY() {
		return  y;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}
	
	/**
	 * Acelererar spelaren i x-led
	 */
	public void accelerateX(double x) {
		this.aX = x * Math.cos(Math.toRadians(direction+90));
	}

	/**
	 * Acelererar spelaren i y-led
	 */
	public void accelerateY(double y) {
		this.aY = y * Math.sin(Math.toRadians(direction+90));
	}
	
	public void saveBurstDirection() {
		this.latestBurstDirection = direction;
	}
	
	public void thrust(boolean isAccelerating) {
		thrust = isAccelerating;
	}
	
	public void setRotationSpeed(double v) {
		this.rotationSpeed= v;
	}
	
	/**
	 * Skjuter genom att skapa ett objekt skott och lägga till det i listan för alla players skott.
	 * @param obj
	 */
	public void fire(Objects obj) {
		obj.addShot((x +player.getWidth()*scale/2),(y+player.getHeight()*scale/2),direction);
	}

	//Returnerar en rektangel som markerar players gränser.
	public astroidsPaket.klasser.Rectangle getBounds() {
		
		return hitBox;
	}
	
	public void setColor(Color c) {
		color = c;
	}
	
	/**
	 * Får spelaren att vara still och utföra explode-animationen när den exploderar/ är träffad
	 */
	public void explode() {
		explode = true;
		thrust(false);
		accelerateX(0);
		accelerateY(0);
		setRotationSpeed(0);
		velX = 0;
		velY = 0;
	}
	
	/**
	 * Avslutar explode animationen
	 */
	public void respawn() {
		explode = false;
	}
	

}