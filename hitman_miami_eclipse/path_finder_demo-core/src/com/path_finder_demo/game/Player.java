package com.path_finder_demo.game;

import com.badlogic.gdx.math.Rectangle;

public class Player {
	private int posx;
	private int posy;
	private PlayerModel player_model;
	private static double SPEED = 2.0;
	private LevelMap map;
	private Rectangle hit_box;
	
	public boolean move(double x, double y, boolean running){
		
		int speed_factor = 1;
		if (running)
			speed_factor = 2;
		int aux_posx = posx +(int) (x * SPEED * speed_factor);
		int aux_posy = posy +(int) (y * SPEED * speed_factor);
		hit_box.setPosition(aux_posx,aux_posy);
		
		if (!map.isValid(hit_box)){
			hit_box.setPosition(posx, posy);
			return false;
		}
		posx = aux_posx;
		posy = aux_posy;
		player_model.updateAnimation();
		return true;
	}
	public Player(Integer x, Integer y, PlayerModel player_model, LevelMap map){
		this.posx = x;
		this.posy = y;
		this.player_model = player_model;
		this.hit_box = new Rectangle(x,y,16,12);
		this.map = map;
	}
	public int getX(){
		return posx;
	}
	public int getY(){
		return posy;
	}
	
}
