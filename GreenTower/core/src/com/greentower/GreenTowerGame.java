package com.greentower;

import java.io.IOException;
import java.util.Vector;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.data.Highscore;
import com.greentower.data.HighscoreManager;
import com.greentower.states.MenuState;
/**
 * it is necessary to adjust the number of maps!!!
 * @author jonathan
 *
 */
public class GreenTowerGame extends Game {
	
	
	//virtual width and height for the game
	public static final int V_WIDTH = 20*32;
	public static final int V_HEIGHT = 30*32;
	//fix box2d scaling
	public static final int PPM = 100;
	
	// it is necessary to adjust this variable!
	private int numberOfMaps = 5;
	
	public static final String TITLE = "Green Tower";
	
	public HighscoreManager highscore;
	
	//all screens using this batch
	public SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		try {
			highscore = new HighscoreManager(numberOfMaps);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setScreen(new MenuState(this));
	}
	
	@Override
	//draw the images here
	public void render () {
		super.render();
	}
}