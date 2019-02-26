package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Gamover klassen hanterar grafiken av den skärm som visas då det blivit
 * gameover.
 * 
 * @author mattiaserlingson
 *
 */

public class Gameover {
	
	private Game game;
	private BufferedImage gameOverBG;
	public Rectangle newGame = new Rectangle(Game.width-150, 200, 300, 70);
	String name = "";
	

	
	public Gameover(Game game) throws IOException {
		
		this.game = game;
		/*BufferedImageLoader load = new BufferedImageLoader();
		this.gameOverBG = load.loadImage("src/astroidsPaket/bilder/UFO.png");
		*/
		
	
	}

	public void render(Graphics g) {

		//g.drawImage(gameOverBG, 0, 0, Game.width * Game.scale + 100, Game.height * Game.scale + 100, null);
		Font font = new Font("arial", Font.BOLD, 100);
		g.setFont(font);
		g.setColor(Color.GREEN);
		g.drawString("GAME OVER!", 280, 350);
		g.drawLine(260, 355, 950, 355);
		Font font1 = new Font("arial", Font.CENTER_BASELINE, 60);
		g.setFont(font1);
		g.setColor(Color.GREEN);
		g.drawString("Your score: " + this.game.gameOperator.getScore(), 280, 450);
		g.drawString("Name: ", 280, 550);
		if(!(name.length() == 0)) {
			g.drawString(name, 500, 550);
		}
	}
	public String getName() {
		return this.name;
	}
	public void setName(String s) {
		this.name = s;
	}
		
}
	
