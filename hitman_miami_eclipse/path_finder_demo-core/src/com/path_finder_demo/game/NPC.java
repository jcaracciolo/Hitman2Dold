/*
 *@author Tomas Raies
 *@date   13 de oct. de 2015
 */

package com.path_finder_demo.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class NPC extends Character {
	protected static final float EPSILON = 2f;
	public  Path currentPath;
	protected Step finalStep;
	protected Step currentStep = null;
	protected PathFinder aStarPathFinder;
	protected PathFinder linearPathFinder;
	
	
	public NPC (Rectangle hitBox, LevelMap map, Model model){
		super(hitBox, map, model);
	}
	public void setAStarPathFinder(PathFinder pathFinder){
		this.aStarPathFinder = pathFinder;
	}
	
	@Override
	public boolean moveTo(Vector2 position) {
		
		if (finalStep != null && position.epsilonEquals(finalStep.getPosition(), EPSILON )){
			return false;
		}
		
		Vector2 currPosition = new Vector2();
		currPosition = hitBox.getPosition(currPosition);
		Path auxPath = aStarPathFinder.findPath(currPosition, position);
		if (auxPath != null && auxPath.hasNextStep()){
			
			currentPath = auxPath;
			//currentStep = currentPath.nextStep();
			isMoving = true;
			return true;
		}
		
		return false;
	}
	
	@Override
	public void update() {
		if (!isMoving || currentPath == null){
			
			isMoving = false;
			currentPath = null;
			currentStep = null;
			return;
		}
	
		if (currentStep == null || currentStep.getPosition().epsilonEquals(getPosition(), EPSILON)){
			
			if (currentPath.hasNextStep()){
				System.out.println("algo");
				currentStep = currentPath.nextStep();
				move(currentStep.getPosition().sub(getPosition()));
			}
			else {
				currentPath = null;
				isMoving = false;
			}
		}
		System.out.println(currentStep.getPosition().epsilonEquals(getPosition(), EPSILON));
		super.update();	
	}
	

}