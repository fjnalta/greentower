package com.greentower.sprites;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.greentower.GreenTowerGame;
import com.greentower.sprites.Player.playerState;
import com.greentower.states.GameStateManager;

public class Player {

	// playerStates for handling animations and prevent multiple jumping
	public enum playerState {
		idle, jumping, running, fall
	}

	// set initial playerState
	public playerState state;

	// create Gravity on Player only
	private static final int GRAVITY = -15;
	private static final int MOVEMENT = 200;

	private Vector3 moveDirection;
	private Vector3 position;
	private Vector3 velocity;

	private boolean isAboveElement = true;
	private float currentPos;

	private Texture player;

	private Rectangle hitbox;

	public Player(int x, int y) {
		this.position = new Vector3(x, y, 0);
		this.velocity = new Vector3(0, 0, 0);
		this.player = new Texture("player.png");
		moveDirection = new Vector3(0, 0, 0);
		this.hitbox = new Rectangle(this.position.x, this.position.y, this.player.getWidth(), this.player.getWidth());
	}

	public void update(float dt, List<Rectangle> list) {
		// set the PlayerState
		setPlayerState();

		// Test: Wenn Spieler auf Rechtangle trifft und drauf ist, soll er nicht
		// fallen

		// _____Test End

		velocity.scl(dt);
		// don't fall out of the map
		int size = 15;
		if (this.position.y < size) {
			this.velocity.y = size;
			this.position.y = size;
			this.state = playerState.idle;
		}
		// controls in air are not as direct as on ground
		// if (state == playerState.jumping) {
		// if ((velocity.x >= 0 && moveDirection.x <= 0) || (velocity.x <= 0 &&
		// moveDirection.x >= 0))
		// this.velocity.x += (moveDirection.x * MOVEMENT * dt) / 40;
		// } else
		this.velocity.x = moveDirection.x * MOVEMENT * dt;
		// add velocity to the player position
		position.add(velocity.x, velocity.y, 0);
		// reverse scl
		velocity.scl(1 / dt);

		this.hitbox.setPosition(this.position.x, this.position.y);
		test(list);
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
		if (state != playerState.jumping) {
			velocity.y = 1300;
		}
	}

	public void setMoveDirection(Vector3 dir) {
		this.moveDirection = dir;
	}

	/**
	 * velocoty.y ist 0 und Spielerstatus ist nicht jump -> idle velocity.y ist
	 * negativ/positiv -> Spielerstatus -> jump
	 */
	private void setPlayerState() {
		if (velocity.y == 0 && velocity.x == 0 && state != playerState.jumping)
			state = playerState.idle;
		else if (velocity.y < -0.1 || velocity.y > 0.1)
			state = playerState.jumping;
		else if ((velocity.x < -0.1 || velocity.x > 0.1) && state == playerState.idle)
			state = playerState.running;
	}

	private void test(List<Rectangle> list) {
		for (Rectangle rect : list) {
			if (this.hitbox.overlaps(rect)) {
				isAboveElement = true;
				this.velocity.x = 0;
				this.velocity.y = 0;
				this.hitbox.y = rect.y + rect.height;
				this.position.y = rect.y + rect.height;
				this.state = playerState.idle;
			} else {
				isAboveElement = false;
				velocity.add(0, GRAVITY, 0);
			}
		}
	}
}
