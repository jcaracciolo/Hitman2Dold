/*
 *@author Tomas Raies
 *@date   13 de oct. de 2015
 */

package com.path_finder_demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Character implements Movable {
	protected static final float NORMAL_SPEED = 60f;
	protected static final float RUNNING_SPEED = 65f;
	protected Vector2 direction;
	protected Rectangle hitBox;
	protected Model model;
	protected LevelMap map;
	protected boolean running;
	protected boolean isMoving = false;
	
	public Character(Rectangle hitBox, LevelMap map, Model model){
		this.direction = new Vector2();
		this.map = map;
		this.model = model;
		this.hitBox = hitBox;
		this.running = false;
	}
	public boolean isMoving(){
		return isMoving;
	}
	@Override
	public Vector2 getPosition() {
		return hitBox.getPosition(new Vector2());
	}

	@Override
	public Vector2 getDirection() {
		return new Vector2(direction);
	}

	@Override
	public boolean move(Vector2 direction) {
		if (direction.isZero()){
			return false;
		}
		this.direction.set(direction.nor());
		return true;
	}
	public boolean move(Vector2 direction, boolean running) {
		
		move(direction);
		this.running = running;
		return true;
	}

	@Override
	public abstract boolean moveTo(Vector2 position);
	
	@Override
	public void update(){
		if (!isMoving || direction.isZero()){
			//System.out.println(isMoving);
			return;
		}
		
		float speed;
		if (running){
			speed = RUNNING_SPEED;
		}
		else {
			speed = NORMAL_SPEED;
		}
		Vector2 currPosition = new Vector2();
		currPosition = hitBox.getPosition(currPosition);
		currPosition = currPosition.add(direction.scl(speed * Gdx.graphics.getDeltaTime()));
		Rectangle currHitBox = new Rectangle(hitBox);
		currHitBox.setPosition(currPosition);
		hitBox = currHitBox;
		model.update();
//		if (map.isValid(currHitBox)){
//			hitBox = currHitBox;
//			model.update();
//		}
//		else {
//			direction.setZero();
//			isMoving = false;
//		}
		
	}

}
