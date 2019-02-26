package astroidsPaket.klasser;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;

//Interface with common methods for all game objects
public interface gameObjects {

	public void tick();
	public void render(Graphics g);
	public void move();
	public astroidsPaket.klasser.Rectangle getBounds();
	
	public double getX();
	public double getY();
	public void setX(double x);
	public void setY(double y);

}

