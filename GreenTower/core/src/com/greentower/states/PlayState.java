package com.greentower.states;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
import com.greentower.sprites.Player;

public class PlayState implements Screen {
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	//Player
	public Player player;
	
	//Tiled Map
	private TmxMapLoader maploader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	
	//Box2d Physics
	public static World world;
	private Box2DDebugRenderer b2dr;
	
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
		//add bodies to the world
		createColliders();
		//TODO - Debug Renderer
		b2dr = new Box2DDebugRenderer();
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
		
		game.batch.setProjectionMatrix(gamecam.combined);
		
		//only draw what can be seen
		game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
		//render the TileMap
		renderer.render();
		//render DEBUG
		b2dr.render(world, gamecam.combined);
		//center the Play screen around the Viewport
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		//draw the HUD
		hud.stage.draw();				
	}
	
	protected void handleInput(float dt) {
		if(Gdx.input.isKeyPressed(Keys.SPACE))
		{
			if(player.getBody().getLinearVelocity().y == 0)
				player.getBody().setLinearVelocity(player.getBody().getLinearVelocity().x, 100f);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				player.getBody().applyForceToCenter(-800f, 0f, true);
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				player.getBody().applyForceToCenter(800f, 0f, true);
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
		
		//physics calculations
		world.step(dt, 6, 2);
		//move camera with player
		if(player.getBody().getPosition().y > (GreenTowerGame.V_HEIGHT/2)  / GreenTowerGame.PPM) {
			gamecam.position.set((GreenTowerGame.V_WIDTH / 2)  / GreenTowerGame.PPM, player.getBody().getPosition().y, 0);
		}
		//update the game camera
		gamecam.update();
		//render the game
		renderer.setView(gamecam);
		
		if(checkForGoalCollision())
			onGoalReached();
		
		
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
