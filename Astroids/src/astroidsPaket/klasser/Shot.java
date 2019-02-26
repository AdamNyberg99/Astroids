package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Shot implements gameObjects{

	private double x;
	private double y;
	private double velX = 0;
	private double velY = 0;
	private double direction = 0;
	private int width=10;
	private int height=10;
	private astroidsPaket.klasser.Rectangle hitBox;
	private Color color;

	public Shot(double x, double y, double direction, Color color) {
		this.x = x;
		this.y = y;
		this.direction = direction;
		hitBox = new astroidsPaket.klasser.Rectangle((int)x,(int) y, width, height);
		this.color = color;
	}

	@Override
	public void tick() {
		move();
		hitBox = new astroidsPaket.klasser.Rectangle((int)x,(int) y, width, height);
	}

	@Override
	public void render(Graphics g) {
		g.setColor(color);
		g.fillOval((int) x,(int) y, 8, 8);
		
	}

	/**
	 * Flyttar spelaren i riktningen direction med hastighet velX, velY i x respektive y - led.
	 */
	@Override
	public void move() {
		velX = 8 * Math.cos(Math.toRadians(direction-90));
		velY = 8 * Math.sin(Math.toRadians(direction-90));
		x += velX;
		y += velY;
		
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public void setX(double x) {
	}

	@Override
	public void setY(double y) {		
	}

	/**
	 * Returnerar en rektangel som markerar skott objektets gränser.
	 */
	@Override
	public astroidsPaket.klasser.Rectangle getBounds() {
		return hitBox;
	}

}