package com.path_finder_demo.game;

public class LinearPathFinder {
	private LevelMap map;
	
	public LinearPathFinder(LevelMap map) {
		this.map = map;
	}
	public Path findPath(int sx, int sy, int tx, int ty){
		if (map.blocked(null,tx,ty)){
			return null;
		}
		Path path = new Path(); 
		float manhattan_distance = (Math.abs(tx - sx) + Math.abs(ty - sy)) / 2;
		float inc_x = (float)(tx - sx) / manhattan_distance;
		System.out.println("inc "+inc_x);
		float inc_y = (float)(ty - sy) / manhattan_distance;
		for(float x = sx; Math.abs(tx-x) > 1 ; x+=inc_x){
			for (float y = sy;  Math.abs(ty-y) > 1; y+=inc_y){
				
				if (map.blocked(null, (int)x, (int)y)){
					return null;
				}
				else {
					path.prependStep((int)x, (int)y);
				}
			}
		}
		path.prependStep(tx, ty);
		return path;
	}
}