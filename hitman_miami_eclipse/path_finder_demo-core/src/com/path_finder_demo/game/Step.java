package com.path_finder_demo.game;

import com.badlogic.gdx.math.Vector2;

public class Step {
	private Vector2 position;
	
	public Step(Vector2 position){
		this.position = position;
	}
	public Vector2 getPosition(){
		return new Vector2(position);
	}
}
