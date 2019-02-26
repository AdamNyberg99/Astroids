package astroidsPaket.klasser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Klass som hanterar Menyvalet Highscore
 * 
 * @author mattiaserlingson
 *
 */

public class Menyhighscore {

	List<Integer> scoreList = new ArrayList<Integer>();
	Map<Integer, String> highscoreMap = new HashMap();

	private String fileName = "src/astroidsPaket/klasser/Highscore";
	private BufferedImage background;
	private int highScore;
	int y = 250;
	Graphics g;
	private boolean isSorted = false;

	public Menyhighscore() throws IOException {
		BufferedImageLoader loader = new BufferedImageLoader();
		this.background = loader.loadImage("src/astroidsPaket/bilder/Vdtct7v.jpg");

	}
	/**
	 * Skriver ut rubrik, ram och text på skärmen på givna positioner
	 * 
	 * @param g
	 * @throws IOException
	 */

	public void render(Graphics g) throws IOException {
		this.g = g;
		g.drawImage(background, 0, 0, Game.width * Game.scale + 100, Game.height * Game.scale + 100, null);

		Font rubrik = new Font("arial", Font.BOLD, 80);
		g.setFont(rubrik);
		g.setColor(Color.WHITE);
		g.drawString("HIGHSCORE", 360, 150);
		g.fillRoundRect(380, 200, 450, 550, 100, 100);
		printTillbaka();
		if(scoreList.size() == 0) {
			loadHighScore();
		}

		sort();
		print();
	}

	/**
	 * This method reads from the high score file line by line and puts score
	 * mapping to the names in a hash map and the scores in a list so we can sort
	 * them later and
	 * 
	 * @throws IOException
	 */
	public void loadHighScore() throws IOException {
		FileReader in = new FileReader(fileName);
		BufferedReader br = new BufferedReader(in);

		String st;
		while ((st = br.readLine()) != null) {
			if (!(st.length() == 0 )) {
				String[] inputs = st.split(":");
				String a = inputs[0];
				String b = inputs[1];
				int e = Integer.parseInt(b);
				highscoreMap.put(e, a);
				scoreList.add(e);
			}
		}

		in.close();

	}

	/**
	 * This method saves the score and name from player in the text file
	 * 
	 * @param name  name of player
	 * @param score score from current game
	 */
	public void saveHighScore(String name, int score) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(fileName, true));
			String temp = name;
			temp += ":";
			temp += Integer.toString(score);
			out.write("\n" + temp);

			out.close();

		} catch (IOException ioe) {
			ioe.printStackTrace();

		}
	}

	/**
	 * 
	 * @param name    player name
	 * @param score   player score
	 * @param counter counter to know where in y-led we should print
	 */
	public void printScore(String name, int score, int counter) {
		Font rubrik = new Font("arial", Font.TYPE1_FONT, 40);
		g.setFont(rubrik);
		g.setColor(Color.BLACK);
		g.drawString(name + ": " + score, 400, 250 + (counter * 50));

	}

	/**
	 * this just sorts the list of scores and reverses it then we print the top 10
	 * to the screen
	 */
	public void print() {
		for (int i = 0; i < 10; i++) {
			if(i < scoreList.size()) {
			System.out.println("H_map: " + highscoreMap.size() + ", ScoreList: " + scoreList.size() +" I = " + i);
			printScore(highscoreMap.get(scoreList.get(i)), scoreList.get(i), i);
			}
		}

	}
/**
 * sorts the list of scores then reverses 
 * it so the highest score is at position 0
 */
	public void sort() {
		while (!isSorted) {
			Collections.sort(scoreList);
			Collections.reverse(scoreList);
			isSorted = true;
			highScore = scoreList.get(0);
		}
	}

	/**
	 * returns the highest integer 
	 * value from scorelist 
	 * @return
	 */
	public int getHighscore() {
		try {
			loadHighScore();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sort();

		return this.scoreList.get(0);
	}

	public void setIsSorted(boolean bol) {
		isSorted = bol;
	}
	/**
	 * Printar tilbakaknappen som tar dig tillbaka till emnyn
	 */
	public void printTillbaka() {
		g.setColor(Color.white);
		g.fillRoundRect(50, 50, 170, 90, 20, 20);
		Font fonte = new Font("italic", Font.BOLD, 40);
		g.setColor(Color.PINK);
		g.setFont(fonte);
		g.drawString("<-Back", 55, 110);
	}

}
