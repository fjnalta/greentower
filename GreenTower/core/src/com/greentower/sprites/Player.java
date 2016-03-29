package com.greentower.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.greentower.GreenTowerGame;
import com.greentower.states.PlayState;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player {
	
	//playerStates for handling animations and prevent multiple jumping
	public enum playerState {
		idle,
		jumping,
		running
	}

	//set initial playerState
	public playerState state;
	
	private static final float HITBOX_WIDTH = 32f;
	private static final float HITBOX_HEIGHT = 32f;
	
	private BodyDef def;
	private Body box;
	private PolygonShape shape;
	private FixtureDef fdef;
	
	private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 3;

    private Animation[] animation; //[0] = walkLeft, [1] = walkRight, [2] = idle, [3] = jump
    private Texture animationSheet;
    private TextureRegion[][] frames;
    public TextureRegion currentFrame;
    float stateTime;
	
	public Player(){
		state = playerState.idle;
		
		def = new BodyDef();
		def.position.set(3, 3);
		def.type = BodyType.DynamicBody;
		box = PlayState.world.createBody(def);
		box.setGravityScale(2f);
		shape = new PolygonShape();
		shape.setAsBox(1, 1);
		fdef = new FixtureDef();
		fdef.shape = shape;
		box.createFixture(fdef);
		
		//animations
		animationSheet = new Texture("Player.png");
        TextureRegion[][] tmp = TextureRegion.split(animationSheet, animationSheet.getWidth()/FRAME_COLS, animationSheet.getHeight()/FRAME_ROWS);
        frames = new TextureRegion[4][FRAME_COLS];
        animation = new Animation[4];
        
        //walkLeft
        for (int i = 0, index = 0; i < FRAME_COLS; i++, index++) {
        	frames[0][index] = tmp[0][i];
        }
        assert(frames[0] != null);
        animation[0] = new Animation(0.1f, frames[0]);
        //walkRight
        for (int i = 0, index = 0; i < FRAME_COLS; i++, index++) {
            frames[1][index] = tmp[1][i];
        }
        animation[1] = new Animation(0.1f, frames[1]);
        //idle
        frames[2][0] = tmp[2][0];
        animation[2] = new Animation(0.1f, frames[2][0]);
        //jump
        frames[3][0] = tmp[2][1];
        animation[3] = new Animation(0.1f, frames[3][0]);
        
        currentFrame = animation[2].getKeyFrame(0, true);
	}
	
	public Body getBody(){
		return box;
	}
	
	public Rectangle getPlayerRect()
	{
		return new Rectangle(
				box.getPosition().x * GreenTowerGame.PPM,
				box.getPosition().y * GreenTowerGame.PPM,
				HITBOX_WIDTH,
				HITBOX_HEIGHT);
	}
	
    public void update(float dt) {
        stateTime += dt;
		if(box.getLinearVelocity().x == 0 && box.getLinearVelocity().y == 0) {
			state = playerState.idle;
			System.out.println(state + ": " + box.getLinearVelocity());
		}
		
		if(box.getLinearVelocity().y < -0.1 || box.getLinearVelocity().y > 0.1) {
			state = playerState.jumping;
			System.out.println(state + ": " + box.getLinearVelocity());
		} else if((box.getLinearVelocity().x < -0.1 || box.getLinearVelocity().x > 0.1)) {
			state = playerState.running;
		}
		
		switch(state) {
		case jumping:
			currentFrame = animation[3].getKeyFrame(stateTime, true);
		case running:
			if(box.getLinearVelocity().x < -0.1)
				currentFrame = animation[0].getKeyFrame(stateTime, true);
			else
				currentFrame = animation[1].getKeyFrame(stateTime, true);
		case idle:
			currentFrame = animation[2].getKeyFrame(stateTime, true);
		}
    }
}

