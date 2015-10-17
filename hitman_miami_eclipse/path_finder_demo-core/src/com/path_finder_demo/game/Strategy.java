/*
 *@author Tomas Raies
 *@date   17 de oct. de 2015
 */

package com.path_finder_demo.game;

public interface Strategy {
	public void behave(Context context);
	public boolean done();
}
