package astroidsPaket.klasser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * En klass som "lyssnar" p� tangenterna och sedan kallar p� metoder i GAme-klassen som s�ger vad som ska h�nda
 * om en viss tangent trycks
 *
 */
public class KeyBoardInput extends KeyAdapter{
	
	Game game;
	
	public KeyBoardInput(Game game) {
		this.game = game;
	}
	
	/**
	 * Kallar p� metoden keyPressed i game
	 */
	public void keyPressed(KeyEvent e) {
		//System.out.println("hey");
		game.keyPressed(e);
		
	}

	/**
	 * Kallar p� metoden keyReleased i game
	 */	
	public void keyReleased(KeyEvent e) {
		game.keyReleased(e);
	}


	

}
