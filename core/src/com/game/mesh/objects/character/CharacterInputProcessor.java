package com.game.mesh.objects.character;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;

import com.game.GameSystem;
import com.game.addition.algorithms.aStar.Tile;
import com.game.addition.algorithms.aStar.TileType;
import com.game.addition.algorithms.aStar.algorithm.AlgorithmAStar;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.State;
import com.game.mesh.objects.singletons.special.Level;

import java.util.ArrayList;

public class CharacterInputProcessor extends Character implements InputProcessor{
	private static InputMultiplexer multiplexer = new InputMultiplexer ();
	
	private Tile start;
	private Tile finish;
	private Character character;
	
	
	public CharacterInputProcessor (Character character){
		this.character = character;
	}
	
	public void setInputProcessor (){
		multiplexer.addProcessor (this);
		Gdx.input.setInputProcessor (multiplexer);
		
		start = new Tile ();
		finish = new Tile ();
	}
	
	public void goToObject (int x, int y){
		if (character.isSelected){
			//обязательно надо к int делать преобразование, а то работать не будет.
			start.x = (int) ((character.getBodyX () + character.getBodyW () / 2 - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			start.y = (int) ((character.getBodyY () + character.getBodyH () / 2) / (GameObject.UNIT * GameObject.ANGLE));
			
			finish.x = (int) ((x - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			finish.y = (int) (y / (GameObject.UNIT * GameObject.ANGLE));
			
			AlgorithmAStar <Tile> algorithm = new AlgorithmAStar <> ();
			Level.getInstance ().setWithIgnoreFinish (true);
			Level.getInstance ().setFinish (new Tile ((int) finish.x, (int) finish.y, TileType.wall));
			Level.getInstance ().setWithDiagonalNeighbors (false);
			ArrayList <Tile> path = algorithm.findWay (Level.getInstance (), start, finish);
			
			if (path != null && path.size () != 1){
				character.goToObject = true;
				character.setPath (path);
			}
		}
	}
	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button){
		if (character.isSelected && !Gdx.input.isKeyPressed (Input.Keys.F) && character.state != State.push && character.state != State.abut){
			screenY = (int) (character.getSpriteY () + character.getSpriteH () / 2 - GameSystem.SCREEN_H / 2) + (int) GameSystem.SCREEN_H - screenY;
			
			//обязательно надо к int делать преобразование, а то работать не будет.
			start.x = (int) ((character.getBodyX () + character.getBodyW () / 2 - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			start.y = (int) ((character.getBodyY () + character.getBodyH () / 2) / (GameObject.UNIT * GameObject.ANGLE));
			
			finish.x = (int) ((screenX - GameSystem.INDENT_BETWEEN_SCREEN_LEVEL) / GameObject.UNIT);
			finish.y = (int) (screenY / (GameObject.UNIT * GameObject.ANGLE));
			
			AlgorithmAStar <Tile> algorithm = new AlgorithmAStar <> ();
			Level.getInstance ().setWithIgnoreFinish (false);
			Level.getInstance ().setWithDiagonalNeighbors (true);
			ArrayList <Tile> path = algorithm.findWay (Level.getInstance (), start, finish);
			
			if (path != null && path.size () != 1){
				character.setPath (path);
			}
			
			return true;
		}
		return false;
	}
	
	@Override
	public void clear (){
		multiplexer.clear ();
	}
	
	@Override
	public boolean touchUp (int screenX, int screenY, int pointer, int button){ return false; }
	@Override
	public boolean touchDragged (int screenX, int screenY, int pointer){ return false; }
	@Override
	public boolean mouseMoved (int screenX, int screenY){ return false; }
	@Override
	public boolean scrolled (int amount){ return false; }
	@Override
	public boolean keyDown (int keycode){ return false; }
	@Override
	public boolean keyUp (int keycode){ return false; }
	@Override
	public boolean keyTyped (char character){ return false; }
}
