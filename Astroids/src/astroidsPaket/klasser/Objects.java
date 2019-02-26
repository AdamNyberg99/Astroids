package astroidsPaket.klasser;

import java.util.List;
import java.awt.Color;
import java.awt.Graphics;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.LinkedList;

public class Objects {

	public Game game;
	private GameOperator gameOperator;
	public LinkedList<Shot> shots = new LinkedList<Shot>();
	public LinkedList<Stone> astroids = new LinkedList<Stone>();
	public LinkedList<Shot> enemyShots = new LinkedList<Shot>();
	public LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	
	
	public Objects(Game g, GameOperator g2) {
		// TODO Auto-generated constructor stub
		this.game = g;
		this.gameOperator=g2;
	}
	
	/**
	 * Updaterar all objekt i listorna
	 */
	public void tick(){
		remove();
		for(int i= 0; i< shots.size(); i++) {
			shots.get(i).tick();
		}
		
		for(int i= 0; i< astroids.size(); i++) {
			astroids.get(i).tick();
		}
		for(int i= 0; i< enemyShots.size(); i++) {
			enemyShots.get(i).tick();
		}
		for(int i= 0; i< enemies.size(); i++) {
			enemies.get(i).tick();
		}
		
		
	}
	
	/*
	 * Målar objekt i listorna
	 */
	public void render(Graphics g) {
		for(int i= 0; i< shots.size(); i++) {
			shots.get(i).render(g);
		}
		
		for(int i= 0; i< astroids.size(); i++) {
			astroids.get(i).render(g);
		}
		for(int i= 0; i< enemyShots.size(); i++) {
			enemyShots.get(i).render(g);
		}
		if(!enemies.isEmpty()) {
			for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).render(g);
			}
		}	
		
	}
	
	/**
	 * Tar bort alla skott som befinner sig utanför skärmen/ spelplanen
	 */
	public void remove() {
		for (Shot s: shots) {
			if(s.getX() <0 - 50) {			
				shots.remove(s);
			} else if(s.getX() > Game.width*Game.scale - 50) {
				shots.remove(s);
			} else if(s.getY() <0) {
				shots.remove(s);
			} else if(s.getY()  > Game.height * Game.scale+50) {
				shots.remove(s);
			}
			return;
		}
		//Same as above but for enemy Shots
		for (Shot s: enemyShots) {
			if(s.getX() <0 - 50) {			
				enemyShots.remove(s);
			} else if(s.getX() > Game.width*Game.scale - 50) {
				enemyShots.remove(s);
			} else if(s.getY() <0) {
				enemyShots.remove(s);
			} else if(s.getY()  > Game.height * Game.scale+50) {
				enemyShots.remove(s);
			}
			return;
		}
	}
	
	/**
	 * Lägger till ett skott i listan för skott, och placerar det i punkt x,y och ger den en färdriktning direction
	 */
	public void addShot(double x, double y, double direction) {
		shots.add(new Shot(x, y, direction, Color.CYAN));
	}
	
	/**
	 * Lägger till en astroid, spawnar i punkt x,y och är width*hight stor
	 * @throws IOException
	 */
	public void addStone(int x, int y, int width, int height, int level) throws IOException{
		astroids.add(new Stone(x, y, width, height, level));
	}
	//Same as addShot but for enemy-list
	public void addEnemyShot(double x, double y, double direction) {
		enemyShots.add(new Shot(x, y, direction, Color.RED));
	}
	
	/**
	 * Lägger till en fiende
	 */
	public void addEnemy() {
		try {
			int y = (int) game.gameOperator.randomGenerator(100, Game.height*Game.scale - 100);
			enemies.add(new Enemy(1300, y, gameOperator));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returnar listor med objekten
	 * 
	 */
	
	public LinkedList<Stone> getAstroids() {
		return astroids;
	}
	public LinkedList<Enemy> getEnemies() {
		return enemies;
	}
	

}