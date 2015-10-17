/*
 *@author Tomas Raies
 *@date   17 de oct. de 2015
 */

package com.path_finder_demo.game;

public interface Handler {
	public void handle();
	public void send(Message message) throws WrongMessageException;
}
