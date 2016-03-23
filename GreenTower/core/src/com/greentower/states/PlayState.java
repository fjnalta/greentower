package com.greentower.states;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.greentower.GreenTowerGame;
import com.greentower.sprites.Player;


/**
 * The {@link State} that contains the main game logic.
 */
public class PlayState extends State {
	
	public static final int TILES_HORIZONTAL = 20;
	
	private Player player;
	
	private BitmapFont font;
	private OrthographicCamera camera;
		
	private TiledMap map;
	private TmxMapLoader maploader;
	
	private OrthogonalTiledMapRenderer mapRenderer;
	
	private List<Rectangle> listrect = new ArrayList<Rectangle>();
	
	//Box2d variables for physics and colliders
	private World world;

	
	/**
	 * Initializes a new instance of the {@link PlayState} class.
	 * 
	 * @param gamestateManager the {@link GameStateManager} of the state.
	 */
	protected PlayState(GameStateManager gamestateManager) {
		super(gamestateManager);	
		
		player = new Player(64, 64);

		font = new BitmapFont();
		
		createMap("asd.tmx");
		
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.position.set(
				GreenTowerGame.WIDTH / 2,
				GreenTowerGame.HEIGHT / 2,
				0);
		
		camera.update();
	}
	
	/**
	 * Creates the main map.
	 * 
	 * @param mapFileName the name of the .tmx file that contains the map data.
	 */
	private void createMap(String mapFileName) {
		maploader = new TmxMapLoader();
		//load bottom map
		map = maploader.load(mapFileName);
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		
		world = new World(new Vector2(0, 0), true);
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//import MapObjects
		for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2);
			
			fdef.shape = shape;
			body.createFixture(fdef);
			
			//Test
			listrect.add(rect);
		
		}		
	}

	
	
	/**
	 * Updates the game logic.
	 * 
	 * @param delta the time since the last update call.
	 */
	@Override
	public void update(float dt) {
		//always handle the input first
		handleInput();
		
		//world.step(1 / 60f, 6, 2);
		//playerCollision();
		//then everything else
		player.update(dt);
		//update the camera position relative to the player
		
		if(player.getPosition().y > GreenTowerGame.HEIGHT / 2){
			//camPosYbefore = player.getPosition().y;
			camera.position.set(GreenTowerGame.WIDTH / 2, player.getPosition().y, 0);
		}

		//update the camera
		mapRenderer.setView(camera);
		camera.update();
	}
	
	/**
	 * Handling all the Player inputs here.
	 */
	@Override
	protected void handleInput() {
		
	}

	
	/**
	 * Packages everything into a {@link SpriteBatch} and renders it to the
	 * screen.
	 * 
	 * @param spritebatch the {@link SpriteBatch} used for drawing.
	 */
	@Override
	public void render(SpriteBatch spritebatch) {
		spritebatch.setProjectionMatrix(camera.combined);
		
		//draw tileMap
		mapRenderer.render();
		//start packaging
		
		spritebatch.begin();
		
		player.draw(spritebatch);
		
		//TODO: For testing purposes
		//font.draw(spritebatch, ""+player.state, player.getPosition().x+2, player.getPosition().y+20);
		//font.draw(spritebatch, "VelX: "+(int)player.getVelocity().x, player.getPosition().x+2, player.getPosition().y+40);
		//font.draw(spritebatch, "VelY: "+(int)player.getVelocity().y, player.getPosition().x+2, player.getPosition().y+60);
		
		spritebatch.end();
	}

	/**
	 * Frees unmanaged resources.
	 */
	@Override
	public void dispose() {
		mapRenderer.dispose();
		font.dispose();
		map.dispose();
	}

}
