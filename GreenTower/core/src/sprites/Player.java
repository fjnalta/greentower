package sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

import sprites.Player.playerState;

public class Player {
	
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
	private Vector3 position;
	private Vector3 velocity;
	
	private Texture player;
	
	public Player(int x, int y){
		position = new Vector3(x, y, 0);
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
		
		//add velocity to the player position
		position.add(moveDirection.x * MOVEMENT * dt, velocity.y, 0);
		velocity.x = moveDirection.x * MOVEMENT * dt;
		//don't fall out of the map
		if (position.y < 0){
			velocity.y = 0;
			position.y = 0;
		}
		//reverse scl
		velocity.scl(1/dt);
		
		//System.out.println(state);
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
			velocity.y = 500;
		}
	}
	
	public void setMoveDirection(Vector3 dir){
		this.moveDirection = dir;
	}
	
	private void setPlayerState(){
		if(velocity.x < -0.1 || velocity.x > 0.1){
			state = playerState.running;
		}
		if(velocity.y < -0.1 || velocity.y > 0.1){
			state = playerState.jumping;
		} else if (velocity.x < 0.1 && velocity.x > -0.1){
			state = playerState.idle;
		}
	}
	
}

