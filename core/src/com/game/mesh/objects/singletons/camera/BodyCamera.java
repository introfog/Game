package com.game.mesh.objects.singletons.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;

import com.game.GameSystem;

public class BodyCamera{
	private float y;
	private OrthographicCamera camera;
	
	
	public BodyCamera (){
		y = GameSystem.SCREEN_H / 2;
		camera = new OrthographicCamera (GameSystem.SCREEN_W, GameSystem.SCREEN_H);
		camera.setToOrtho (false);
	}
	
	public void update (){
		camera.update ();
	}
	
	public Matrix4 getProjectionMatrix (){
		return camera.combined;
	}
	
	public void moveY (float deltaY){
		camera.translate (0, deltaY);
		y += deltaY;
	}
	
	public void setPositionY (float y){
		camera.translate (0, y - this.y);
		this.y = y;
	}
}