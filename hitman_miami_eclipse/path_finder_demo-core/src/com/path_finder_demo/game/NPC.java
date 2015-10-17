/*
 *@author Tomas Raies
 *@date   13 de oct. de 2015
 */

package com.path_finder_demo.game;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class NPC extends Character {
	protected static final float EPSILON = 2f;
	protected  Path currentPath;
	protected Step finalStep;
	protected Step currentStep = null;
	private PathFinder aStarPathFinder;
	private PathFinder linearPathFinder;
	private Context context;
	private Strategy calmBehaviour;
	private Strategy suspiciousBehaviour;
	private Strategy alertBehaviour;
	private Strategy currentBehaviour;
	
	public NPC (Rectangle hitBox, LevelMap map, CharacterView model){
		super(hitBox, map, model);
		context = new Context();
	}
	public void setAStarPathFinder(PathFinder pathFinder){
		this.aStarPathFinder = pathFinder;
	}
	public void setLinearPathFinder(PathFinder pathFinder) {
		this.linearPathFinder = pathFinder;
	}
	public void setCalmBehaviour(Strategy calmBehaviour) {
		this.calmBehaviour = calmBehaviour;
	}
	public void setSuspiciousBehaviour (Strategy suspiciousBehaviour) {
		this.suspiciousBehaviour = suspiciousBehaviour;
	}
	public void setAlertBehaviour (Strategy alertBehaviour) {
		this.alertBehaviour = alertBehaviour;
	}
	public boolean moveTo(Vector2 position, boolean linear) {
		if (finalStep != null && position.epsilonEquals(finalStep.getPosition(), EPSILON )){
			return false;
		}
		Vector2 currPosition = new Vector2();
		currPosition = hitBox.getPosition(currPosition);
		
		PathFinder pathFinder;
		if (linear) {
			pathFinder = linearPathFinder;
		}
		else {
			pathFinder = aStarPathFinder;
		}
		
		Path auxPath = pathFinder.findPath(this, currPosition, position);
		if (auxPath != null && auxPath.hasNextStep()){
			currentPath = auxPath;
			currentStep = currentPath.nextStep();
			move(currentStep.getPosition().sub(getPosition()));
			isMoving = true;
			return true;
		}
		return false;
	}
	
	@Override
	public void update() {
		selectBehaviour();
		currentBehaviour.behave(context);
		context.flush();
		updatePosition();
		super.update();	
	}
	private void updatePosition() {
		if (!isMoving || currentPath == null){
			isMoving = false;
			currentPath = null;
			currentStep = null;
			return;
		}
	
		if (currentStep == null || currentStep.getPosition().epsilonEquals(getPosition(), EPSILON)){
			if (currentPath.hasNextStep()){
				currentStep = currentPath.nextStep();
			}
			else {
				currentPath = null;
				isMoving = false;
			}
		}
		if (isMoving){
			/*
			 * Ineficiente? Probablemente, pero me soluciona la vida por ahora. (Tiene que hacer una raiz cuadrada por frame) 
			 */
			move(currentStep.getPosition().sub(getPosition()));
		}
	}
	private void selectBehaviour() {
		
		if (context.playerIsVisible() || !alertBehaviour.done()) {
			
			currentBehaviour = alertBehaviour;
		}
//		else if (context.getNoise() != null || !suspiciousBehaviour.done()) {
//			
//			currentBehaviour = suspiciousBehaviour;
//		}
		else {
			
			currentBehaviour = calmBehaviour;
		}
	}
	public void addToContext(Noise n){
		context.add(n);
	}
	public void addToContext(Vector2 playerPosition) {
		context.add(playerPosition);
	}
}
