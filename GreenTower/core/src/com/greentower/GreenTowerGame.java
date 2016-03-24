package com.greentower;

import java.io.IOException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.data.Highscore;
import com.greentower.states.MenuState;

public class GreenTowerGame extends Game {
	
	
	//virtual width and height for the game
	public static final int V_WIDTH = 20*32;
	public static final int V_HEIGHT = 30*32;
	//fix box2d scaling
	public static final float PPM = 10f;
	
	public static final String TITLE = "Green Tower";
	
	public static Highscore score;

	//all screens using this batch
	public SpriteBatch batch;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		
		try {
			score = new Highscore();
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