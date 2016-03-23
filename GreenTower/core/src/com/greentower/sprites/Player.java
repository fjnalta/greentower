package com.greentower.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.greentower.GreenTowerGame;
import com.greentower.sprites.Player.playerState;
import com.greentower.states.GameStateManager;
import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

public class Player {
	
	//playerStates for handling animations and prevent multiple jumping
	public enum playerState {
		idle,
		jumping,
		running
	}
	

	//create Gravity on Player only
	private static final int GRAVITY = -15;
	private static final int MOVEMENT = 200;
	
	private static final float VELOCITY_JUMP = 200f;
	private static final float VELOCITY_WALK = 200f;
	
	/**
	 * Width of the hitbox of the player.
	 */
	public static final int BOX_WIDTH = 32;
	
	/**
	 * Height of the hitbox of the player.
	 */
	public static final int BOX_HEIGHT = 64;
	
	
	//set initial playerState
	private playerState state;
	
	private Vector2 position;
	private Vector2 moveDirection;
	private Vector2 velocity;
	
	private Texture texture;
	
	
	/**
	 * Initializes a new instance of the {@link Player} class.
	 * 
	 * @param posX the horizontal position of the player.
	 * @param posY the vertical position of the player.
	 */
	public Player(int posX, int posY) {
		position = new Vector2(posX, posY);
		velocity = new Vector2(0, 0);
		
		texture = new Texture("player.png");
	}
	
	
	/**
	 * Gets the position of the current {@link Player}.
	 * 
	 * @return a {@link Vector2}.
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Gets the velocity of the current {@link Player}.
	 * 
	 * @return a {@link Vector2}.
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * Sets the move direction of the current {@link Player}.
	 *  
	 * @param moveDirection the new direction.
	 */
	public void setMoveDirection(Vector2 moveDirection){
		this.moveDirection = moveDirection;
	}
	

	
	public void update(float deltaTime){
		
		handleInput();
		
		//set the PlayerState
		updatePlayerState();
				
		if(position.y > 0)
			velocity.add(0, GRAVITY); //add gravity by delta time
		
		velocity.scl(deltaTime);
		//don't fall out of the map
		if (position.y < 0) 
		{
			velocity.y = 0;
			position.y = 0;
			state = playerState.idle;
		}
		
		//controls in air are not as direct as on ground
		if(state == playerState.jumping)
		{
			if((velocity.x >= 0 && moveDirection.x <= 0)
				|| (velocity.x <= 0 && moveDirection.x >= 0))
			{
				velocity.x += (moveDirection.x * MOVEMENT * deltaTime) / 40;
			}
		}
		else
			velocity.x = moveDirection.x * MOVEMENT * deltaTime;
		
		//add velocity to the player position
		position.add(velocity.x, velocity.y);
		//reverse scl
		velocity.scl(1 / deltaTime);
	}
	
	/**
	 * Handles the keyboard input.
	 */
	private void handleInput()
	{
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			jump();
		
		if(Gdx.input.isKeyPressed(Keys.LEFT) 
			|| Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			if(Gdx.input.isKeyPressed(Keys.LEFT))
				setMoveDirection(new Vector2(-1, 0));
			
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
				setMoveDirection(new Vector2(1, 0));
			
		}
		else
			setMoveDirection(new Vector2(0, 0));
	}
	
	/**
	 * Updates the {@link PlayerState} of the current {@link Player}.
	 */
	private void updatePlayerState(){
		if(velocity.y == 0 && velocity.x == 0 && state != playerState.jumping)
			state = playerState.idle;
		else if(velocity.y < -0.1 || velocity.y > 0.1)
			state = playerState.jumping;
		else if((velocity.x < -0.1 || velocity.x > 0.1) && state == playerState.idle)
			state = playerState.running;
	}
	
	/**
	 * Tells the current {@link Player} to jump.
	 */
	private void jump() {
		if(state != playerState.jumping){
			velocity.y = 1000;
		}
	}
	
	
	/**
	 * Draws the current {@link Player}.
	 * 
	 * @param spritebatch the {@link SpriteBatch} which is used for drawing.
	 */
	public void draw(SpriteBatch spritebatch)
	{
		spritebatch.draw(
				texture,
				position.x,
				position.y,
				BOX_WIDTH,
				BOX_HEIGHT);
	}
}

