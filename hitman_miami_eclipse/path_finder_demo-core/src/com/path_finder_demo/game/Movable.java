/*
 *@author Tomas Raies
 *
 *@date 13 de oct. de 2015
 */


package com.path_finder_demo.game;

import com.badlogic.gdx.math.Vector2;

public interface Movable {
	Vector2 getPosition();
	Vector2 getDirection();
	boolean move(Vector2 direction);
	void update();
}
