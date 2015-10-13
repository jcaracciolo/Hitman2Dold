package com.path_finder_demo.game;

import java.util.ArrayList;

public class Path {
	public ArrayList<Step> steps = new ArrayList<Step>();
	private int counter = 0;
	
	public int getLength(){
		//TODO
		return 0;
	}
	public Step nextStep(){
		return steps.get(--counter);
	}
	public boolean hasNextStep(){
		if (counter >= 1)
			return true;
		return false;
	}
	
	public void appendStep(int x, int y){
		//TODO
	}
	public void prependStep(int x,int y){
//		if (counter == 0 || (x!=steps.get(counter-1).getX() && y != steps.get(counter-1).getY())){
//			steps.add(new Step(x,y));
//			counter++;
//		}
		steps.add(counter,new Step(x,y));
		counter++;
	}
	public boolean reachedX(float xp){
		Step curr_step = steps.get(counter  );
		Step next_step = steps.get(counter -1);
		if (curr_step.getX() <= next_step.getX()) {
			return xp >= curr_step.getX();
		}
		else{
			return xp <= curr_step.getX();
		}
	}
	public boolean reachedY(float yp){
		Step curr_step = steps.get(counter  );
		Step next_step = steps.get(counter -1);
		if (curr_step.getY() <= next_step.getY()) {
			return yp >= curr_step.getY();
		}
		else{
			return yp <= curr_step.getY();
		}
	}
	public boolean reachedStep(float xp, float yp){
		return reachedX(xp) && reachedY(yp);
	}
	public boolean equals(Object o){
		if ( o == null){
			return false;
		}
		if (o.getClass() != Path.class){
			return false;
		}
		return ((Path)o).steps.equals(this.steps);
	}
}
