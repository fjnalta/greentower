package com.greentower.states;

import java.util.Vector;

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
import com.greentower.data.Highscore;
import com.greentower.data.Point;

public class HighscoreState implements Screen {

	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Stage stage;

	private Table highscoreTable;

	public HighscoreState(GreenTowerGame game) {
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		stage = new Stage(gamePort, ((GreenTowerGame) game).batch);

		Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
		highscoreTable = new Table();
		highscoreTable.center();
		highscoreTable.setFillParent(true);

		Label headLine = new Label("HIGHSCORES LEVEL"+ game.highscore.currenPosScore, font);

		highscoreTable.add(headLine);
		highscoreTable.row();
		Vector<Point> var= game.highscore.getCurrentHighscore();
		for (Point p : var) {
			System.out.println(p.toString() +  " CurrentPos->" + game.highscore.currenPosScore + var.size());
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

	private void handleInput() {
		int currentPosition = game.highscore.currenPosScore;
		if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			game.setScreen(new MenuState(this.game));
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if (currentPosition <= 0) {
				game.highscore.currenPosScore = game.highscore.highscoreVector.size()-1;
			} else {
				game.highscore.currenPosScore--;
			}
			game.setScreen(new HighscoreState(this.game));
			this.dispose();
		}
		
		if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if (currentPosition < game.highscore.highscoreVector.size()-1) {
				game.highscore.currenPosScore++;
			} else {
				game.highscore.currenPosScore = 0;
			}
			game.setScreen(new HighscoreState(this.game));
			this.dispose();
		}

	}

	public void update(float dt) {
		handleInput();
	}

	@Override
	public void render(float delta) {

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update(delta);
		
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
