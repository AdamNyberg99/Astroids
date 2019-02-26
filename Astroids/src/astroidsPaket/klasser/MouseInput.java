package astroidsPaket.klasser;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

/**
 * Avlyssnar musen och utför handlingar om vissa fall sker, t.ex om musen är över en knapp och man trycker.
 * (Borde kanske igentligen kalla på metoder i game och andra klasser som har mouseKlicked metoder som i KeyBoard- klassen)
 */
public class MouseInput implements MouseListener{
	private Game game;
	public MouseInput(Game game) {
		this.game=game;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Kollar om musen befinner sig över en knapp när man klickar med musen, om musen är över en knapp ändras game.State
	 * till det state som knappen motsvarar
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		int mx = e.getX();
		int my = e.getY();
		
		if (Game.State == STATE.MENUE) {

			if (mx >= GameMenu.getX() && mx < GameMenu.getX() + GameMenu.getButtonWidth()) {

				// Play-button
				if (my > GameMenu.getY() && my < GameMenu.getY() + GameMenu.getButtonHeight()) {
					game.gameOperator.playPressed();
					Game.State = STATE.GAME;			
				}
				// Quit-button
				if (my > GameMenu.getY() + 90 * 3 && my < GameMenu.getY() + 90 * 3 + GameMenu.getButtonHeight()) {
					System.exit(1);
				}
				if (my > GameMenu.getY() + 90 && my < GameMenu.getY() + 90 + GameMenu.getButtonHeight()) {
					Game.State = STATE.HIGHSCORE;
					try {
						game.menyHighScore.scoreList.clear();
						game.menyHighScore.highscoreMap.clear();
						game.menyHighScore.setIsSorted(false);
						game.menyHighScore.loadHighScore();
						game.menyHighScore.sort();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
					

				}
			}
		}

		if (Game.State == STATE.HIGHSCORE) {
			if (my >= 50 && my <= 140 && mx >= 50 && mx <= 220) {		
				Game.State = STATE.MENUE;
				game.menyHighScore.scoreList.clear();
				game.menyHighScore.highscoreMap.clear();
				game.menyHighScore.setIsSorted(false);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
