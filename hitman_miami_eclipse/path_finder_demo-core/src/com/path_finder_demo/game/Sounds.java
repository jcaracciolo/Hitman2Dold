/*
 *@author Juan F. Caracciolo
 *@date   Oct 17, 2015
 */

package com.path_finder_demo.game;

import com.badlogic.gdx.Gdx;  
import com.badlogic.gdx.audio.Sound;  
  
public class Sounds {  
      
    public static Sound shoot;
    public static Sound run;
    public static Sound death;
    public static Sound victory;
    

  
      
    public static void load () {  
          
        //shoot = loadSound("shoot.wav");  
        //run = loadSound("run.wav"); 
    	//victory = loadSound("victory.wav"); 
    	//death = loadSound("death.wav"); 
    }  
      
    private static Sound loadSound(String filename) {  
        return Gdx.audio.newSound(Gdx.files.internal("data/sounds/" + filename));  
    }
      
    public static void play (Sound sound) {  
        sound.play(1);  
    }  
}  