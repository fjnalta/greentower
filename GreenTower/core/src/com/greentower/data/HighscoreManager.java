package com.greentower.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;

/**
 * Manages the Highscores of all maps. It is possible to save the stats for all
 * maps in one file.
 * 
 * @author jonathan
 *
 */
public class HighscoreManager {

	public Vector<Highscore> highscoreVector;
	public Highscore currenScore;
	public int currenPosScore = -1;
	public int levelNumber;

	/**
	 * 
	 * @param levelNumber
	 *            max. number of levels
	 * @throws IOException
	 */
	public HighscoreManager(int levelNumber) throws IOException {
		this.levelNumber = levelNumber;
		highscoreVector = new Vector<Highscore>();
		for (int i = levelNumber; i > 0; i--) {
			highscoreVector.addElement(new Highscore());
		}
		readFile();
	}

	public Highscore getHighscore() {
		return currenScore;
	}

	private void readFile() throws NumberFormatException, IOException {
		FileReader fr = new FileReader("scores.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] read = line.split("/");
			String name = read[0];
			int point = Integer.parseInt(read[1]);
			if (readAnotherLine(name) && currenPosScore < highscoreVector.size() - 1) {
				currenPosScore++;
				currenScore = highscoreVector.get(currenPosScore);
			} else {
				currenScore.addScores(name, point);
			}
		}
		currenPosScore = 0;
	}

	private boolean readAnotherLine(String s) {
		if (s.equals("score")) {
			return true;
		}
		return false;
	}

	/**
	 * Writes the scores into a file
	 * 
	 * @throws IOException
	 */
	public void saveScore() throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter("scores.txt"));
		for (Highscore score : highscoreVector) {
			String message = "score/" + currenPosScore + "\r\n";
			for (int i = score.size() - 1; i >= 0; i--) {
				message += score.getScores().get(i).name + "/" + score.getScores().get(i).point + "\r\n";
			}
			bf.write(message);
		}
		bf.flush();
		bf.close();
	}

	/**
	 * 
	 * @return a dynamic array that contains points
	 */
	public Vector<Point> getCurrentHighscore() {
		currenScore = highscoreVector.get(currenPosScore);
		return currenScore.getScores();
	}

	/**
	 * 
	 * @param p the point to be added to the highscore
	 */
	public void add(Point p) {

	}

	private void printAllScores() {
		int count = 1;
		System.out.println("es gibt " + highscoreVector.size() + " Vectoren");
		for (Highscore high : highscoreVector) {
			System.out.println("___________LEVEL" + count + "_________");
			for (Point point : high.getScores()) {
				System.out.println(point.toString());
			}
			count++;
		}
	}
}
