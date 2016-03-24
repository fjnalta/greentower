package com.greentower.states;

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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.greentower.GreenTowerGame;
import com.greentower.Hud;

public class PlayState implements Screen {
	
	private static final float PIXEL_PER_METER = 100f;
	
	private GreenTowerGame game;
	private OrthographicCamera gamecam;
	private Viewport gamePort;
	private Hud hud;
	
	//Player
	private Body player;
	
	//Tiled Map
	private TmxMapLoader maploader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	
	//Box2d Physics
	private World world;
	private Box2DDebugRenderer b2dr;

	public PlayState(GreenTowerGame game){
		this.game = game;
		gamecam = new OrthographicCamera();
		gamePort = new FitViewport(GreenTowerGame.V_WIDTH, GreenTowerGame.V_HEIGHT, gamecam);
		hud = new Hud(game.batch);
		//create the TileMap
		createMap();
		//center the camera around the viewport
		gamecam.position.set(gamePort.getScreenWidth() / 2, gamePort.getScreenHeight() / 2, 0);
	
		//BOX2dPhysics
		//Create the physics world
		world = new World(new Vector2(0, -9.81f), true);
		
		//create player
		player = createPlayer();
		//add bodies to the world
		createColliders();
		//TODO - Debug Renderer
		b2dr = new Box2DDebugRenderer();
	}
	
	private Body createPlayer() {
		BodyDef def = new BodyDef();
		def.position.set(100, 100);
		def.type = BodyType.DynamicBody;
		Body box = world.createBody(def);
		box.setGravityScale(2f);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(10, 10);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		box.createFixture(fdef);
		return box;
}
	
	private void createColliders(){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		//define fixture first
		FixtureDef fdef = new FixtureDef();
		//define body
		Body body;
		
		//import MapObjects
		for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() /2);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
			
			fdef.shape = shape;
			body.createFixture(fdef);
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
			if(player.getLinearVelocity().y == 0)
				//player.applyForceToCenter(0, 10000f, true);
				player.setLinearVelocity(player.getLinearVelocity().x, 200000f);
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT) || Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				player.applyForceToCenter(-550, 0, true);
			}
			if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				player.applyForceToCenter(550, 0, true);
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)){
			game.setScreen(new MenuState(this.game));
		}
	}
	
	/**
	 * Handling all the Player inputs here
	 * 
	 */
	public void update(float dt){
		handleInput(dt);
		this.hud.update();
		//physics calculations
		world.step(dt, 6, 2);
		
		if(player.getPosition().y > GreenTowerGame.V_HEIGHT/2 ){
			//camPosYbefore = player.getPosition().y;
			gamecam.position.set( GreenTowerGame.V_WIDTH / 2,player.getPosition().y ,0 );
		}
		//update the game camera
		gamecam.update();
		//render the game
		renderer.setView(gamecam);
	}
	
	private void createMap(){
		maploader = new TmxMapLoader();
		map = maploader.load("SciFiStage.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);		
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
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		map.dispose();
		world.dispose();
		
	}
}
