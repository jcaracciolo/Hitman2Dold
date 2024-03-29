package com.path_finder_demo.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

class ControlHandler implements InputProcessor {
	private static int MOVE_LEFT = 0;
	private static int MOVE_UP = 1;
	private static int MOVE_RIGHT = 2;
	private static int MOVE_DOWN = 3;
	private static double SIN45 = 0.7071;
	
	private boolean move_left;
	private boolean move_right;
	private boolean move_up;
	private boolean move_down;
	private boolean move_run;
	private int mouse_x;
	private int mouse_y;
	private boolean mouse_click;
	private Player player;
	private LevelMap map;
	private Set<Goon> goonSet;
	
	public ControlHandler(Player player, Set<Goon> goonSet, LevelMap map){
		this.player = player;
		this.map = map;
		this.goonSet = goonSet;
	}
	public void update(){
		float x,y;
    	x=y=0f;
		if (move_left)
			x = -1;
		if (move_right)
			x = 1;
		if (move_up)
			y = 1;
		if (move_down)
			y = -1;
		if (y!=0 && x!=0){
			x *= SIN45;
			y *= SIN45;
        }
		if ((x!=0 || y!=0)&& player!= null){
        	player.move(new Vector2(x,y), move_run);
		}
		else {
			player.stopMoving();
		}
//		if (mouse_click){
//			mouse_click = false;
//			for(Goon g: goonSet)
//				g.moveTo(new Vector2(mouse_x, mouse_y),move_run);
//		}
	}
	
	
	@Override
    public boolean keyUp(int keycode) {
		if(keycode == Input.Keys.SHIFT_LEFT)
    		move_run = false;
    	if(keycode == Input.Keys.LEFT)
    		move_left = false;
        if(keycode == Input.Keys.RIGHT)
        	move_right = false;
        if(keycode == Input.Keys.UP)
        	move_up = false;
        if(keycode == Input.Keys.DOWN)
            move_down = false;
        return false;
    }
	
	@Override
    public boolean keyDown(int keycode) {
    	if(keycode == Input.Keys.SHIFT_LEFT)
    		move_run = true;
    	if(keycode == Input.Keys.LEFT)
    		move_left = true;
        if(keycode == Input.Keys.RIGHT)
        	move_right = true;
        if(keycode == Input.Keys.UP)
        	move_up = true;
        if(keycode == Input.Keys.DOWN)
            move_down = true;
        return false;
    }
	
    @Override
    public boolean keyTyped(char character) {
    	
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    	
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    	mouse_click = true;
    	mouse_x = screenX;
    	mouse_y = Gdx.graphics.getHeight() - screenY;
        return false;
    	
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
    	
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
}
