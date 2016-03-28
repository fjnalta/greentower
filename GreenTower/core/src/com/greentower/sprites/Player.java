package com.greentower.sprites;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.greentower.GreenTowerGame;
import com.greentower.states.PlayState;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Player {

	//set initial playerState
//	public playerState state;
	
	private static final float HITBOX_WIDTH = 32f;
	private static final float HITBOX_HEIGHT = 32f;
	
	private BodyDef def;
	private Body box;
	private PolygonShape shape;
	private FixtureDef fdef;
	
	public Player(){
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
	
	//playerStates for handling animations and prevent multiple jumping
//	public enum playerState {
//		idle,
//		jumping,
//		running
//	}

	
//	private void setPlayerState(){
//		if(velocity.y == 0 && velocity.x == 0 && state != playerState.jumping)
//			state = playerState.idle;
//		else if(velocity.y < -0.1 || velocity.y > 0.1)
//			state = playerState.jumping;
//		else if((velocity.x < -0.1 || velocity.x > 0.1) && state == playerState.idle)
//			state = playerState.running;
//	}
}

