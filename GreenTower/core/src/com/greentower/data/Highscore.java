package com.greentower.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Highscore {

	private Vector<Point> scores;

	/**
	 * Read the old scores from the scores.txt
	 * @throws IOException
	 */
	public Highscore() throws IOException{
		scores = new Vector<Point>();
		FileReader fr = new FileReader("scores.txt");
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		while((line = br.readLine())!= null){
			String[] read = line.split("/");
			String name = read[0];
			int point = Integer.parseInt(read[1]);
			scores.addElement(new Point(name,point));
		}
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
	 * @param name	players name
	 * @param score players score
	 */
	public void addScores(String name, int score) {
		scores.addElement(new Point(name, score));
		Collections.sort(scores);
	}

	/**
	 * Writes the scores into a file
	 * 
	 * @throws IOException
	 */
	public void saveScore() throws IOException {
		BufferedWriter bf = new BufferedWriter(new FileWriter("scores.txt"));
		String message = "";
		for (int i = scores.size() - 1; i >= 0; i--) {
			message += scores.get(i).name + "/" + scores.get(i).point + "\r\n";
		}
		bf.write(message);
		bf.flush();
		bf.close();
	}

	public int size() {
		return scores.size();
	}

}
