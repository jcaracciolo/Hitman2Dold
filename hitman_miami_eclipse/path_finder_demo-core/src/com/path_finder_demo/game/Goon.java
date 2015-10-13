package com.path_finder_demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;

public class Goon implements Mover{
	private float x;
	private float y;
	private Step current_step = null;
	public  Path current_path = null;
	private PathFinder path_finder;
	private boolean is_moving = false;
	private static float SPEED = 2.0f;
	private Model model;
	private Rectangle hit_box;
	private Player player;
	
	public Goon(float x, float y, PathFinder path_finder, Model model){
		
		this.x = x;
		this.y = y;
		this.path_finder = path_finder;
		this.model = model;
		this.hit_box = new Rectangle(x,y,1,1);
	}
	public void move(float xp,float yp){
		
		
		Path aux = path_finder.findPath((int)x,(int)y,(int)xp, (int)yp);
		if (aux != null && aux.equals(current_path)){
			return;
		}
		else{
			current_path = aux;
		}
		if (current_path != null && current_path.hasNextStep()) {
			is_moving = true;
			current_step = current_path.nextStep();	
			
		}
		else {
			is_moving = false;
			current_step = null;
			current_path = null;
		}
	}
	public void update(){
		hit_box.setPosition(x, y);
		
		if (!is_moving){
			return;
		}
		if (current_step!=null && (Math.abs((int)current_step.getX()-(int)x) > 1 || Math.abs((int)current_step.getY() -(int)y) > 1) ){
			walkTowards(current_step);
			//System.out.println(x + " " +y);
			return;
		}
		else{
			current_step = null;
		}
		if (current_step == null && current_path != null && current_path.hasNextStep()){
			current_step = current_path.nextStep();
//			walkTowards(current_step);
		}
		else if (current_step == null){
			is_moving = false;
			current_path = null;
			return;
		}
		walkTowards(current_step);
	}
	public void followPlayer(Player player){
		this.player = player;
	}
	public boolean isMoving(){
		return is_moving;
	}
	public void walkTowards (Step s){
		System.out.println(x + " " +y);
		float distance = getDistance(x,y,s.getX(),s.getY());
		float xv = getUnitVector(s.getX() - x, distance);
		float yv = getUnitVector(s.getY() - y, distance);
		System.out.println("x: " + xv + " y: "+ yv);
		y = y + (yv * SPEED);
		x = x + (xv * SPEED);
//		y = s.getY();
//		x = s.getX();
//		y = step_x;
//		x = step_y;
		
		model.update();	
	}
	public float getX(){
		return x;
	}
	public float getY(){
		return y;
	}
	private float getDistance(float x1,float y1,float x2,float y2){
//		return (float)(Math.sqrt(Math.pow(x1 - x2,2) + Math.pow(y1 - y2, 2)));
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}
	private float getUnitVector(float s, float distance){
		return (s /distance);
	}
	
}
