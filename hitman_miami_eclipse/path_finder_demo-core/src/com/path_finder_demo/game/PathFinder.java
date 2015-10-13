package com.path_finder_demo.game;

import java.util.ArrayList;
import java.util.Collections;

import com.badlogic.gdx.math.Vector2;

public class PathFinder {
	private ArrayList<Node> closed = new ArrayList<Node>();
	private ArrayList<Node> open = new ArrayList<Node>();
	private Node[][] nodes;
	private LevelMap map;
	private int maxSearchDistance;
	
	public PathFinder(LevelMap map, int maxSearchDistance){
		this.map = map;
		this.maxSearchDistance = maxSearchDistance;
		nodes = new Node[map.getWidthInTiles()][map.getHeightInTiles()];
		for (int x=0; x < map.getWidthInTiles();x++){
			for (int y = 0; y< map.getHeightInTiles();y++){
				nodes[x][y] = new Node(x,y);
			}
		}
		
	}
	
	public Path findPath(Vector2 startPosition, Vector2 finalPosition) {
		
		
		int sx = Math.round(startPosition.x) / map.getTileWidth();
	    int sy = Math.round(startPosition.y) / map.getTileWidth();
	    int tx = Math.round(finalPosition.x) / map.getTileWidth();
	    int ty = Math.round(finalPosition.y) / map.getTileWidth();
	    
	    if (map.blocked(null, finalPosition) || (sx == tx && sy == ty)) {
		      return null;
	    }
	    
	    cleanNodes();
	    
	    nodes[sx][sy].cost = 0 + manhattanDistance(sx,sy,tx,ty);
	    closed.clear();
	    open.clear();
	    open.add(nodes[sx][sy]);
	    int maxDepth = 0;

	    while ( maxDepth < maxSearchDistance && open.size() != 0){
	    	Collections.sort(open);
	    	Node current = open.get(0);
	        
	        if (current == nodes[tx][ty]){
	          break;
	        }
	        open.remove(current);
	        closed.add(current);
	        
	        for (int x=-1; x<2;x++) {
	          for (int y = -1; y<2; y++) {
	            if (x==0 && y==0) {
	              continue;
	            }
	            if (x!=0 && y!=0){
	            	continue;
	            }
	            int xp = x+current.x;
	            int yp = y+current.y;
	            
	            Vector2 currPosition = new Vector2(xp * map.getTileWidth(),yp * map.getTileWidth());
	            
	            if (!map.blocked(null,currPosition)) {
	              float nextStepCost = current.cost + map.getCost(null,current.x, current.y, xp,yp) + manhattanDistance(current.x,current.y,xp,yp);
	              Node neighbour = nodes[xp][yp];
	              if (nextStepCost < neighbour.cost) {
	                if (open.contains(neighbour)) {
	                  open.remove(neighbour);
	                }
	                if (closed.contains(neighbour)) {
	                  closed.remove(neighbour); 
	                }
	              }
	              if (!open.contains(neighbour) && !(closed.contains(neighbour))){
	                neighbour.cost = nextStepCost;
	                maxDepth = Math.max(maxDepth, neighbour.setParent(current));
	                open.add(neighbour);
	                Collections.sort(open);
	              }
	            }
	          }
	        }
	    }
	    if (nodes[tx][ty].parent == null) {
	    	return null;
	    }
	    Path path = new Path();
	    // agregar la ultima posicion? path.prepend(finalPosition);
	    Node target = nodes[tx][ty];
	    Vector2 stepPosition = new Vector2();
	    while (target != nodes[sx][sy]){
	    	stepPosition = new Vector2(target.getX() * 32, target.getY() * 32);
	    	path.prependStep(stepPosition);
	    	target = target.getParent();
	    	
	    }
	    path.prependStep(new Vector2(sx * 32,sy*32));
	    return path;  
	}
	private int manhattanDistance(int x, int y, int xp, int yp){
		return Math.abs(xp - x) + Math.abs(yp -y);
//		return 0;
	}
	private void cleanNodes(){
		for(Node[] arr:nodes){
			for(Node n: arr){
				n.looseParent();
			}
		}
	}
	private class Node implements Comparable <Node>{
		private int x;
		private int y;
		private float cost;
		private Node parent = null;
		private int depth;
		
		public Node(int x,int y){
			this.x = x;
			this.y = y;
		}
		public int setParent(Node parent) {
			depth = parent.depth + 1;
			this.parent = parent;
			return depth;
		}
		public Node getParent(){
			return this.parent;
		}
		public void looseParent(){
			this.parent = null;
			this.depth = 0;
		}
		public int compareTo(Node other) {
			if (cost < other.cost){
				return -1;
			} else if ( cost > other.cost){
				return 1;
			} else {
				return 0;
			}
		}
		public boolean equals(Object o){
			if (o.getClass() != Node.class){
				return false;
			}
			Node other = (Node) o;
			if (other.x != x || other.y != y){
				return false;
			}
			return true;
		}
		public int hashCode(){
			return x ^ y;
		}
		public int getX(){
			return x;
		}
		public int getY(){
			return y;
		}
		public String toString(){
			return "x :" + x + " y: " + y;
		}
	}
	
}
