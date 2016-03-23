package com.greentower.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greentower.GreenTowerGame;

/**
 * This Class representation the MainMenu. First du have to press Space, and
 * then you can choice between start game and highscorelist. Start game -> start
 * the game highscorelist -> show the Highscore
 * 
 * @author Yangus
 *
 */
public class MenuState implements Screen {
	
	private Texture titleText = new Texture("bg.png");
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Stage stage;
	
	private Label startLabel;
	private Label highscoreLabel;
	private Table menuTable;
	
	
	private boolean wannaPlay;
	
	public MenuState(GreenTowerGame game){
		
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		stage = new Stage(gamePort, ((GreenTowerGame) game).batch);

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		menuTable = new Table();
		menuTable.center();
		menuTable.setFillParent(true);
		
		startLabel = new Label("START GAME", font);
		highscoreLabel = new Label("HIGHSCORE", font);
		startLabel.setColor(Color.RED);
		
		menuTable.add(startLabel);
		menuTable.row();
		menuTable.add(highscoreLabel);
		stage.addActor(menuTable);
		
		wannaPlay = true;
	}

	@Override
	public void show() {
		
	}
	
	@Override
	public void render(float delta) {
		update(delta);
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.batch.begin();
		game.batch.draw(titleText, (GreenTowerGame.V_WIDTH / 2) - (titleText.getWidth() / 2), (GreenTowerGame.V_HEIGHT) - (titleText.getHeight()));
		game.batch.end();		
		
		
		stage.draw();
	}
	
	public void update(float dt){
		handleInput(dt);
	}
	
	public void handleInput(float dt){
		if ((!wannaPlay) && Gdx.input.isKeyJustPressed(Keys.ENTER)){
			game.setScreen(new HighscoreState(this.game));
		}
		if ((wannaPlay) && Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			game.setScreen(new PlayState(this.game));
		}
		if (Gdx.input.isKeyJustPressed(Keys.UP)){
			if(!wannaPlay){
				wannaPlay = true;
				startLabel.setColor(Color.RED);
				highscoreLabel.setColor(Color.WHITE);
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.DOWN)){
			if(wannaPlay){
				wannaPlay = false;
				startLabel.setColor(Color.WHITE);
				highscoreLabel.setColor(Color.RED);
			}
		}
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
}
