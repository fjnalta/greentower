package com.greentower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Hud {
	
	//need new viewport for the HUD
	public Stage stage;
	
	private Viewport viewport;
	
	private float playTime;
	private Label timeLabel;
	
	public Hud(SpriteBatch sb){
		playTime = 0f;
		
		viewport = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//create table to organize HUD
		Table table = new Table();
		table.top();
		//set table on full size
		table.setFillParent(true);
		
		timeLabel = new Label("TIME: " + playTime, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(timeLabel);
		//add the table to the stage
		stage.addActor(table);
	}
	
	public void update(float deltaTime)
	{
		playTime += deltaTime;
		
		timeLabel.setText("TIME: " + String.format("%.2f", playTime));
	}
	
	public float getPlayTime()
	{
		return playTime;
	}
}
