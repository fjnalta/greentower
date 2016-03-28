package com.greentower.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import java.io.File;

public class HighscoreList {

	private static final String SCORES_DIRECTORY = "scores/";
	private static final String SEPARATOR = ";";
	private static final String FILE_EXTENSION = ".txt";
	
	private String levelName;
	
	private List<HighscoreListEntry> scores;

	
	public HighscoreList(String levelName)
	{
		scores = new ArrayList<HighscoreListEntry>();
		
		this.levelName = levelName;
	}


	public void insertScore(HighscoreListEntry entry) {
		
		scores.add(entry);
		
		Collections.sort(scores);
	}
	
	public int size() {
		return scores.size();
	}
	
	public HighscoreListEntry get(int index)
	{
		return scores.get(index);
	}
	
	public String getLevelName()
	{
		return levelName;
	}

	/**
	 * Writes the scores into a file
	 * 
	 * @throws IOException
	 */
	public void saveScore() throws IOException
	{
		File scoreDirectory = new File(SCORES_DIRECTORY);
		scoreDirectory.mkdirs();
		
		BufferedWriter bf = new BufferedWriter(
				new FileWriter(SCORES_DIRECTORY + levelName + FILE_EXTENSION));
		
		for (HighscoreListEntry entry : scores)
		{
			bf.write(entry.name + SEPARATOR + entry.time);
			bf.newLine();
		}
		
		bf.close();
	}


	public static HighscoreList load(String levelName) throws IOException
	{
		HighscoreList list = new HighscoreList(levelName);
		
		FileReader fr = new FileReader(SCORES_DIRECTORY + levelName + FILE_EXTENSION);
		
		BufferedReader br = new BufferedReader(fr);
	
		String line = "";
		while((line = br.readLine())!= null)
		{
			String[] read = line.split(SEPARATOR);
			
			String name = read[0];
			float time = Float.parseFloat((read[1]));
		
		
			list.scores.add(new HighscoreListEntry(name, time));
		}
		
		br.close();
		
		Collections.sort(list.scores);
		
		return list;
	}
	
	private static HighscoreList loadFromFile(File file) throws IOException
	{
		HighscoreList list = new HighscoreList(
				getFileNameWithoutExtension(file.getName()));
		
		FileReader fr = new FileReader(file);
		
		BufferedReader br = new BufferedReader(fr);
	
		String line = "";
		while((line = br.readLine())!= null)
		{
			String[] read = line.split(SEPARATOR);
			
			String name = read[0];
			float time = Float.parseFloat((read[1]));
		
		
			list.scores.add(new HighscoreListEntry(name, time));
		}
		
		br.close();
		
		Collections.sort(list.scores);
		
		return list;
	}
	
	private static String getFileNameWithoutExtension(String fileName)
	{
		if (fileName == null)
			return null;

        int pos = fileName.lastIndexOf(".");

        if (pos == -1)
        	return fileName;

        return fileName.substring(0, pos);
	}
	
	public static List<HighscoreList> loadAllScores()
	{
		File scoreDirectory = new File(SCORES_DIRECTORY);
		
		File[] files = scoreDirectory.listFiles();
		
		List<HighscoreList> highscores = new ArrayList<HighscoreList>();
		
		for(File file : files)
		{
			try {
				highscores.add(loadFromFile(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return highscores;
	}
}
