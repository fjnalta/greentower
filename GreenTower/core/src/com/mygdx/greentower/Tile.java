package com.mygdx.greentower;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

public class Tile {
	
	public enum Type {
		solid,
		nonsolid,
		platform,
	}
	private Type type;
	private Texture texture;
	
	//Default: metal block
	public Tile() {
		type = Type.solid;
		texture = new Texture("floor_tile-01.png");
	}
	
	public Tile(Type type, String texture) {
		this.type = type;
		this.texture = new Texture(texture);
	}
}
