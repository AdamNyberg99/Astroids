package astroidsPaket.klasser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * En klass som "lyssnar" på tangenterna och sedan kallar på metoder i GAme-klassen som säger vad som ska hända
 * om en viss tangent trycks
 *
 */
public class KeyBoardInput extends KeyAdapter{
	
	Game game;
	
	public KeyBoardInput(Game game) {
		this.game = game;
	}
	
	/**
	 * Kallar på metoden keyPressed i game
	 */
	public void keyPressed(KeyEvent e) {
		//System.out.println("hey");
		game.keyPressed(e);
		
	}

	/**
	 * Kallar på metoden keyReleased i game
	 */	
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}


	

}
