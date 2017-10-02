package com.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.game.objects.ObjectManager;


public class StartGame implements Screen{
	private Game game;
	
	StartGame (Game game){
		this.game = game;
	}
	
	@Override
	public void show (){
	
	}
	
	@Override
	public void render (float delta){
		
		
		ObjectManager.getInstance ().update ();
		ObjectManager.getInstance ().draw ();
		
		//if (Gdx.input.isTouched ()){
		//	System.out.println (Gdx.input.getX () + " " + Gdx.input.getY ());
		//}
	}
	
	@Override
	public void resize (int width, int height){
		
	}
	
	@Override
	public void pause (){
		
	}
	
	@Override
	public void resume (){
		
	}
	
	@Override
	public void hide (){
		
	}
	
	@Override
	public void dispose (){
	}
}
