package com.greentower.states;

import java.io.IOException;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.GreenTowerGame;
import com.greentower.data.Point;

public class HighscoreState implements Screen {
	
	private GreenTowerGame game;
	
	private String playerName;
	private SpriteBatch b;
	
	public HighscoreState(GreenTowerGame game){
		this.game = game;
//		for (Point p : gsm.score.getScores()) {
//			System.out.print(p.toString()+ ",");
//		}
//		System.out.println();
//		if(enterPlayerName){
//			enterPlayername();
//			setScreen(new HighscoreState(this));
//			dispose();
//		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
//	private void enterPlayername() {
//		Gdx.input.getTextInput(new TextInputListener() {
//			@Override
//			public void input(String text) {
//				gsm.score.getScores().add(new Point(text, 100));
//				Collections.sort(gsm.score.getScores());
//				try {
//					gsm.score.saveScore();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//
//			@Override
//			public void canceled() {
//				playerName = "cancled by user";
//			}
//		}, "Enter your name", "here", "Enter name -> Press R");
//	}
//	
//	@Override
//	protected void handleInput() {
//		if(Gdx.input.isKeyPressed(Keys.ENTER)){
//			setScreen(new PlayState(this));
//			dispose();
//		}
//	}
//
//	@Override
//	public void update(float dt) {
//		// TODO Auto-generated method stub
//	}
//
//	@Override
//	public void render(SpriteBatch sb) {
//		Gdx.gl.glClearColor(0, 0, 0, 0);
//		Gdx.gl.glClear(GL20.GL_DEPTH_BITS);
//		// TODO Auto-generated method stub
//		sb.begin();
//		gsm.peek().font.setColor(Color.WHITE);
//		float middle = Gdx.graphics.getWidth()/2-100;
//		gsm.peek().font.draw(sb, "HIGHSCORE", middle, Gdx.graphics.getHeight()/2+100 );
//		gsm.peek().font.draw(sb, "name/score", middle, Gdx.graphics.getHeight()/2+50 );
//		for(int i = 0; i < gsm.score.size() ; i++){
//			gsm.peek().font.draw(sb, gsm.score.getScores().get(i).toString(), middle, Gdx.graphics.getHeight()/2-(i*20) );
//		}
//		gsm.peek().font.draw(sb, "Press ENTER for MainMenu", middle, Gdx.graphics.getHeight()/2+50 );
//		sb.end();
//		handleInput();
//		
//	}
	
}
