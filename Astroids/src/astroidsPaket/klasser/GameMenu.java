package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.TextLayout;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

public class GameMenu {
	
	private BufferedImage backgroundMenu;
	
	public static Rectangle play = new Rectangle(Game.width-100, 200, 300, 70);
	/**
	public Rectangle highhscore = new Rectangle(Game.width-100, 300, 300, 70);
	public Rectangle help = new Rectangle(Game.width-100, 400, 300, 70);
	public Rectangle quit = new Rectangle(Game.width-100, 500, 300, 70);
 	**/
	public static BufferedImage playIMG;
	public BufferedImage helpIMG;
	public BufferedImage highScoreIMG;
	public BufferedImage quitIMG;
	public BufferedImage settingsIMG;
	public GameMenu() throws IOException {
		BufferedImageLoader loader = new BufferedImageLoader();
		this.backgroundMenu = loader.loadImage("src/astroidsPaket/bilder/Vdtct7v.jpg");
		
		this.playIMG = loader.loadImage("src/astroidsPaket/bilder/play.PNG");
		this.helpIMG = loader.loadImage("src/astroidsPaket/bilder/help.PNG");
		this.highScoreIMG = loader.loadImage("src/astroidsPaket/bilder/HighScore.PNG");
		this.quitIMG = loader.loadImage("src/astroidsPaket/bilder/Quit.PNG");
		this.settingsIMG = loader.loadImage("src/astroidsPaket/bilder/Settings.PNG");



	}
	
	public void render(Graphics g) {
		//BufferedImageLoader loader = new BufferedImageLoader();
		//Graphics2D g2d = (Graphics2D) g; // För att vi ska kunna skriva ut rektanglar (knappar)

		g.drawImage(backgroundMenu, 0, 0, Game.width*Game.scale+100, Game.height*Game.scale+100, null);
		Font rubrik = new Font("arial", Font.BOLD, 70);
		g.setFont(rubrik);
		g.setColor(Color.WHITE);
		
		g.drawString("Astroids", Game.width-100, 100);
		
		//Font knappar = new Font("arial", Font.BOLD, 50);
		//g.setFont(knappar);
		
		g.drawImage(playIMG, play.x, play.y, (int) (playIMG.getWidth()/3.3), (int) (playIMG.getHeight()/3.4), null);
		g.drawImage(highScoreIMG, play.x, play.y+90, (int) (playIMG.getWidth()/3.3), (int) (playIMG.getHeight()/3.4), null);
		g.drawImage(helpIMG, play.x, play.y+180, (int) (playIMG.getWidth()/3.3), (int) (playIMG.getHeight()/3.4), null);
		g.drawImage(quitIMG, play.x, play.y+270, (int) (playIMG.getWidth()/3.3), (int) (playIMG.getHeight()/3.4), null);
		g.drawImage(settingsIMG, play.x, play.y+360, (int) (playIMG.getWidth()/3.3), (int) (playIMG.getHeight()/3.4), null);
		
	}
	
	public static int getX() {
		return play.x;
	}
	public static int getButtonWidth() {
		return (int) (playIMG.getWidth()/3.3);
	}
	public static int getY() {
		return play.y;
	}
	public static int getButtonHeight() {
		return (int) (playIMG.getHeight()/3.4);
	}

}
