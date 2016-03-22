package com.greentower;

import java.util.List;

public class TileMapGenerator {

	private TileMap map;
	
	public TileMapGenerator(TileMap map) { 
		
		this.map = map;
		
		fillMap();
	}
	
	private void fillMap()
	{
		List<Tile[]> rows = map.getRows();
		rows.clear();
		
		for(int y = 0; y < map.getHeight(); y++)
		{
			rows.add(generateRow());
		}
	}
	
	public void replaceTopRowAtBottom()
	{
		List<Tile[]> rows = map.getRows();
		
		if(rows.size() > 0)
		{
			rows.remove(0);
			
			rows.add(generateRow());
		}
	}
	
	private Tile[] generateRow()
	{
		Tile[] row = new Tile[map.getWidth()];
		
		if(row.length > 0)
		{
			row[0] = new Tile();
		
			for(int x = 1; x < row.length - 1; x++)
			{
				//TODO: create tiles
			}
			
			row[row.length - 1] = new Tile();
		}
		
		return row;
	}
}
