package com.sirolf2009.wartriumph.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.OnscreenKeyboard;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.sirolf2009.wartriumph.Styles;
import com.sirolf2009.wartriumph.WarTriumph;

public class ScreenMainMenu implements Screen {
	
	private boolean isPaused;
	private boolean isShown;
	
	private Stage menu;

	public ScreenMainMenu(final WarTriumph warTriumph) {
		menu = new Stage();
		Gdx.input.setInputProcessor(menu);
		
		Table table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		HorizontalGroup groupUsername = new HorizontalGroup();
		
		final TextField textFieldUsername = new TextField("sirolf2009", Styles.TextFieldStyleMainMenu);
		textFieldUsername.setMessageText("Your username");
		textFieldUsername.setOnscreenKeyboard(new OnscreenKeyboard() {
			@Override
			public void show(boolean visible) {
				Gdx.input.setOnscreenKeyboardVisible(visible);
			}
		});

		groupUsername.addActor(new Label("Username: ", Styles.labelStyleMainMenu));
		groupUsername.addActor(textFieldUsername);
		
		HorizontalGroup groupIP = new HorizontalGroup();
		
		final TextField textFieldIP = new TextField("localhost", Styles.TextFieldStyleMainMenu);
		textFieldIP.setMessageText("The IP adress of the server");
		textFieldIP.setOnscreenKeyboard(new OnscreenKeyboard() {
			@Override
			public void show(boolean visible) {
				Gdx.input.setOnscreenKeyboardVisible(visible);
			}
		});
		
		groupIP.addActor(new Label("IP: ", Styles.labelStyleMainMenu));
		groupIP.addActor(textFieldIP);
		
		TextButton button = new TextButton("Connect", Styles.ButtonStyleMainMenu);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				warTriumph.setScreen(new ScreenGame(textFieldIP.getText(), textFieldUsername.getText()));
			}
		});

		table.add(groupUsername).align(Align.right).row();
		table.add(groupIP).align(Align.right).row();
		table.add(button);
		
		menu.addActor(table);
		
		show();
	}

	@Override
	public void render(float delta) {
		if(!isPaused) {
			onUpdate(delta);
			
			if(isShown) {
				Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
				Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
				menu.draw();
			}
		}
	}

	private void onUpdate(float delta) {
		Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		isShown = true;
	}

	@Override
	public void hide() {
		isShown = false;
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	@Override
	public void resume() {
		isPaused = false;
	}

	@Override
	public void dispose() {
	}

}
