package com.path_finder_demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Model {
	Goon character;
	SpriteBatch batch;
	Texture texture;
	TextureRegion[] walkFrames;
	Sprite sprite;
	Animation walkAnimation;
	TextureRegion currentFrame;
	float stateTime;
	
	//"assets/hitman_straight_walk_x2.png"
	
	public Model(String sprite_path,int sprite_width, int animation_length){
		
		batch = new SpriteBatch();
		texture = new Texture(Gdx.files.internal(sprite_path));
		TextureRegion[][] tmp = TextureRegion.split(texture, sprite_width, sprite_width);
		walkFrames = new TextureRegion[animation_length];
		for(int i=0; i<animation_length;i++){
			walkFrames[i] = tmp[0][i];
		}
		walkAnimation = new Animation(0.025f,walkFrames);
		stateTime = 0f;
	}
	public void setPlayer(Goon character){
		this.character = character;
	}
	public void draw(){
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		batch.begin();
//		if (character.currentPath != null){
//			for (Step s:character.currentPath.steps){
//				batch.draw(currentFrame, s.getPosition().x,s.getPosition().y);
//			}
//		}
		batch.draw(currentFrame, (int)character.getPosition().x, (int)character.getPosition().y);
		batch.end();
	}
	public void update(){
		 stateTime += Gdx.graphics.getDeltaTime();
	}
}
