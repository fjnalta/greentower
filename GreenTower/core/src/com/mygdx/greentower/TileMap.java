package com.mygdx.greentower;

import java.util.LinkedList;
import java.util.List;

import states.PlayState;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.greentower.Tile.Type;

public class TileMap {

	private int width,
				height;
	
	private List<Tile[]> rows;

	
	

	public TileMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		
		rows = new LinkedList<Tile[]>();
	}
	

	public Tile metal(int gridX, int gridY) {
		return rows.get(gridY)[gridX] = new Tile();
	}
	
	/**
	 * Returns all tiles at position in the given proximity.
	 * (3 width = 3x3 area of tiles, 4 width = 4x4 area)
	 * @param x x-position (absolute pixels)
	 * @param y y-position (absolute pixels)
	 * @param width Size of the area to check for tiles. Minimum 3.
	 * @return
	 */
	public Tile[] getSurroundingTiles(float xPos, float yPos, int width) {
		int size = Math.max(width, 3);
		int x = (int)Math.max((xPos/64)-(size/2), 0);
		int y = (int)Math.max((yPos/64)-(size/2), 0);
		Tile[] tiles = new Tile[size*size];
		
		for(int i = 0; i < size; i++) {
			if(y+i >= PlayState.TILES_HORIZONTAL)
				break;
			
			Tile[] row = rows.get(y+i);
			for(int k = 0; k < size; k++) {
				if(x+k >= row.length)
					break;
				tiles[i*size+k] = row[x+k];
			}
		}
		
		return tiles;
	}
	
	public Tile[] getRow(int y)
	{
		return rows.get(y);
	}
	
	public List<Tile[]> getRows() {
		return rows;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
