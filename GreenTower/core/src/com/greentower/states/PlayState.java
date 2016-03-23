package com.greentower.states;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Value.Fixed;
import com.greentower.GreenTowerGame;
import com.greentower.Int32Point2D;
import com.greentower.Tile;
import com.greentower.TileMap;
import com.greentower.TileMapCamera;
import com.greentower.TileMapGenerator;
import com.greentower.sprites.Player;
import com.greentower.sprites.Player.playerState;

public class PlayState extends State{
	
	private static final int TILE_WIDTH = 64;
	private static final int TILE_HEIGHT = 64;
	public static final int TILES_HORIZONTAL = 20;
	
	private Player player;
	//private Texture bg;
	
	//TODO: For testing purposes
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera cam;
		
	
	private TmxMapLoader maploader;
	private OrthogonalTiledMapRenderer renderer;
	private TiledMap map;
	
	private List<Rectangle> listrect = new ArrayList<Rectangle>();
	
	//Box2d variables for physics and colliders
	private World world;
	private Box2DDebugRenderer b2dr;

	protected PlayState(GameStateManager gsm) {
		super(gsm);	
		//create player
		player = new Player(64,64);
		//set background texture
		//bg = new Texture("bg.png");
		//only need one camera -> derive from state

		font = new BitmapFont();
		
		//create the Map
		createMap();
		
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.position.set( GreenTowerGame.WIDTH / 2, GreenTowerGame.HEIGHT/2,0);
		cam.update();
	}
	
	private void createMap(){
		maploader = new TmxMapLoader();
		//load bottom map
		map = maploader.load("asd.tmx");
		renderer = new OrthogonalTiledMapRenderer(map);
		
		world = new World(new Vector2(0, 0), true);
		b2dr = new Box2DDebugRenderer();
		
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		//import MapObjects
		for(MapObject object: map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
			
			Rectangle rect = ((RectangleMapObject) object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			
			bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() /2);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth() / 2, rect.getHeight() /2);
			
			fdef.shape = shape;
			body.createFixture(fdef);
			
			//Test
			listrect.add(rect);
		
		}		
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
		world.step(1/60f, 6, 2);
		//playerCollision();
		//then everything else
		player.update(dt, this.listrect);
		//update the camera position relative to the player
		
		if(player.getPosition().y > GreenTowerGame.HEIGHT/2 ){
			//camPosYbefore = player.getPosition().y;
			cam.position.set( GreenTowerGame.WIDTH / 2,player.getPosition().y ,0 );
		}
		//TODO - create new platforms here
		

		//update the camera
		renderer.setView(cam);
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
		//draw tileMap
		renderer.render();
		//start packaging
		sb.begin();
		//draw background in the middle of the screen
	//	sb.draw(bg, cam.position.x - cam.viewportWidth / 2, 0);
		//draw the player
		sb.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
		//TODO: For testing purposes
		font.draw(sb, ""+player.state, player.getPosition().x+2, player.getPosition().y+20);
		font.draw(sb, "VelX: "+(int)player.getVelocity().x, player.getPosition().x+2, player.getPosition().y+40);
		font.draw(sb, "VelY: "+(int)player.getVelocity().y, player.getPosition().x+2, player.getPosition().y+60);
		
		//TODO - create Map
		
		ShapeRenderer sh = new ShapeRenderer();
		sh.setAutoShapeType(true);
		sh.begin();
		for(Rectangle rec : listrect){
			sh.rect(rec.x,rec.y,rec.width,rec.height  );
		}
		sh.end();
		
		
		/*for(Platform platform : platforms)
		{
			spritebatch.draw(
					imgPlatform,
					platform.getRect().x,
					platform.getRect().y,
					platform.getRect().width,
					platform.getRect().height);
			
		}*/
		
//		for(int y = 0; y < tilemap.getHeight(); y++)
//		{
//			Tile[] row = tilemap.getRow(y);
//			
//			for(int x = 0; x < tilemap.getWidth(); x++)
//			{
//				Tile tile = row[x];
//				if(tile != null)
//				{
//					Int32Point2D worldPos = tileCamera.tileToWorld(x, y);
//					Int32Point2D screenPos = tileCamera.screenToWorld(worldPos.x, worldPos.y);
//				
//					sb.draw(
//					tile.texture,
//					screenPos.x,
//					screenPos.y,
//					TILE_WIDTH,
//					TILE_HEIGHT);
//				}
//			}
//		}
		
		sb.end();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
	}

}
