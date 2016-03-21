package com.mygdx.greentower;

import java.util.LinkedList;
import java.util.List;

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
