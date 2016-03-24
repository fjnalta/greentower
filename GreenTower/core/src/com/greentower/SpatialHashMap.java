package com.greentower;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SpatialHashMap<TValue> {
	
	
	private Map<Int32Point2D, TValue> map;
	
	private int cellSize;
	
	
	public SpatialHashMap(int cellSize)
	{
		if(cellSize <= 0)
			throw new IllegalArgumentException("The cellSize must be bigger than 0.");
		
		map = new HashMap<Int32Point2D, TValue>();
		
		this.cellSize = cellSize;
	}
	
	
	public int getCellSize()
	{
		return cellSize;
	}
	
	public boolean contains(int worldX, int worldY)
	{
		Int32Point2D point = new Int32Point2D(
				worldX / cellSize,
				worldY / cellSize);
		
		return map.containsKey(point);
	}
	
	public TValue get(int worldX, int worldY)
	{
		Int32Point2D point = new Int32Point2D(
				worldX / cellSize,
				worldY / cellSize);
		
		return map.get(point);
	}
	
	public void set(int worldX, int worldY, TValue value)
	{
		Int32Point2D point = new Int32Point2D(
				worldX / cellSize,
				worldY / cellSize);
		
		if(map.containsKey(point))
			map.replace(point, value);
		else
			map.put(point, value);
	}

	
	public static SpatialHashMap<RectangleMapObject> FromMapObjects(
			Array<RectangleMapObject> array,
			int cellSize)
	{
		SpatialHashMap<RectangleMapObject> hashMap = 
				new SpatialHashMap<RectangleMapObject>(cellSize);
		
		for(RectangleMapObject mapObject : array)
		{
			Rectangle rect = mapObject.getRectangle();
			
			
			for(int x = 0;
					x < rect.width;
					x += cellSize)
			{
				for(int y = 0;
						y < rect.height;
						y += cellSize)
				{
					hashMap.set(
							(int)rect.x + x,
							(int)rect.y + y,
							mapObject);
				}
			}
		}
		
		
		return hashMap;
	}
	
	
	/**
	 * Private class which serves as key for the map.
	 */
	private static class Int32Point2D
	{
		int x,
			y;
		
		/**
		 * Initializes a new instance of the {@link Int32Point2D} class.
		 * @param x
		 * @param y
		 */
		public Int32Point2D(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int hashCode() {
			
			 int hash = 17;
	         
	         hash = hash * 23 + Integer.hashCode(x);
	         hash = hash * 23 + Integer.hashCode(y);
	         
	         return hash;
		}
		
		@Override
		public boolean equals(Object obj) {
			
			if(obj instanceof SpatialHashMap.Int32Point2D)
			{
				Int32Point2D point = (Int32Point2D)obj;
				
				return point.x == this.x
					&& point.y == this.y;
			}
			
			return false;
		}

	}

}
