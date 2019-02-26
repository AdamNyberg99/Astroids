package astroidsPaket.klasser;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static final int width = 600;
	public static final int height = (width / 14) * 9;
	public static final int scale = 2;
	public final String Title = "Astroids";

	private boolean running = false;
	private Thread thread;

	public static STATE State = STATE.MENUE;
	private GameMenu menu;
	private Gameover gameover;
	public Screentext screenText;
	public Menyhighscore menyHighScore;
	public GameOperator gameOperator;

	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

	/**
	 * Initialiserar all objekt som krävs för att gameklassen ska fungera.
	 */
	public void initialisera() {

		this.addKeyListener(new KeyBoardInput(this));
		this.addMouseListener(new MouseInput(this));
		try {
			menyHighScore = new Menyhighscore();
			screenText = new Screentext(menyHighScore);
			
			menu = new GameMenu();
			gameOperator = new GameOperator(this);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Startar game loopen, startar den nya tråden som exekverar metoden Run()
	synchronized void start() {
		if (running) {
			return;
		}

		running = true;
		thread = new Thread(this);
		thread.start();
	}

	// Avslutar game loopen
	private synchronized void stop() {
		if (!running) {
			return;
		}

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}

	public void run() {
		initialisera();
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();

		// Game loop
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				try {
					tick();
					
				} catch (IOException e) {

					e.printStackTrace();
				}
				updates++;
				delta--;
			}
			try {
				render();
			} catch (IOException e) {
				e.printStackTrace();
			}
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + " Ticks, FPS: " + frames);
				updates = 0;
				frames = 0;
			}
		}
		stop();
	}

	// Uppdaterar alla object.
	private void tick() throws IOException {

		if (State == STATE.GAME) {
			gameOperator.tick();
		}

	}

	// Hanterar grafiken, mÃ¥lar all objekt.
	private void render() throws IOException {

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3); // Vi kommer ha tre buffers
			return;
		}

		Graphics g = bs.getDrawGraphics();

		//////////////////////////////////////////////////////////////////////////
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		
		if (State == STATE.GAME) {
			gameOperator.render(g);
		} else if (State == STATE.MENUE) {
			menu.render(g);
		} else if (State == STATE.GAMEOVER) {
			gameOperator.gameover.render(g);
		} else if (State == STATE.HIGHSCORE) {
			menyHighScore.render(g);

		}

		//////////////////////////////////////////////////////////////////////////
		g.dispose();
		bs.show();

	}

	/**
	 * Hanterar vad som händer när en tangent trycks ned, gör olika skaer beroende på i vilket STATE man är i.
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if(State == STATE.GAME) {
			gameOperator.keyPressed(e);
		}
		
		//Input for name when game is over
		if (State == STATE.GAMEOVER) {
			String playerName;
			LinkedList<Character> temp = new LinkedList<Character>();
			playerName = gameOperator.gameover.getName();
			
			if (!(e.getKeyCode() == KeyEvent.VK_ENTER)) {
				
					temp.add(e.getKeyChar());
					playerName += e.getKeyChar();
					gameOperator.gameover.setName(playerName);
					temp.clear();
			}else {
				menyHighScore.saveHighScore(playerName, screenText.getScore());
				menyHighScore.setIsSorted(false);
				State = STATE.MENUE;

			}
		}
	}

	/////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * Hanterar vad som händer när en tangent släpps, har bara funktioner när man är i STATE = GAME.
	 */
	public void keyReleased(KeyEvent e) {
		if(State == STATE.GAME) {
			gameOperator.keyReleased(e);
		}
	}


	public static void main(String[] args) {
		Game game = new Game();

		game.setPreferredSize(new Dimension(width * scale, height * scale));
		game.setMaximumSize(new Dimension(width * scale, height * scale));
		game.setMinimumSize(new Dimension(width * scale, height * scale));

		JFrame frame = new JFrame(game.Title);
		frame.add(game);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		game.start();
	}

	

}