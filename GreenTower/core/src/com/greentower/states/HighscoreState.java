package com.greentower.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
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

public class HighscoreState implements Screen {
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Stage stage;
	
	private Table highscoreTable;
	
	public HighscoreState(GreenTowerGame game){
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		stage = new Stage(gamePort, ((GreenTowerGame) game).batch);
		
		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		highscoreTable = new Table();
		highscoreTable.center();
		highscoreTable.setFillParent(true);
		
		Label headLine = new Label("HIGHSCORES LEVEL.getName()?", font);
		
		highscoreTable.add(headLine);
		highscoreTable.row();
	
		for (Point p : GreenTowerGame.score.getScores()){
			Label tempLabel = new Label(p.toString(), font);
			highscoreTable.add(tempLabel);
			highscoreTable.row();
		}
		
		stage.addActor(highscoreTable);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleInput(){
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			game.setScreen(new MenuState(this.game));
			dispose();
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)){
			game.lastHighscore();
			game.setScreen(new HighscoreState(this.game));
			dispose();
		}
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)){
			game.nextHighscore();
			game.setScreen(new HighscoreState(this.game));
			dispose();
		}
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
		stage.dispose();
	}
}
