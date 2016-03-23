package com.greentower.sprites;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.greentower.SpatialHashMap;
import com.sun.media.jfxmedia.events.PlayerStateEvent.PlayerState;

/**
 * Contains the logic to update and draw the player.
 */
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
	
	private static final float VELOCITY_JUMP = 400f;
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
	
	private boolean isOnGround = false;
	
	private SpatialHashMap<RectangleMapObject> collisionMap;
	
	
	/**
	 * Initializes a new instance of the {@link Player} class.
	 * 
	 * @param posX the horizontal position of the player.
	 * @param posY the vertical position of the player.
	 * @param collisionMap the {@link SpatialHashMap} which is used for collision
	 * detection.
	 */
	public Player(
			int posX,
			int posY,
			SpatialHashMap<RectangleMapObject> collisionMap)
	{
		position = new Vector2(posX, posY);
		velocity = new Vector2(0, 0);
		
		texture = new Texture("player.png");
		
		this.collisionMap = collisionMap;
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
	

	/**
	 * Tells the current {@link Player} to update itself.
	 * @param deltaTime
	 */
	public void update(float deltaTime){
		
		handleInput();
		
		checkForCollision();
		
		//set the PlayerState
		updatePlayerState();
				
		if(!isOnGround)
			velocity.add(0, GRAVITY); //add gravity by delta time
		
		
		float changeX = velocity.x * deltaTime;
		float changeY = velocity.y * deltaTime;
		
		//controls in air are not as direct as on ground
		/*if(state == playerState.jumping)
		{
			if((velocity.x >= 0 && moveDirection.x <= 0)
				|| (velocity.x <= 0 && moveDirection.x >= 0))
			{
				velocity.x += (moveDirection.x * MOVEMENT * deltaTime) / 40;
			}
		}
		else*/
			//velocity.x = moveDirection.x * MOVEMENT * deltaTime;
		
		//add velocity to the player position
		
		position.x += changeX;
		position.y += changeY;
	
	}
	
	/**
	 * Checks for collision with the world.
	 */
	private void checkForCollision()
	{
		//---Vertical collision
		
		if(velocity.y > 0) //moving up
		{
			checkCollisionTop();
		}
		else if(velocity.y < 0) // moving down
		{
			isOnGround = checkCollisionBottom();
		}
		
		
		//---Horizontal collision
		
		if(velocity.x > 0) // moving right
		{
			checkCollisionRight();
		}
		else if(velocity.x < 0) //moving left
		{
			checkCollisionLeft();
		}
	}
	
	
	private boolean checkCollisionTop()
	{
		int leftPosX = (int)position.x;
		int leftPosY = (int)position.y + BOX_HEIGHT;
		
		int rightPosX = (int)position.x + BOX_WIDTH;
		int rightPosY = (int)position.y + BOX_HEIGHT;
		
	
		if(collisionMap.contains(leftPosX, leftPosY)
			|| collisionMap.contains(rightPosX, rightPosY))
		{
			velocity.y = 0;
			
			position.y = (((int)leftPosY / collisionMap.getCellSize()) * collisionMap.getCellSize()) - BOX_HEIGHT;
			
			return true;
		}
		
		return false;
	}
	
	private boolean checkCollisionBottom()
	{
		int leftPosX = (int)position.x;
		int leftPosY = (int)position.y;
		
		int rightPosX = (int)position.x + BOX_WIDTH;
		int rightPosY = (int)position.y;
		
	
		if(collisionMap.contains(leftPosX, leftPosY)
			|| collisionMap.contains(rightPosX, rightPosY))
		{
			velocity.y = 0;
			
			position.y = (((int)position.y / collisionMap.getCellSize()) + 1) * collisionMap.getCellSize();
			
			return true;
		}
		
		return false;
	}
	
	private boolean checkCollisionLeft()
	{
		int bottomPosX = (int)position.x;
		int bottomPosY = (int)position.y;
		
		int topPosX = (int)position.x;
		int topPosY = (int)position.y + BOX_HEIGHT;
		
	
		if(collisionMap.contains(bottomPosX, bottomPosY)
			|| collisionMap.contains(topPosX, topPosY))
		{
			velocity.x = 0;
			
			position.x = (((int)position.x / collisionMap.getCellSize()) + 1) * collisionMap.getCellSize();
			
			return true;
		}
		
		return false;
	}
	
	private boolean checkCollisionRight()
	{
		int bottomPosX = (int)position.x + BOX_WIDTH;
		int bottomPosY = (int)position.y;
		
		int topPosX = (int)position.x + BOX_WIDTH;
		int topPosY = (int)position.y + BOX_HEIGHT;
		
	
		if(collisionMap.contains(bottomPosX, bottomPosY)
			|| collisionMap.contains(topPosX, topPosY))
		{
			velocity.x = 0;
			
			position.x = (((int)bottomPosX / collisionMap.getCellSize()) * collisionMap.getCellSize()) - BOX_WIDTH;
			
			return true;
		}
		
		return false;
	}
	
	
	/**
	 * Handles the keyboard input.
	 */
	private void handleInput()
	{
		if(Gdx.input.isKeyPressed(Keys.SPACE))
			jump();
		
		if(Gdx.input.isKeyPressed(Keys.LEFT))
			velocity.x = -VELOCITY_WALK;
		
		else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			velocity.x = VELOCITY_WALK;
		
		else
			velocity.x = 0;
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
		if(isOnGround){
			velocity.y = VELOCITY_JUMP;
			
			isOnGround = false;
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

