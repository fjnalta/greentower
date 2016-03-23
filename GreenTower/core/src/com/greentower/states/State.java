package com.greentower.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.greentower.sprites.Player;

//handle menu, pause and play with states
public abstract class State {
	
	protected OrthographicCamera cam;
	protected Vector3 mouse;
	protected GameStateManager gsm;
	public Player player;
	public BitmapFont font;
	
	protected State(GameStateManager gsm){
		this.gsm = gsm;
		cam = new OrthographicCamera();
		mouse = new Vector3();
		font = new BitmapFont();
	}
	
	protected abstract void handleInput();
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	public abstract void dispose();
	
}
