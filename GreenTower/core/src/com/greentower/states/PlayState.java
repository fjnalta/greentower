package com.greentower.states;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greentower.GreenTowerGame;
import com.greentower.Hud;
import com.greentower.sprites.Jumper;
import com.greentower.sprites.Player;
import com.greentower.sprites.Player.playerState;

public class PlayState implements Screen {
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	//Player
	public Player player;
	public Jumper jumper;
	
	//Tiled Map
	private TmxMapLoader maploader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	
	//Box2d Physics
	public static World world;
//	private Box2DDebugRenderer b2dr;
	
	private List<Rectangle> goalRects;

	private String levelName;
	
	public PlayState(GreenTowerGame game, String levelName){
		
		this.game = game;
		this.levelName = levelName;
				
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH / GreenTowerGame.PPM, GreenTowerGame.V_HEIGHT / GreenTowerGame.PPM, gamecam);
		hud = new Hud(game.batch);
		//create the TileMap
		createMap();
		//center the camera around the viewport
		gamecam.position.set(gamePort.getScreenWidth() / 2, gamePort.getScreenHeight() / 2, 0);
	
		//BOX2dPhysics
		//Create the physics world
		world = new World(new Vector2(0, -100f), true);
		
		//create player
		player = new Player();
		//create jumper
		jumper = new Jumper(100f, 100f, this);
		//add bodies to the world
		createColliders();
		//TODO - Debug Renderer
//		b2dr = new Box2DDebugRenderer();
	}
	
	private void createMap(){
		maploader = new TmxMapLoader();
		map = maploader.load(levelName + ".tmx");
		renderer = new OrthogonalTiledMapRenderer(map,1 / GreenTowerGame.PPM);		
	}
	
	private void createColliders(){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		//define fixture first
		FixtureDef fdef = new FixtureDef();
		
		//import MapObjects
		for(MapObject object: map.getLayers().get("platform").getObjects().getByType(RectangleMapObject.class)){
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			
			bdef.position.set((rect.getX() + rect.getWidth() / 2) / GreenTowerGame.PPM, (rect.getY() + rect.getHeight() /2) / GreenTowerGame.PPM);
			
			Body body = world.createBody(bdef);
			shape.setAsBox((rect.getWidth() / 2) / GreenTowerGame.PPM, (rect.getHeight() / 2) / GreenTowerGame.PPM);
			
			fdef.shape = shape;
			body.createFixture(fdef);
		}
		
		//import goal rectangles
		goalRects = new ArrayList<Rectangle>();
		for(MapObject object : map.getLayers().get("goal").getObjects().getByType(RectangleMapObject.class))
		{
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			
			goalRects.add(rect);
		}
		
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		update(delta);
		//wipe the screen
		//game.batch.setProjectionMatrix(gamecam.combined);
		//render the TileMap
		renderer.render();
		
		renderObjects(delta);
		//only draw what can be seen
		//game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		//render DEBUG
//		b2dr.render(world, gamecam.combined);
		//center the Play screen around the Viewport
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		//draw the HUD
		hud.stage.draw();				
	}
	
	private void renderObjects(float dt) {
		game.batch.begin();
		Rectangle rect = player.getPlayerRect();
		float offset = 0;
		if(player.getBody().getPosition().y > (GreenTowerGame.V_HEIGHT/2)  / GreenTowerGame.PPM)
			offset = (player.getBody().getPosition().y - gamePort.getWorldHeight() / 2)*10;
		//
		game.batch.draw(player.currentFrame, rect.x-32, rect.y-29-offset, 0, 0, 100, 100, 0.6f, 0.6f, 0);
		game.batch.draw(jumper.currentFrame,jumper.position.x-16,jumper.position.y-offset);
		game.batch.end();
	}
	
	protected void handleInput(float dt) {
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if(player.state != playerState.jumping)
				player.getBody().applyForceToCenter(0, 4000f, true);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				if(player.state == playerState.jumping)
					player.getBody().applyForceToCenter(-1800f, 0f, true);
				else
					player.getBody().applyForceToCenter(-1800f, 0f, true);
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				if(player.state == playerState.jumping)
					player.getBody().applyForceToCenter(1800f, 0f, true);
				else
					player.getBody().applyForceToCenter(1800f, 0f, true);
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			backToMenu();
		} else {
			player.getBody().setLinearVelocity(0, player.getBody().getLinearVelocity().y);
		}
	}
	
	/**
	 * Handling all the Player inputs here
	 * 
	 */
	public void update(float dt){
		
		handleInput(dt);
		
		jumper.update(dt);
		
		//physics calculations
		world.step(dt, 6, 2);
		//move camera with player
		if(player.getBody().getPosition().y > (GreenTowerGame.V_HEIGHT/2)  / GreenTowerGame.PPM) {
			gamecam.position.set(gamePort.getWorldWidth() / 2, Math.max(player.getBody().getPosition().y, gamePort.getWorldHeight() / 2), 0);
		}
		//update the game camera
		gamecam.update();
		//render the game
		renderer.setView(gamecam);
		
		if(checkForGoalCollision())
			onGoalReached();
		
		player.update(dt);
		
		hud.update(dt);
	}
	
	/**
	 * Checks whether the player has reached the goal.
	 * @return
	 */
	private boolean checkForGoalCollision()
	{
		Rectangle playerRect = player.getPlayerRect();
		
		for(Rectangle rect : goalRects)
		{
			if(Intersector.intersectRectangles(playerRect, rect, new Rectangle()))
				return true;
		}
		
		return false;
	}
	
	private void onGoalReached()
	{
		game.setScreen(
				new VictoryScreen(game, levelName, hud.getPlayTime()));
	}
	
	private void backToMenu()
	{
		game.setScreen(new MenuState(this.game)); //exit screen
	}
	
	

	@Override
	public void resize(int width, int height) {
		//update when the window is resized
		gamePort.update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		//this.dispose();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		renderer.dispose();
		world.dispose();
	}
}
