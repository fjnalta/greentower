package com.greentower.states;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

public class VictoryScreen implements Screen {
	
	private static final int[] KEYS_LETTER = 
		{
			Input.Keys.A,
			Input.Keys.B,
			Input.Keys.C,
			Input.Keys.D,
			Input.Keys.E,
			Input.Keys.F,
			Input.Keys.G,
			Input.Keys.H,
			Input.Keys.I,
			Input.Keys.J,
			Input.Keys.K,
			Input.Keys.L,
			Input.Keys.M,
			Input.Keys.N,
			Input.Keys.O,
			Input.Keys.P,
			Input.Keys.Q,
			Input.Keys.R,
			Input.Keys.S,
			Input.Keys.T,
			Input.Keys.U,
			Input.Keys.V,
			Input.Keys.W,
			Input.Keys.X,
			Input.Keys.Y,
			Input.Keys.Z
		};
	
	private static final int MAX_NAME_LENGTH = 10;
	
	private static final String lastName = "";
	
	private GreenTowerGame game;
	
	private String levelName;
	private Stage stage;
	private Viewport viewport;
	private StringBuilder nameBuilder;
	
	private Label nameLabel;
	private float playedTime;
	
	
	public VictoryScreen(GreenTowerGame game, String levelName, float playedTime) {
		
		this.game = game;
		this.levelName = levelName;		
		this.playedTime = playedTime;
		
		viewport = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		
		nameBuilder = new StringBuilder(lastName);
		
		
		Table table = new Table();
		table.center();
		table.setFillParent(true);
		
		Label.LabelStyle labelStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		
		table.add(new Label(
				String.format("Your time: %.2f", playedTime),
				labelStyle));
		table.row();
		
		//title
		table.add(new Label("Enter name: ", labelStyle));
		table.row();
		
		
		nameLabel = new Label("", labelStyle);
		table.add(nameLabel);
		
		//add the table to the stage
		stage.addActor(table);
	}
	
	
	
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float delta) {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		handleInput();
		
		stage.draw();
		
	}
	
	private void handleInput()
	{
		for(int i = 0; i < KEYS_LETTER.length; i++)
		{
			if(Gdx.input.isKeyJustPressed(KEYS_LETTER[i])
				&& nameBuilder.length() < MAX_NAME_LENGTH)
			{
				nameBuilder.append((char)('A' + i));
				
				nameLabel.setText(nameBuilder);
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.BACKSPACE)
			&& nameBuilder.length() > 0)
		{
			nameBuilder.deleteCharAt(nameBuilder.length() - 1);
			
			nameLabel.setText(nameBuilder);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && nameBuilder.length() > 0)
			saveScoreAndExit();
	}
	
	private void saveScoreAndExit()
	{
		HighscoreList highscoreList = null;
		
		try
		{
			highscoreList = HighscoreList.load(levelName);
		}
		catch (IOException e) {
			highscoreList = new HighscoreList(levelName);
		}
		
		highscoreList.insertScore(
				new HighscoreListEntry(nameBuilder.toString(), playedTime));
		
		try {
			highscoreList.saveScore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//exit screen
		game.setScreen(new MenuState(game));
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
		this.dispose();
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
