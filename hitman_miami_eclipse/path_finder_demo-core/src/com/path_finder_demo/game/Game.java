package com.path_finder_demo.game;

import java.util.HashSet;
import java.util.Set;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Game extends ApplicationAdapter {
	Texture img;
	TiledMap tiled_map;
	Goon goon;
//	NPC npc;
	CharacterView goon_model;
	PathFinder path_finder;
	LevelMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	int[][] moveset;
	int i = 0;
	FPSLogger fps_logger;
	ControlHandler control;
	RandArray<Vector2> rand_array;
	Player player;
	CharacterView player_model;
	Set<Goon> goon_set = new HashSet<Goon>();

	Set<CharacterView> goon_model_set = new HashSet<CharacterView>();
	float timer = 0f;
	
	@Override
	public void create () {
		int w = Gdx.graphics.getWidth();
        int h = Gdx.graphics.getHeight();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, w,h );
		camera.update();
		
		
		tiled_map = new TmxMapLoader().load("assets/test5.tmx");
		renderer = new OrthogonalTiledMapRenderer(tiled_map);
		
		
		LevelMap map = new LevelMap(w,h, 32,tiled_map);
		
		
		//LinearPathFinder path_finder = new LinearPathFinder(map);
		
		fps_logger = new FPSLogger();
		
		
		PathFinder path_finder = new PathFinder(map, 100);

		for(int i=0; i< 50; i++){		
			goon_model = new CharacterView("assets/hitman_walk.png", 18, 13, 15);
			goon = new Goon(new Rectangle(40,40, 18,13),map,goon_model);
			goon.setAStarPathFinder(path_finder);
			goon_model.setPlayer(goon);
			goon_model_set.add(goon_model);
			goon_set.add(goon);
		}
//		goon_model = new Model("assets/hitman_straight_walk.png", 32, 15);
//		goon = new Goon(new Rectangle(50,50, 1,1),map,goon_model);
//		goon.setAStarPathFinder(path_finder);
//		goon_model.setPlayer(goon);
//		goon_model_set.add(goon_model);
//		goon_set.add(goon);
		
		control = new ControlHandler(player,goon_set,map);
		Gdx.input.setInputProcessor(control);
		rand_array = new RandArray<Vector2>();
		rand_array.add(new Vector2(200, 150));
		rand_array.add(new Vector2(700,700));
		rand_array.add(new Vector2(73,792));
		rand_array.add(new Vector2(817,48));
		player_model = new CharacterView("assets/hitman_walk.png", 18, 13, 15);
		player = new Player(new Rectangle(50,50,18,13),map, player_model);
		player_model.setPlayer(player);
		control = new ControlHandler(player,goon_set,map);
		Gdx.input.setInputProcessor(control);
		
	}

	@Override
	public void render () {
		fps_logger.log();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		control.update();
		player.update();
//		if (player.getX() < 400 && player.getY() > 400){
//			for(Goon g:goon_set) {
//				g.move(player.getX(),player.getY());
//				
//			}
//		}
		
//		for(Goon g: goon_set){
//			
//			if (!g.isMoving()){
//				
//				Vector2 next = rand_array.get();
//				g.moveTo(next);
//			}
//		}
		
		if (i == 20){
			for (Goon g:goon_set){
				g.moveTo(player.getPosition());
			}
			i=0;
		}
		else{
			i++;
		}
		for (Goon g:goon_set){
			g.update();
		}
        camera.update();
        renderer.setView(camera);
        renderer.render();

        player_model.draw();
//      goon_model.draw();
        for (CharacterView gm:goon_model_set){
			gm.draw();
		}
        
	}
}
