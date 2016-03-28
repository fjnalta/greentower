package com.greentower;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.data.Highscore;
import com.greentower.states.MenuState;

public class GreenTowerGame extends Game {

	private LinkedList<Highscore> vectorHighscore;
	private int currentScore;

	// virtual width and height for the game
	public static final int V_WIDTH = 20 * 32;
	public static final int V_HEIGHT = 30 * 32;
	// fix box2d scaling
	public static final float PPM = 10f;

	public static final String TITLE = "Green Tower";

	public static Highscore score;

	// all screens using this batch
	public SpriteBatch batch;

	@Override
	public void create() {
		batch = new SpriteBatch();
		vectorHighscore = new LinkedList<Highscore>();
		try {
			createHighscore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		score = vectorHighscore.get(currentScore);
		setScreen(new MenuState(this));
	}

	@Override
	// draw the images here
	public void render() {
		super.render();
	}

	private void createHighscore() throws NumberFormatException, IOException {
		int pos = -1;
		FileReader fr = new FileReader("scores.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while ((line = br.readLine()) != null) {

			String[] read = line.split("/");
			String name = read[0];
			int num = Integer.parseInt(read[1]);

			if (name.equals("score")) {
				vectorHighscore.add(new Highscore());
				pos++;
			} else {
				vectorHighscore.get(pos).addScores(name, num);
			}
		}
	}

	/**
	 * Saves the current Scores in scores.txt
	 * 
	 * @throws IOException
	 */
	public void saveScore() throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter("scores.txt"));
		String message = "";
		int cur = 0;
		for (Highscore h : vectorHighscore) {
			message += "score/" + cur + "\r\n";
			for (int i = h.getScores().size() - 1; i >= 0; i--) {
				message += h.getScores().get(i).getName() + "/" + h.getScores().get(i).getPoint() + "\r\n";
			}
			cur++;
		}
		bf.write(message);
		bf.flush();
		bf.close();
	}

	/**
	 * 
	 * @return the current highscore
	 */
	public Highscore getCurrentHighscore() {
		return score;
	}

	/**
	 * set the current score to the next. Is used for the highscoreState to
	 * switch the screen with the shown scores.
	 */
	public void nextHighscore() {
		currentScore++;
		if (currentScore > vectorHighscore.size() - 1) {
			currentScore = 0;
		}
		score = vectorHighscore.get(currentScore);
	}

	/**
	 * get the last score. Is used for the highscoreState to switch the screen
	 * with the shown scores.
	 */
	public void lastHighscore() {
		currentScore--;
		if (currentScore < 0) {
			currentScore = vectorHighscore.size() - 1;
		}
		score = vectorHighscore.get(currentScore);
	}

}