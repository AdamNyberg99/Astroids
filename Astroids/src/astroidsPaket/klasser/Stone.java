package astroidsPaket.klasser;

import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Stoneklassen motsvarar asteroid-objekten i spelet och implementerar metoderna från Gameobjects
 * @author mattiaserlingson
 */

public class Stone implements gameObjects {

	private int x; // x-position
	private int y; // y-position
	private double velX = 0; // velocity in direction x
	private double velY = 0; // velocity in direction y
	public int width; // width of the object
	private int height; // height of the object
	private double direction = 0 + ((int) (Math.random() * ((360 - 0)) + 1));// direction of the object initialized to
	private int level;
	private double levelSpeed;
	private BufferedImage stone; // variable for stone
	private astroidsPaket.klasser.Rectangle hitBox;


	/**
	 * Konstruktör för asteroiderna där vi sätter koordinater och storlek på asteroiden
	 * Vi skapar även en hitbox till varje objekt för att detektera kollison.
	 * Vi sätter även hastighet på stenen beroende på vilken level den har
	 * @param x x-koordinat
	 * @param y
	 * y-koordinat
	 * @param w
	 * width
	 * @param h
	 * hight
	 * @param level
	 * level på sten
	 * @throws IOException
	 */

	public Stone(int x, int y, int w, int h, int level) throws IOException {
		this.level = level;
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;

		hitBox = new astroidsPaket.klasser.Rectangle(x, y, w, h);

		BufferedImageLoader loader = new BufferedImageLoader();
		this.stone = loader
				.loadImage("src/astroidsPaket/bilder/Asteroid.PNG");
		/*
		 * sets different speed depending on the level of wich the stone is
		 * */
		if(this.level == 3) {
			this.levelSpeed = 2;
		}else if(this.level == 2 ) {
			this.levelSpeed = 3;
		}else if(this.level == 1) {
			this.levelSpeed = 4;
		}
	}

	@Override	
	
	/**
		 * kollar om stenarna lämnat skärmen och returnerar de på motsatt sida med random position.
		 * Uppdaterar hitboxen för varje tick.
		 */

	public void tick() {
		setRandomStoneReturn();
		hitBox = new astroidsPaket.klasser.Rectangle(x, y, width, height);


	}

	// returns a random number between min and max
	public double randomGenerator(double minInterval, double maxInterval) {

		return minInterval + (int) (Math.random() * (((maxInterval) - (minInterval)) + 1));
	}

	// Takes stone back to side of canvas when leaving screen.
	public void setRandomStoneReturn() {

		if (x < -this.getWidth()+10) { // left
			setY(randomGenerator(150, Game.height - 180));
			setX((Game.width * 2) - 20);
			setDirection(randomGenerator(-30, 30));
		} else if (x > ((Game.width * 2)+this.getWidth())) { // right
			setY(randomGenerator(150, Game.height - 180));
			setX(-this.getWidth());
			setDirection(randomGenerator(150, 210));
		} else if (y > Game.height*2 + this.getHeight()) { // bottom
			setY(0 - this.getHeight()+20);
			setX(randomGenerator(250, ((Game.width * 2) - 300)));
			setDirection(randomGenerator(-120, -60));
		} else if (y < 0 - this.getHeight()) { // top
			setY(Game.height * 2);
			setX(randomGenerator(300, ((Game.width * 2) - 300)));
			setDirection(randomGenerator(60, 120));

		} else

			this.move();
	}

	/* draws the image... */
	public void render(Graphics g) {

		Graphics2D graph = (Graphics2D) g;
		graph.drawImage(stone, x, y, width, height, null);

	}

	@Override
	/* decreased stones x-pos with -velocity */
	public void move() {
		this.velX = levelSpeed * Math.cos(Math.toRadians(direction));
		this.velY = levelSpeed * Math.sin(Math.toRadians(direction));
		this.x -= velX;
		this.y -= velY;

	}

	@Override
	public double getX() {
		// TODO Auto-generated method stub
		return this.x;
	}

	@Override
	public double getY() {
		// TODO Auto-generated method stub
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
		// TODO Auto-generated method stub

	}

	public void setY(int y) {
		this.y = y;
		// TODO Auto-generated method stub
	}

	public void setVelX(int x) {
		this.velX = (int) (x * Math.cos(Math.toRadians(direction)));
	}

	public void setVelY(int y) {
		this.velY = y;
	}

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub

	}

	public void setDirection(double degrees) {
		this.direction = degrees;

	}

	public double getDirection() {
		return this.direction;
	}

	public astroidsPaket.klasser.Rectangle getBounds() {
		return hitBox;
	}

	public int getLevel() {
		return this.level;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
