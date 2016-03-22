package com.greentower;

/**
 * Provides the ability to convert between 
 * tile, world and screen coordinates.
 */
public class TileMapCamera {

	
	private int mapWidth,
				mapHeight,
				tileSize;

	private Int32Point2D position;
	
	
	/**
	 * Initializes a new instance of the {@link TileMapCamera} class.
	 * 
	 * @param mapWidth the width of the map, in tiles.
	 * @param mapHeight the height of the map, in tiles.
	 * @param tileSize the size of a tile, in pixel.
	 * 
	 * @throws IllegalArgumentException if the {@code mapWidth} or
	 * {@code mapHeight} parameter is smaller than zero, or the 
	 * {@code tileSize} parameter is smaller or equal to zero.
	 */
	public TileMapCamera(int mapWidth, int mapHeight, int tileSize) {
		
		if(mapWidth < 0)
			throw new IllegalArgumentException("Parameter mapWidth < 0");
		
		else if(mapHeight < 0)
			throw new IllegalArgumentException("Parameter mapHeight < 0");
		
		else if(tileSize <= 0)
			throw new IllegalArgumentException("Parameter tileSize < 0");
		
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.tileSize = tileSize;
		
		position = new Int32Point2D();
		
		
	}
	
	/**
	 * Converts the following world coordinates into screen
	 * coordinates.
	 * 
	 * @param worldX the x-component.
	 * @param worldY the y-component.
	 * @return a {@link Int32Point2D} that contains the screen coordinates.
	 */
	public Int32Point2D worldToScreen(int worldX, int worldY) {
		
		return new Int32Point2D(
				worldX - position.x,
				worldY - position.y);
	}

	/**
	 * Converts the following screen coordinates into world coordinates.
	 * 
	 * @param screenX the x-component.
	 * @param screenY the y-componen.
	 * @return a {@link Int32Point2D} that contains the world coordinates. 
	 */
	public Int32Point2D screenToWorld(int screenX, int screenY) {
		
		return new Int32Point2D(
				screenX + position.x,
				screenY + position.y);
	}
	
	/**
	 * Converts the following world coordinates into tile coordinates.
	 * 
	 * @param worldX the x-component.
	 * @param worldY the y-component.
	 * @return a {@link Int32Point2D} that contains the tile coordinates.
	 */
	public Int32Point2D worldToTile(int worldX, int worldY) {
		
		return new Int32Point2D(
				worldX / tileSize,
				worldY / tileSize);
	}
	
	/**
	 * Converts the following tile coordinates into world coordinates.
	 * 
	 * @param tileX the x-component.
	 * @param tileY the y-component.
	 * @return a {@link Int32Point2D} that contains the world coordinates.
	 */
	public Int32Point2D tileToWorld(int tileX, int tileY) {
		
		return new Int32Point2D(
				tileX * tileSize,
				tileY * tileSize);
	}
	
	/**
	 * Gets the position of the current {@link TileMapCamera}.
	 * 
	 * @return a {@link Int32Point2D}.
	 */
	public Int32Point2D getPosition() {
		return position;
	}

	/**
	 * Sets the position of the current {@link TileMapCamera}.
	 * 
	 * @param position the new position.
	 * 
	 * @throws IllegalArgumentException if the parameter is null.
	 */
	public void setPosition(Int32Point2D position) {
		
		if(position == null)
			throw new IllegalArgumentException("Parameter null: position.");
		
		this.position = position;
	}


	/**
	 * Gets the width of the map associated with the current
	 * {@link TileMapCamera}.
	 * 
	 * @return an integer value.
	 */
	public int getMapWidth() {
		return mapWidth;
	}

	/**
	 * Gets the height of the map associated with the current
	 * {@link TileMapCamera}.
	 * 
	 * @return an integer value.
	 */
	public int getMapHeight() {
		return mapHeight;
	}


	/**
	 * Gets the size of a square tile associated with the current
	 * {@link TileMapCamera}.
	 * 
	 * @return an integer value.
	 */
	public int getTileSize() {
		return tileSize;
	}
}
