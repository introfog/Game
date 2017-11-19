package com.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.game.MyGame;
import com.game.addition.Font;

public class MainMenuScreen implements Screen{
	private TextButton.TextButtonStyle normalStyle;
	private WidgetGroup widgetGroup = new WidgetGroup ();
	private Stage stage = new Stage (new ScreenViewport ());
	
	
	private void createStyle (){
		TextureAtlas buttonAtlas = new TextureAtlas ("core/assets/images/button.atlas");
		Skin skin = new Skin ();
		skin.addRegions (buttonAtlas);
		
		
		normalStyle = new TextButton.TextButtonStyle ();
		normalStyle.font = Font.generateFont ("core/assets/fonts/russoone.ttf", MyGame.BUTTON_FONT_SIZE, Color.WHITE);
		normalStyle.up = skin.getDrawable ("button_up");
		normalStyle.over = skin.getDrawable ("button_checked");
		normalStyle.down = skin.getDrawable ("button_checked");
	}
	
	private void createPlayButton (){
		TextButton play;
		play = new TextButton ("Играть", normalStyle);
		play.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new SelectedModeScreen ());
			}
		});
		play.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 + MyGame.BUTTON_H + MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (play);
	}
	
	private void createSettingsButton (){
		TextButton settings;
		settings = new TextButton ("Настройки", normalStyle);
		settings.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new SettingsScreen ());
			}
		});
		settings.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2, MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (settings);
	}
	
	private void createExitButton (){
		TextButton exit;
		exit = new TextButton ("Выход", normalStyle);
		exit.addListener (new ClickListener (){
			@Override
			public void touchUp (InputEvent event, float x, float y, int pointer, int button){
				MyGame.getInstance ().setScreen (new QuitGameScreen ());
			}
		});
		exit.setBounds (Gdx.graphics.getWidth () / 2 - MyGame.BUTTON_W / 2,
				Gdx.graphics.getHeight () / 2 - MyGame.BUTTON_H - MyGame.DISTANCE_BETWEEN_BUTTONS,
				MyGame.BUTTON_W, MyGame.BUTTON_H);
		widgetGroup.addActor (exit);
	}
	
	private void createButton (){
		createStyle ();
		
		createPlayButton ();
		createSettingsButton ();
		createExitButton ();
		
		stage.addActor (widgetGroup);
	}
	
	
	@Override
	public void show (){
		createButton ();
		
		// Устанавливаем нашу сцену основным процессором для ввода
		Gdx.input.setInputProcessor (stage);
	}
	
	@Override
	public void render (float delta){
		Gdx.gl.glClearColor (0, 0, 0, 1);
		Gdx.gl.glClear (GL20.GL_COLOR_BUFFER_BIT);
		
		stage.act (delta);
		stage.draw ();
	}
	
	@Override
	public void resize (int width, int height){ }
	
	@Override
	public void pause (){ }
	
	@Override
	public void resume (){ }
	
	@Override
	public void hide (){ }
	
	@Override
	public void dispose (){
		stage.dispose ();
	}
}
