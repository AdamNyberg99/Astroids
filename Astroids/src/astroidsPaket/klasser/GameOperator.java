package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class GameOperator {
	private Game game;
	public Gameover gameover;

	public boolean itterating = false;
	public boolean shooting = false;
	public boolean enemyDead = false;
	private int deathTimer = 1;
	private int gameTime = 0;
	private int seconds = 0;
	private int spawnTime = 0;
	private boolean playerHit = false;

	private Enemy enemy;
	private Player p;
	private static Objects objectController;

	private Screentext screenText;
	private int level = 0;

	private Menyhighscore menyHighScore;

	public GameOperator(Game game) {
		this.game = game;
		this.screenText = game.screenText;
	}
	
	/**
	 * uppdaterar alla objekt i som finns i spelet och r�knar tiden sedan spelomg�ngen startade.
	 * @throws IOException
	 */
	public void tick() throws IOException {
		gameTime++;
		if(gameTime == 60) {
			seconds++;
			gameTime = 0;
		}
		if(playerHit) {
			resetPlayer();
		}
		p.tick();
		objectController.tick();
		Collision();	
		updateLevel();
		playerStatus();
	}
	
	/**
	 * M�lar alla gameObject  
	 */
	public void render(Graphics g) {
		screenText.render(g);
		objectController.render(g);
		p.render(g);
		
		if(!objectController.enemies.isEmpty()) { //as long as there is an enemy render
			objectController.enemies.getFirst().render(g);	//geFirst since there will at most be one enemy in the list
		}
	}

	/**
	 * Initialiserar alla object som kr�vs f�r att spela.
	 */
	public void gameInit() {
		try {
			level = 0;
			p = new Player(600, 300);
			objectController = new Objects(game, this);
			gameover = new Gameover(game);
			screenText.resetLifeList();
			screenText.resetScore();
			playerHit = false;
			seconds = 0;
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Sk�ter vad som ska h�nda n�r man �r in-game och trycker p� tangenter
	 * 
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (!playerHit) {
			if (key == KeyEvent.VK_RIGHT) {
				p.setRotationSpeed((5));

			} else if (key == KeyEvent.VK_LEFT) {
				p.setRotationSpeed(-5);

			} else if (key == KeyEvent.VK_UP) {
				p.thrust(true);
				p.accelerateX(0.25);
				p.accelerateY(0.25);
				p.saveBurstDirection();

			} else if (key == KeyEvent.VK_SPACE && !shooting) {
				shooting = true;
				p.fire(objectController);
			}
		} 
	}
	
	/**
	 * Sk�ter vad som ska h�nda n�r man �r in-game och sl�pper p� tangenter
	 * 
	 */
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			p.thrust(false);
			p.accelerateX(0);
			p.accelerateY(0);
			
		} else if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) {
			p.setRotationSpeed(0);
		} else if (key == KeyEvent.VK_SPACE) {
			shooting = false;
		}
	}

	/**
	 * Collision-metoden detekterar kollision mellan alla objekten i spelet
	 * och best�mmer vad som ska h�nde vid respektive kollision.
	 * Kollision mellan asteroider och player, mellan p-shots och asteroider och enemy
	 * mellan, e-shot och player.
	 * 
	 * @throws IOException
	 */

	public void Collision() throws IOException {

		for (int j = 0; j < objectController.astroids.size(); j++) {
			astroidsPaket.klasser.Rectangle r = objectController.astroids.get(j).getBounds();

			// if player collide with stone
			if (p.getBounds().intersect(r) && !playerHit) {
				playerHit = true;
				spawnTime = seconds + deathTimer;

				int level = objectController.astroids.get(j).getLevel();
				// objectController.astroids.get(j).setColor(Color.GREEN);
				removeStone(objectController.astroids.get(j), level);

				p.setColor(Color.GREEN);
				// Removes lifes if hit
				if (!screenText.getLifeList().isEmpty()) {
					screenText.getLifeList().remove(0);
				}
			}

			// if playershots hits enemy
			for (int i = 0; i < objectController.shots.size(); i++) {
				astroidsPaket.klasser.Rectangle shot = objectController.shots.get(i).getBounds();

				if (!objectController.enemies.isEmpty()) {
					if (shot.intersect(objectController.enemies.getFirst().getBounds()) && !enemyDead) {
						objectController.enemies.remove(0);
					}
				}

				// if playershots hits asteroid
				if (r.intersect(shot)) {
					objectController.shots.remove(objectController.shots.get(i));
					int stoneLevel = objectController.astroids.get(j).getLevel();
					this.removeStone(objectController.astroids.get(j), stoneLevel);
				}
			}

		}
		// If player collides with enemy
		if (!objectController.enemies.isEmpty()) {
			if (p.getBounds().intersect(objectController.enemies.get(0).getBounds()) && !enemyDead && !playerHit) {
				playerHit = true;
				spawnTime = seconds + deathTimer;
				enemyDead = true;
			}
		}

		


		// if enemyshots hits player
		for (int i = 0; i < objectController.enemyShots.size(); i++) {
			astroidsPaket.klasser.Rectangle enemyShot = objectController.enemyShots.get(i).getBounds();
			if (p.getBounds().intersect(enemyShot) && !playerHit) {
				objectController.enemyShots.remove(objectController.enemyShots.get(i));
				playerHit = true;
				spawnTime = seconds + deathTimer;
				if (!screenText.getLifeList().isEmpty()) {
					screenText.getLifeList().remove(0);
				}
			}
		}
	}

	/**
	 * �ter-placerar spelaren till dess utg�ngsl�ge igen efter att det har crashat, om det inte har g�tt tillr�ckligt l�ng tid
	 * s�ger den �t player att va i explode l�ge.
	 */
	public void resetPlayer() {
		if (playerHit && seconds == spawnTime) {
			p.setX(Game.width - 50);
			p.setY(Game.height - 50);
			playerHit = false;
			p.respawn();
		} else if (playerHit && seconds != spawnTime) {
			p.explode();
		}
	}

	///////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * Metoden tar bort och l�gger till nya stenar om man lyckats tr�ffa en.
	 * Stenarna halverar bredd och h�jd f�r varje level och ger stenen en ny level.
	 *  
	 * @param s
	 * Sten som skal tas bort
	 * 
	 * @param stoneLevel
	 * Level p� stenen som ska tas bort. Det avg�r vilken po�ng man f�r f�r
	 * att ha f�rst�rt den.
	 * 
	 * @throws IOException
	 */

	public void removeStone(Stone s, int stoneLevel) throws IOException {
		int levelControl = stoneLevel;

		if (levelControl == 3) {
			screenText.setScore(50);
			objectController.astroids
					.add(new Stone((int) s.getX(), (int) s.getY(), s.getWidth() / 2, s.getHeight() / 2, 2));
			objectController.astroids
					.add(new Stone((int) s.getX(), (int) s.getY(), s.getWidth() / 2, s.getHeight() / 2, 2));
			objectController.astroids.remove(s);

		} else if (levelControl == 2) {
			screenText.setScore(100);
			objectController.astroids
					.add(new Stone((int) s.getX(), (int) s.getY(), s.getWidth() / 2, s.getHeight() / 2, 1));
			objectController.astroids
					.add(new Stone((int) s.getX(), (int) s.getY(), s.getWidth() / 2, s.getHeight() / 2, 1));
			objectController.astroids.remove(s);

		} else if (levelControl == 1) {
			screenText.setScore(200);
			objectController.getAstroids().remove(s);
		}

	}

	////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Metoden kollar om listan med asteroider �r tom, isf updaterar vi level med +1.
	 * Sedan l�gger vi till antalet stenar utifr�n level+2 p� en random position p� sk�rmen, 
	 * men med ett visst avst�nd fr�n player.
	 * Stenarnas x och y koordinatar genereras fr�n en funktion som returnerar ett 
	 * random-v�rde inom ett specifikt intervall.
	 * 
	 * @throws IOException
	 */
	public void updateLevel() throws IOException {
		if (objectController.astroids.isEmpty()) {
			level += 1;
			// LA TILL S� ATT ENEMY SPAWNAR F�RST LEVEL �R 2
			if (level > 1) {
				objectController.addEnemy();
			}
			// �NDRADE TILL 2
			boolean stop = false;	//boolean f�r att stoppa while-loopen d� r�tt antal stenar lagts till. 
			int counter = 0;	//R�knare f�r att veta d� r�tt antal stenas skapats
			// �NDRAT S� STENAR INTE SPAWNAR P� PLAYER
			while (!stop) {
				double xtemp = randomGenerator(100, (Game.width * 2)-100);
				double ytemp = randomGenerator(50, (Game.height * 2)-50);
					if (xtemp < p.getX() - 350 || xtemp > (p.getX() + 400) || ytemp > (p.getY() + 250) || ytemp < (p.getY()-250)) {
						objectController.addStone((int) xtemp, (int) ytemp, 160, 160, 3);
						counter++;
						if (counter == level + 2) {
							stop = true;
					}else {
						
					}
				}
			}
		}
	}

	public double randomGenerator(double minInterval, double maxInterval) {

		return minInterval + (int) (Math.random() * (((maxInterval) - (minInterval)) + 1));
	}
	
	/**
	 * T�mmer alla object n�r en spel omg�ng �r avslutad.
	 */
	public void gameOver() {
		objectController.astroids.clear();
		objectController.enemies.clear();
		objectController.enemyShots.clear();
		objectController.shots.clear();
		p = null;
	}
	
	/**
	 * Kollar om spelaren fortfarande har n�gra liv kvar.
	 */
	public void playerStatus() {
		if (screenText.getLifeList().isEmpty()) {
			Game.State = STATE.GAMEOVER;
			gameOver();
		}
	}

	public int getScore() {
		return screenText.getScore();
	}

	//////////////////////////////////////////////////////////////////////////////////////

	public static Objects getObjectController() {
		return objectController;
	}

	public Player getPlayer() {
		return this.p;
	}

	public void playPressed() {
		gameInit();
	}

}
