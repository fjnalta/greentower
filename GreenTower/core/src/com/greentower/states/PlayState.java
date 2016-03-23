package com.greentower.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.greentower.GreenTowerGame;
import com.greentower.Int32Point2D;
import com.greentower.Tile;
import com.greentower.TileMap;
import com.greentower.TileMapCamera;
import com.greentower.sprites.Jumper;
import com.greentower.sprites.Player;

public class PlayState extends State{
	
	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 64;
	public static final int TILES_HORIZONTAL = 20;
	
	private Texture bg;
	
	//TODO: For testing purposes
	private BitmapFont font;
	private OrthographicCamera cam;
	
	
	public TileMap tilemap;
	private TileMapCamera tileCamera;
	
	//Items
	public Jumper[] jumpers;

	protected PlayState(GameStateManager gsm) {
		super(gsm);	
		//create player
		player = new Player(64,64);
		//set background texture
		bg = new Texture("bg.jpg");
		//only need one camera -> derive from state

		font = new BitmapFont();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set( GreenTowerGame.WIDTH / 2, GreenTowerGame.HEIGHT/2,0);
		cam.update();
		
		//TODO - create the map?!?
		tilemap = new TileMap(
				TILES_HORIZONTAL,
				(Gdx.graphics.getBackBufferHeight() / TILE_HEIGHT) + 1);
		tileCamera = new TileMapCamera(tilemap.getWidth(), tilemap.getHeight(), TILE_HEIGHT);
	}

	/**
	 * Handling all the Player inputs here
	 * 
	 */
	@Override
	protected void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			player.jump();
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				player.setMoveDirection(new Vector3(-1,0,0));
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				player.setMoveDirection(new Vector3(1,0,0));
			}
		} else {
			player.setMoveDirection(new Vector3(0,0,0));
		}
		
	}
	
//	protected void playerCollision() {
//		Tile[] tiles = tilemap.getSurroundingTiles(player.getPosition().x, player.getPosition().y, 4);
//		for(Tile tile : tiles) {
//			player.
//		}
//	}
	
	
	float camPosYbefore = 0;
	/**
	 * Updates the game logic.
	 * 
	 * @param delta the time since the last update call.
	 */
	@Override
	public void update(float dt) {
		//always handle the input first
		handleInput();
		//then collision
		//playerCollision();
		//then everything else
		player.update(dt);
		//update the camera position relative to the player
		
		if(player.getPosition().y > GreenTowerGame.HEIGHT/2 &&(camPosYbefore < player.getPosition().y)){
			camPosYbefore = player.getPosition().y;
			cam.position.set( GreenTowerGame.WIDTH / 2,player.getPosition().y ,0 );
		}
		//TODO - create new platforms here
		
//		if game ends open HighscoreState
		if (player.getPosition().y < (cam.position.y - cam.viewportHeight / 2)) {
			//gsm.set(new HighscoreState(gsm,true));
			//dispose();
		}
		//update the camera
		cam.update();
	}

	
	/**
	 * Packages everything into a Spritebatch and renders it to the screen
	 * 
	 * @param sb Spritebatch
	 */
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		//start packaging
		sb.begin();
		//draw background in the middle of the screen
		sb.draw(bg, cam.position.x - cam.viewportWidth / 2, 0);
		//draw the player
		sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
		//TODO: For testing purposes
		font.draw(sb, ""+player.state, player.getPosition().x+2, player.getPosition().y+20);
		font.draw(sb, "VelX: "+(int)player.getVelocity().x, player.getPosition().x+2, player.getPosition().y+40);
		font.draw(sb, "VelY: "+(int)player.getVelocity().y, player.getPosition().x+2, player.getPosition().y+60);
		
		//TODO - create Map
		
		/*for(Platform platform : platforms)
		{
			spritebatch.draw(
					imgPlatform,
					platform.getRect().x,
					platform.getRect().y,
					platform.getRect().width,
					platform.getRect().height);
			
		}*/
		
		for(int y = 0; y < tilemap.getHeight(); y++)
		{
			Tile[] row = tilemap.getRow(y);
			
			for(int x = 0; x < tilemap.getWidth(); x++)
			{
				Tile tile = row[x];
				if(tile != null)
				{
					Int32Point2D worldPos = tileCamera.tileToWorld(x, y);
					Int32Point2D screenPos = tileCamera.screenToWorld(worldPos.x, worldPos.y);
				
					sb.draw(
					tile.texture,
					screenPos.x,
					screenPos.y,
					TILE_WIDTH,
					TILE_HEIGHT);
				}
			}
		}
		
		sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
