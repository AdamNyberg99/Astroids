package astroidsPaket.klasser;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Enemy class
public class Enemy implements gameObjects {

	private double x;
	private double y;
	private double velX = 2;
	private BufferedImage enemy;
	private double direction = -90;
	private int tick = 0; // counter for when the enemy should shot
	private GameOperator game;
	private astroidsPaket.klasser.Rectangle hitBox;
	private boolean enemyOutOfScope = false;

	// Constructor sets position x, y and loads the image
	public Enemy(int x, int y, GameOperator game) throws IOException {
		this.game = game;
		this.x = x;
		this.y = y;
		BufferedImageLoader loader = new BufferedImageLoader();
		this.enemy = loader.loadImage("src/astroidsPaket/bilder/UFO.png");

	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		move();
		hitBox = new astroidsPaket.klasser.Rectangle((int)x,(int) y,(int) (enemy.getWidth()*0.4), (int) (enemy.getHeight()*0.4));
	}

	@Override
	public void render(Graphics g) {
		// TODO Auto-generated method stub
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.scale(0.4, 0.4);
		at.rotate(Math.toRadians(direction), enemy.getWidth() / 2, enemy.getHeight() / 2);

		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(enemy, at, null);
	}

	@Override
	// Moves the enemy. when it comes to a certain point
	// the enemy stops and rotates/fires
	/**
	 * Move metod för enemy som åker från vänster till höger med hastigheten velX.
	 * och som avfyrar ett skott varannan sekund.
	 */
	public void move() {
		x-= velX;
		if (x < 1200) {
			tick++;
			if (tick == 120) {
				enemyFire(GameOperator.getObjectController());
				tick = 0;
			}
			
		}else if(this.x < 0) {
			enemyOutOfScope = true;
			tick = 0;
		}

	}

	/**
	 * Lägger till skott i enemyShot-listan och avfyrar.(Precis som player)
	 * @param obj
	 * Tar "Objects" som parameter för att kunna lägg till i listan.
	 */
	public void fire(Objects obj) {
		obj.addEnemyShot((x + enemy.getWidth() * 0.4 / 2), (y + enemy.getHeight() * 0.4 / 2), direction);
	}

	@Override
	public astroidsPaket.klasser.Rectangle getBounds() {
		// TODO Auto-generated method stub
		return hitBox;
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

	@Override
	public void setX(double x) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setY(double y) {
		// TODO Auto-generated method stub

	}
	
	public boolean getEnemyOutOfScope() {
		return enemyOutOfScope;
	}
	public void setEnemyOutOfScope(boolean b) {
		enemyOutOfScope = b;
	}

	/**
	 * Metoden kalkulerar vinkeln mellan player och enemy och låter skjuta
	 * i den riktningen
	 * @param obj
	 * Tar "Objects" som parameter då metoden "fire" behöver det som parameter
	 */
	public void enemyFire(Objects obj) {

		double playerX = this.game.getPlayer().getX();
		double playerY = this.game.getPlayer().getY();
		double deltaX = (this.getX() - playerX);
		double deltaY = (this.getY()) - playerY;
		double diagonal = Math.sqrt((Math.pow(deltaX, 2) + Math.pow(deltaY, 2)));

		if (playerY < y) {

			this.direction = -90 + Math.toDegrees((Math.acos(deltaX / diagonal)));
			this.fire(obj);
		} else {
			this.direction = -90 - Math.toDegrees((Math.acos(deltaX / diagonal)));
			this.fire(obj);
		}

	}

}
