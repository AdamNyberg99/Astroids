package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Klassen hanterar den text som visas på skärmen under spelets gång.
 * Nuvarande poäng, högsta poäng och antal liv.
 * @author mattiaserlingson
 *
 */

public class Screentext {
	
	private int score = 0;
	private int highscore;
	private Menyhighscore mscore;
	
	
	private BufferedImage lifePic;
	private List<BufferedImage> lifeList = new ArrayList<BufferedImage>();

	/**
	 * Konstruktör
	 * @param m
	 * Menyhighscore som deklareras i game.
	 * Ger oss möjlighet att hämta highscore.
	 * @throws IOException
	 */

	public Screentext(Menyhighscore m) throws IOException {
		this.mscore = m;
		this.highscore = m.getHighscore();
		BufferedImageLoader loader = new BufferedImageLoader();
		this.lifePic = loader.loadImage("src/astroidsPaket/bilder/Ship.png");
		lifeList.add(lifePic);
		lifeList.add(lifePic);
		lifeList.add(lifePic);
		
	}
	
	/**
	 * This method prints grahics to the screen. I shows current score, 
	 * highscore, and how many lives you have left.
	 * @param g
	 */
	
	public void render(Graphics g) {
		
		//Visar nuvarande score pÃ¥ skÃ¤rmen
		Font font = new Font("arial", Font.BOLD, 30);
		g.setFont(font); // applies the created font to our Graphics2D
		g.setColor(Color.RED); // Sets color to white
		String s = Integer.toString(this.getScore()); // parses the integer score to a String
		g.drawString("Score: " + s, 50, 50); // Drawes string on screen
		
		//visar highscore pÃ¥ skÃ¤rmen
		Font font1 = new Font("arial", Font.BOLD, 30);
		g.setFont(font1); // applies the created font to our Graphics2D
		g.setColor(Color.YELLOW); // Sets color to yellow
		g.drawString("Highscore: " + highscore, 500, 50); // Drawes string on screen
		
		Graphics2D g2d = (Graphics2D) g;
		//visar liv i form av skepp pÃ¥ skÃ¤rmen
		for (int i = 0; i < lifeList.size(); i++) {
			if (i == 0) {
				AffineTransform one = AffineTransform.getTranslateInstance((Game.width * 2) - 200, 30);
				one.scale(0.07, 0.07);
				g2d.drawImage(lifePic, one, null);
			} else if (i == 1) {
				AffineTransform two = AffineTransform.getTranslateInstance((Game.width * 2) - 150, 30);
				two.scale(0.07, 0.07);
				g2d.drawImage(lifePic, two, null);
			} else if (i == 2) {
				AffineTransform three = AffineTransform.getTranslateInstance((Game.width * 2 - 100), 30);
				three.scale(0.07, 0.07);
				g2d.drawImage(lifePic, three, null);
			}
		}

		
	}
	
	
	//returner nuvarande poÃ¤ngen
	public int getScore() {
		return this.score;
	}

	// sÃ¤tter nytt vÃ¤rde pÃ¥ nuvarande poÃ¤ng
	public void setScore(int i) {
		this.score += i;
	}
	
	public void resetScore() {
		score = 0;
	}
	//returnerar
	public List getLifeList() {
		return this.lifeList;
	}
	
	public void resetLifeList() {
		lifeList.clear();
		lifeList.add(lifePic);
		lifeList.add(lifePic);
		lifeList.add(lifePic);
	}
	
}
