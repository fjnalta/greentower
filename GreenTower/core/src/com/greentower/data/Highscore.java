package com.greentower.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

public class Highscore {

	public Vector<Point> scores;

	/**
	 * Read the old scores from the scores.txt
	 * 
	 * @throws IOException
	 */
	public Highscore() throws IOException {
		scores = new Vector<Point>();

	}

	/**
	 * 
	 * @return Vector<Point> that contains the points.
	 */
	public Vector<Point> getScores() {
		return scores;
	}

	/**
	 * 
	 * @param name
	 *            players name
	 * @param score
	 *            players score
	 */
	public void addScores(String name, int score) {
		scores.addElement(new Point(name, score));
		Collections.sort(scores);
	}

	public int size() {
		return scores.size();
	}

}
