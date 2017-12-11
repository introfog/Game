package com.game.mesh.objects.character;

import box2dLight.PointLight;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Pools;

import com.game.addition.algorithms.aStar.realisation.ConcreteNode;
import com.game.mesh.body.AnimatedObject;
import com.game.mesh.objects.GameObject;
import com.game.mesh.objects.ObjectType;
import com.game.mesh.objects.State;
import com.game.messages.GameMessage;
import com.game.render.Render;

import java.util.ArrayList;

public class Character extends GameObject{
	protected static final float CHARACTER_W = UNIT;
	protected static final float CHARACTER_H = UNIT;
	
	private static final float BODY_CHARACTER_W = 2 * CHARACTER_W / 5;
	private static final float BODY_CHARACTER_H = CHARACTER_H / 4;
	
	protected boolean goToObject = false;
	protected boolean isSelected = false;
	protected Direction currentDirection = Direction.forward;
	protected State state = State.stand;
	
	private CharacterName name = CharacterName.unknown;
	private PointLight flashLight;
	private CharacterMessageParser parser;
	private CharacterControl control;
	private CharacterInputProcessor inputProcessor;
	private CharacterAnimations animations;
	
	
	private Character (CharacterName name){
		objectType = ObjectType.character;
		this.name = name;
		
		body = new AnimatedObject (0, 0, CHARACTER_W, CHARACTER_H, BODY_CHARACTER_W, BODY_CHARACTER_H);
		body.move (0, 0.25f);
		
		flashLight = new PointLight (Render.getInstance ().handler,100, Color.GRAY, (int) (300 * ASPECT_RATIO),
				CHARACTER_W / 2, CHARACTER_H);
		parser = new CharacterMessageParser (this);
		control = new CharacterControl (this);
		inputProcessor = new CharacterInputProcessor (this);
		animations = new CharacterAnimations (this);
	}
	
	private static class CharacterHolder{
		private final static Character first = new Character (CharacterName.first);
		private final static Character second = new Character (CharacterName.second);
	}
	
	protected Character (){ }
	
	
	public static Character getFirstInstance (){
		return CharacterHolder.first;
	}
	
	public static Character getSecondInstance (){
		return CharacterHolder.second;
	}
	
	
	public void setSpritePosition (float x, float y){
		isSelected = (name == CharacterName.first);
		
		body.setSpritePosition (x, y);
		body.move (0, 0.25f);
		
		flashLight.setActive (true);
		flashLight.setPosition (x + CHARACTER_W / 2, y + CHARACTER_H);
		inputProcessor.setInputProcessor ();
	}
	
	public CharacterName getName (){
		return name;
	}
	
	@Override
	public void update (){
		parser.update ();
		animations.update ();
		control.update ();
	}
	
	@Override
	public void sendMessage (GameMessage message){
		parser.parseMessage (message);
	}
	
	@Override
	public void draw (){
		animations.draw ();
	}
	
	@Override
	public void clear (){
		state = State.stand;
		currentDirection = Direction.forward;
		
		flashLight.setActive (false);
		control.clear ();
		inputProcessor.clear ();
		animations.clear ();
		Pools.free (this);
	}
	
	protected float getBodyX (){
		return body.getBodyX ();
	}
	
	protected float getBodyY (){
		return body.getBodyY ();
	}
	
	protected float getBodyW (){
		return body.getBodyW ();
	}
	
	protected float getBodyH (){
		return body.getBodyH ();
	}
	
	protected float getSpriteX (){
		return body.getSpriteX ();
	}
	
	protected float getSpriteY (){
		return body.getSpriteY ();
	}
	
	protected float getSpriteW (){
		return body.getSpriteW ();
	}
	
	protected float getSpriteH (){
		return body.getSpriteH ();
	}
	
	protected void move (float deltaX, float deltaY){
		body.move (deltaX, deltaY);
		flashLight.setPosition (flashLight.getX () + deltaX, flashLight.getY () + deltaY);
	}
	
	protected boolean intersects (float x, float y, float w, float h){
		return body.intersects (x, y, w, h);
	}
	
	protected void setPath (ArrayList <ConcreteNode> path){
		control.setPath (path);
	}
	
	protected void goTo (int x, int y){
		inputProcessor.goTo (x, y);
	}
}