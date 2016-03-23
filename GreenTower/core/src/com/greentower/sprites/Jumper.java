package com.greentower.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

public class Jumper extends Sprite {
	
	public Vector3 position;
	
	private static final int FRAME_COLS = 5;
    private static final int FRAME_ROWS = 1;

    private Animation animation;
    private Texture animationSheet;
    private TextureRegion[] frames;
    private TextureRegion currentFrame;
    float stateTime;

    public Jumper() {
        animationSheet = new Texture("Jumper.png");
        TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames[index++] = tmp[i][j];
            }
        }
        animation = new Animation(0.025f, frames);
        stateTime = 0f;
    }

    public void update(float dt) {
        stateTime += dt;
        currentFrame = animation.getKeyFrame(stateTime, true);
    }
}
