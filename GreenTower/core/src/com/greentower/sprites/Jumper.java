package com.greentower.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.greentower.states.PlayState;

public class Jumper extends Sprite {
	
	public Vector2 position;
	private PlayState world;
	public Rectangle rectangle;
	
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;

    private Animation animation;
    private Texture animationSheet;
    private TextureRegion[] frames;
    public TextureRegion currentFrame;
    float stateTime;

    public Jumper(float x, float y, PlayState world) {
    	this.position = new Vector2(x,y);
    	this.world = world;
    	
        animationSheet = new Texture("Jumper.png");
        TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) { 
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.1f, frames);
        currentFrame = animation.getKeyFrame(0);
        
        rectangle = new Rectangle(x, y, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight());
        stateTime = 0f;
    }

    public void update(float dt) {
    	if(world.player.getPlayerRect().overlaps(rectangle))
    		world.player.getBody().setLinearVelocity(world.player.getBody().getLinearVelocity().x, 150f);
    	
        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime, true);
    }
}
