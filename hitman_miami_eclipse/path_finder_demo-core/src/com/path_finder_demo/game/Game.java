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
	AStarPathFinder path_finder;
	LevelMap map;
	OrthographicCamera camera;
	OrthogonalTiledMapRenderer renderer;
	int[][] moveset;
	int i = 0;
	FPSLogger fps_logger;
	ControlHandler control;
	Player player;
	CharacterView player_model;
	Set<NPC> goon_set = new HashSet<NPC>();
	PostOffice postOffice = PostOffice.getInstance();
	Set<CharacterView> goon_model_set = new HashSet<CharacterView>();
	float timer = 0f;
	NoiseHandler noiseHandler;
	VisionHandler visionHandler;
	
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
		RandArray<Vector2> randArray = new RandArray<Vector2>();
		randArray.add(new Vector2(200, 150));
		randArray.add(new Vector2(700,700));
		randArray.add(new Vector2(73,792));
		randArray.add(new Vector2(817,48));
		
		AStarPathFinder path_finder = new AStarPathFinder(map, 100);
		PathFinder linearPathFinder = new LinearPathFinder(map);
		for(int i=0; i< 10; i++){		
			goon_model = new CharacterView("assets/hitman_walk.png", 18, 13, 15);
			goon = new Goon(new Rectangle(40,40, 18,13),map,goon_model);
			goon.setAStarPathFinder(path_finder);
			goon.setLinearPathFinder(linearPathFinder);
			Strategy followStrategy = new FollowStrategy(goon);
			goon.setAlertBehaviour(followStrategy);
			Strategy patrolStrategy = new PatrolStrategy(randArray,goon);
			goon.setCalmBehaviour(patrolStrategy);
			goon_model.setPlayer(goon);
			goon_model_set.add(goon_model);
			goon_set.add(goon);
		}
		
		
		
		player_model = new CharacterView("assets/hitman_walk.png", 18, 13, 15);
		player = new Player(new Rectangle(50,50,18,13),map, player_model);
		player_model.setPlayer(player);
		noiseHandler = new NoiseHandler(goon_set, path_finder);
		visionHandler = new VisionHandler(goon_set, player, linearPathFinder);
		
		postOffice.setNoiseHandler(noiseHandler);
		control = new ControlHandler(player,map);
		Gdx.input.setInputProcessor(control);
		
	}

	@Override
	public void render () {
		fps_logger.log();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		control.update();
		try{
			postOffice.manage();
		}
		catch(WrongMessageException e){
			System.out.println("Wrong Message");
		}
		noiseHandler.handle();
		visionHandler.handle();
		player.update();
		
//		for(NPC g: goon_set){
//			
//			if (!g.isMoving()){
//				
//				Vector2 next = rand_array.get();
//				g.moveTo(next, true);
//			}
//		}
		
//		if (i == 20){
//			for (Goon g:goon_set){
//				g.moveTo(player.getPosition());
//			}
//			i=0;
//		}
//		else{
//			i++;
//		}
		for (NPC g:goon_set){
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
