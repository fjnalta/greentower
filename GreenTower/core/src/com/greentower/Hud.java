package com.greentower;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * This class representation the HUD of the game.
 * There is a timecounter
 * 
 * @author Ahmed
 */

public class Hud {
	
	//need new viewport for the HUD
	public Stage stage;
	private Viewport viewport;
	
	private Label timeLabel;
	long beginTime;
	
	public Hud(SpriteBatch sb){
		this.beginTime = TimeUtils.millis();
		
		viewport = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, sb);
		
		//create table to organize HUD
		Table table = new Table();
		table.top();
		//set table on full size
		table.setFillParent(true);
		
		timeLabel = new Label("TIME: " + 0, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.add(timeLabel);
		//add the table to the stage
		stage.addActor(table);
	}
	private long getChangeTimeSecond(){
		return (TimeUtils.millis() - beginTime );
	}

	public void update(){
		timeLabel.setText("TIME: "+ this.getChangeTimeSecond()/1000);	
		
	}
}
