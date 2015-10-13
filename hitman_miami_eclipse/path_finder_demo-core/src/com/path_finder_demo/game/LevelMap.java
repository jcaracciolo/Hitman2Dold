package com.path_finder_demo.game;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;

public class LevelMap  implements TileBasedMap {
	
	private int width;
	private int height;
	private TiledMap tiled_map;
	private int tile_width;
	private boolean[][] visited;
	TiledMapTileLayer foreground;
	
	public LevelMap(int width, int height,int tile_width, TiledMap tiled_map){
		this.width = width;
		this.height = height;
		this.tile_width = tile_width;
		this.tiled_map = tiled_map;
		this.foreground =  (TiledMapTileLayer)tiled_map.getLayers().get(1);
		visited = new boolean[width /tile_width ][height / tile_width ];
		
		//level_array[5][10] = true;
	}

	@Override
	public int getWidthInTiles() {
		return width / tile_width;
	}

	@Override
	public int getHeightInTiles() {
		return height /tile_width;
	}

	@Override
	public boolean blocked(Mover mover, int x, int y) {
		if (x < 0 || (y < 0) || x >= width || y >= height){
			return true;
		}
		return foreground.getCell(x / tile_width,y / tile_width) != null;
	}

	@Override
	public float getCost(Mover mover, int sx, int sy, int tx, int ty) {
		if (sx != tx && sy != ty){
			return 1.41f;
		}
		return 1f;
	}

	@Override
	public void pathFinderVisited(int x, int y) {
		visited[x][y] = true;
	}
	public boolean visited(int x, int y) {
		return visited[x][y];
	}
	public void clearVisited() {
		for (int x=0;x<getWidthInTiles();x++) {
			for (int y=0;y<getHeightInTiles();y++) {
				visited[x][y] = false;
			}
		}
	}
	public int tilePosition(int x){
		return x / tile_width;
	}
	public boolean isValid(Rectangle hit_box) {
		int max_height =(int) (hit_box.getY() + hit_box.getHeight());
		int max_width = (int) (hit_box.getX() + hit_box.getWidth());
		
		for (int x = (int) hit_box.getX(); x < max_width; x+=1){
			for (int y = (int) hit_box.getY(); y <  max_height; y+= 1){
				if (blocked(null,x,y)){
					return false;
				}
			}
//			if (blocked(null,tilePosition(x),tilePosition(max_width))){
//				System.out.println("x " + tilePosition(x)+ " y " + tilePosition(max_width));
//				return false;
//			}
		}
//		if (blocked(null,tilePosition(max_height),tilePosition(max_width))){
//			return false;
//		}
		return true;
	}
	
}
