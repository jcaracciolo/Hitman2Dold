package com.path_finder_demo.game;

public interface TileBasedMap {
	public int getWidthInTiles();
    public int getHeightInTiles();	
    public boolean blocked(Mover mover, int x, int y);	
    public float getCost(Mover mover, int sx, int sy, int tx, int ty);
    public void pathFinderVisited(int x, int y);
}
