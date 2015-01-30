package com.sirolf2009.wartriumph.input;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.sirolf2009.wartriumph.screens.ScreenGame;

public class InputAdapterGame implements InputProcessor {

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		OrthographicCamera camera = ScreenGame.camera;
		if(camera.zoom + ((float)amount/10) > 0) {
			ScreenGame.camera.zoom += (float)amount/10;
			ScreenGame.camera.update();
		}
		return false;
	}

}
