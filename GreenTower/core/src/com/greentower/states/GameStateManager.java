package com.greentower.states;

import java.io.IOException;
import java.util.Stack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.data.Highscore;
import com.greentower.sprites.Player;

//handling GameStates here with a stack
public class GameStateManager {
	
	private Stack<State> states;
	public Highscore score;
	
	public GameStateManager(){
		states = new Stack<State>();
		try {
			score = new Highscore();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Player getPlayer() {
		return peek().player;
	}
	
	public void push(State state){
		states.push(state);
	}
	
	public void pop(){
		states.pop();
	}
	
	public State peek() {
		return states.peek();
	}
	
	public void set(State state){
		states.pop();
		states.push(state);
	}
	
	public void update(float dt){
		states.peek().update(dt);
	}
	
	public void render(SpriteBatch sb){
		states.peek().render(sb);
	}

}
