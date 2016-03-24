package com.greentower.states;

import java.io.IOException;
import java.util.Collections;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greentower.GreenTowerGame;
import com.greentower.data.Point;

public class GameOverState implements Screen {
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Stage stage;
	
	private Table gameOverTable;
	
	private String playerName;
	
	public GameOverState(GreenTowerGame game){
		
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		stage = new Stage(gamePort, ((GreenTowerGame) game).batch);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		gameOverTable = new Table();
		gameOverTable.center();
		gameOverTable.setFillParent(true);
		
		Label headLine = new Label("GAME OVER", font);
		
		gameOverTable.add(headLine);
		gameOverTable.row();
		
		enterPlayername(game);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	public void handleInput(){
		
	}
	
	public void update(float dt){
		handleInput();
	}

	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.draw();
	}
	
	@Override
	public void resize(int width, int height) {
		//update when the window is resized
		gamePort.update(width, height);
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
		stage.dispose();
		
	}
	
	
	private void enterPlayername(final GreenTowerGame gameInput) {
		Gdx.input.getTextInput(new TextInputListener() {
			@Override
			public void input(String text) {
				gameInput.highscore.currenScore.getScores().add(new Point(text, 100));
				Collections.sort(gameInput.highscore.currenScore.getScores());
				try {
					game.highscore.saveScore();
				} catch (IOException e) {

				}
			}


			@Override
			public void canceled() {
				playerName = "cancled by user";
			}
		}, "Enter your name", "here", "Enter name -> Press R");
	}

}
