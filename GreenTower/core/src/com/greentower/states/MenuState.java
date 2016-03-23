package com.greentower.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.greentower.GreenTowerGame;

/**
 * This Class representation the MainMenu. First du have to press Space, and
 * then you can choice between start game and highscorelist. Start game -> start
 * the game highscorelist -> show the Highscore
 * 
 * @author Yangus
 *
 */
public class MenuState extends State {

	private Texture background;
	private Texture pressSpaceBtn;
	private Sprite startgameBtn;
	private Sprite showHighscoreBtn;
	private Color isSelected;
	// titlescreen with "Press SPACE
	private boolean IsOntitlescreen;
	// titlescreen with the choice
	// true -> start game
	// False -> showhighscore
	private boolean ChoiceStartGame;

	public MenuState(GameStateManager gsm) {
		super(gsm);
		this.background = new Texture("bg.png");
		this.pressSpaceBtn = new Texture("titelscreen.png");
		this.startgameBtn = new Sprite(new Texture("startBtn.png"));
		this.startgameBtn.setPosition((GreenTowerGame.WIDTH / 2) - (pressSpaceBtn.getWidth() / 2),GreenTowerGame.HEIGHT / 2);
		this.showHighscoreBtn = new Sprite(new Texture("highscoreBtn.png"));
		this.showHighscoreBtn.setPosition((GreenTowerGame.WIDTH / 2) - (pressSpaceBtn.getWidth() / 2),(GreenTowerGame.HEIGHT / 2) - 100);
		this.isSelected = new Color(0, 1f, 0, 0.5f);
		this.startgameBtn.setColor(this.isSelected);
		//Für die Menü auswahl
		this.IsOntitlescreen = true;
		this.ChoiceStartGame = true;

	}

	@Override
	public void handleInput() {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if (this.IsOntitlescreen) {
				this.IsOntitlescreen = !this.IsOntitlescreen;
			} else {
				if (this.ChoiceStartGame) {
					gsm.set(new PlayState(gsm));
					dispose();
				}else{
					gsm.set(new HighscoreState(gsm,false));
					dispose();
				}
			}
		}

		if (!this.IsOntitlescreen && (Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.DOWN))) {
			this.ChoiceStartGame = !this.ChoiceStartGame;
			if (this.ChoiceStartGame) {
				this.startgameBtn.setColor(this.isSelected);
				this.showHighscoreBtn.setColor(1, 1, 1, 1);
			} else {
				this.showHighscoreBtn.setColor(this.isSelected);
				this.startgameBtn.setColor(1, 1, 1, 1);
			}
		}

	}

	@Override
	public void update(float dt) {
		// always check input first
		handleInput();
	}

	@Override
	public void render(SpriteBatch sb) {
		// open "container"
		sb.begin();
		sb.draw(background, (GreenTowerGame.WIDTH / 4), (GreenTowerGame.HEIGHT) - (GreenTowerGame.HEIGHT / 4));
		if (this.IsOntitlescreen) {
			sb.draw(this.pressSpaceBtn, (GreenTowerGame.WIDTH / 2) - (pressSpaceBtn.getWidth() / 2),
					GreenTowerGame.HEIGHT / 2);
		} else {
			this.startgameBtn.draw(sb);
			this.showHighscoreBtn.draw(sb);
		}
		sb.end();
	}

	@Override
	public void dispose() {
		background.dispose();
		pressSpaceBtn.dispose();
	}

}
