package com.greentower.states;

import java.util.List;

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
import com.greentower.data.HighscoreList;
import com.greentower.data.HighscoreListEntry;

public class HighscoreState implements Screen {
	
	private static final int HIGHSCORES_ROW_COUNT = 10;
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Stage stage;
	
	private Table highscoreTable;
	
	private List<HighscoreList> highscores;
	private int highscoresIndex = 0;
	
	Label.LabelStyle font;
	
	
	public HighscoreState(GreenTowerGame game){
		
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		stage = new Stage(gamePort, ((GreenTowerGame) game).batch);
		
		highscores = HighscoreList.loadAllScores();
		
		font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		highscoreTable = new Table();
		highscoreTable.top();
		highscoreTable.setFillParent(true);

		setupHighscoreTable();
		
		stage.addActor(highscoreTable);
	}
	
	private void setupHighscoreTable()
	{
		highscoreTable.clear();
		
		if(highscoresIndex >= 0
			&& highscoresIndex < highscores.size())
		{
			HighscoreList list = highscores.get(highscoresIndex);
			
			Label headLine = new Label(list.getLevelName(), font);
			highscoreTable.add(headLine);
			highscoreTable.row();
			
			
			for(int i = 0;
					i < list.size()
					&& i < HIGHSCORES_ROW_COUNT;
					i++)
			{
				HighscoreListEntry entry = list.get(i);
				
				
				Label scoreLabel = new Label(
						String.format("%.2f    %s", entry.time, entry.name), font);
				
				highscoreTable.add(scoreLabel);
				highscoreTable.row();
			}
			
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	private void handleInput() {
		
		if(Gdx.input.isKeyJustPressed(Keys.LEFT))
			highscoresIndex = Math.max(0, highscoresIndex - 1);
		
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT))
			highscoresIndex = Math.min(highscores.size() - 1, highscoresIndex + 1);
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
			game.setScreen(new MenuState(this.game));
		
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
}
