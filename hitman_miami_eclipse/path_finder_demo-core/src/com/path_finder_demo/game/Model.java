package com.path_finder_demo.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Model {
	Goon goon;
	SpriteBatch batch;
	Texture texture;
	TextureRegion[] walkFrames;
	Sprite sprite;
	Animation walkAnimation;
	TextureRegion currentFrame;
	float stateTime;
	double tile_to_pixel_ratio;
	//"assets/hitman_straight_walk_x2.png"
	
	public Model(String sprite_path,int sprite_width, int animation_length, double tile_to_pixel_ratio){
		this.tile_to_pixel_ratio = tile_to_pixel_ratio;
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
	public void setPlayer(Goon goon){
		this.goon = goon;
	}
	public void draw(){
		currentFrame = walkAnimation.getKeyFrame(stateTime, true);
		batch.begin();
//		if (goon.current_path != null){
//			for (Step s:goon.current_path.steps){
//				batch.draw(currentFrame, s.getX(),s.getY());
//			}
//		}
		batch.draw(currentFrame, goon.getX(),goon.getY());
		batch.end();
	}
	public void update(){
		 stateTime += Gdx.graphics.getDeltaTime();
	}
}
