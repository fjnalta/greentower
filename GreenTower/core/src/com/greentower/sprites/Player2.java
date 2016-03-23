package com.greentower.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.World;

public class Player2 extends Sprite {
	
	//playerStates for handling animations and prevent multiple jumping
	public enum playerState {
		idle,
		jumping,
		running
	}
	//set initial playerState
	public playerState state;

	//create Gravity on Player only
	private static final int GRAVITY = -15;
	private static final int MOVEMENT = 200;
	
	private Vector3 moveDirection;
	public Vector3 position;
	private Vector3 velocity;
	
	private Texture player;
	
	public Player2(World world) {
		position = new Vector3(64, 64, 0);
		velocity = new Vector3(0, 0, 0);
		player = new Texture("player.png");
	}
	
	public void update(float dt){
		//set the PlayerState
		setPlayerState();
		
		if(position.y > 0){
			//add gravity by delta time
			velocity.add(0, GRAVITY, 0);
		}
		
		velocity.scl(dt);
		//don't fall out of the map
		if (position.y < 0){
			velocity.y = 0;
			position.y = 0;
			state = playerState.idle;
		}
		//controls in air are not as direct as on ground
		if(state == playerState.jumping) {
			if((velocity.x >= 0 && moveDirection.x <= 0) || (velocity.x <= 0 && moveDirection.x >= 0))
				velocity.x += (moveDirection.x * MOVEMENT * dt)/40;
		}
		else
			velocity.x = moveDirection.x * MOVEMENT * dt;
		
		//add velocity to the player position
		position.add(velocity.x, velocity.y, 0);
		//reverse scl
		velocity.scl(1/dt);
	}

	public Vector3 getPosition() {
		return position;
	}

	public Texture getTexture() {
		return player;
	}
	
	public Vector3 getVelocity() {
		return velocity;
	}
	
	public void jump() {
		if(state != playerState.jumping){
			velocity.y = 1000;
		}
	}
	
	public void setMoveDirection(Vector3 dir){
		this.moveDirection = dir;
	}
	
	private void setPlayerState(){
		if(velocity.y == 0 && velocity.x == 0 && state != playerState.jumping)
			state = playerState.idle;
		else if(velocity.y < -0.1 || velocity.y > 0.1)
			state = playerState.jumping;
		else if((velocity.x < -0.1 || velocity.x > 0.1) && state == playerState.idle)
			state = playerState.running;
	}
	
	private void detectCollision(){
		
	}
}
