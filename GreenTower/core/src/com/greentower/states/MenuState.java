package com.greentower.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.GreenTowerGame;

public class MenuState extends State{
	
	private Texture background;
	private Texture playBtn;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		
		background = new Texture("bg.png");
		playBtn = new Texture("playBtn.png");
	}

	@Override
	public void handleInput() {
		if(Gdx.input.justTouched()){
			gsm.set(new PlayState(gsm));
			dispose();
		}
		
	}

	@Override
	public void update(float dt) {
		//always check input first
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		//open "container"
		sb.begin();
		sb.draw(background, 0, 0, GreenTowerGame.WIDTH, GreenTowerGame.HEIGHT);
		sb.draw(playBtn, (GreenTowerGame.WIDTH / 2) - (playBtn.getWidth() / 2), GreenTowerGame.HEIGHT / 2);
		sb.end();
	}

	@Override
	public void dispose() {
		background.dispose();
		playBtn.dispose();	
	}

}
