package com.greentower;

public class TileMapCamera {

	
	private int mapWidth,
				mapHeight,
				tileSize;

	private Int32Point2D position;
	
	
	public TileMapCamera(int mapWidth, int mapHeight, int tileSize) {
		
		position = new Int32Point2D();
		
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;
		this.tileSize = tileSize;
	}
	
	
	public Int32Point2D worldToScreen(int worldX, int worldY) {
		
		return new Int32Point2D(
				worldX - position.x,
				worldY - position.y);
	}

	public Int32Point2D screenToWorld(int screenX, int screenY) {
		
		return new Int32Point2D(
				screenX + position.x,
				screenY + position.y);
	}
	
	public Int32Point2D worldToTile(int worldX, int worldY) {
		
		return new Int32Point2D(
				worldX / tileSize,
				worldY / tileSize);
	}
	
	public Int32Point2D tileToWorld(int tileX, int tileY) {
		
		return new Int32Point2D(
				tileX * tileSize,
				tileY * tileSize);
	}
	
	public Int32Point2D getPosition() {
		return position;
	}


	public void setPosition(Int32Point2D position) {
		this.position = position;
	}


	public int getMapWidth() {
		return mapWidth;
	}


	public int getMapHeight() {
		return mapHeight;
	}


	public int getTileSize() {
		return tileSize;
	}
}
