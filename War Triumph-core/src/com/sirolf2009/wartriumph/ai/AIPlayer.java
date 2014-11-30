package com.sirolf2009.wartriumph.ai;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.screens.ScreenGame;

public class AIPlayer implements IEntityIntelligence {

	private AINavigatorSimple nav;
	private AIFollowEntity follow;

	public AIPlayer(AINavigatorSimple nav, AIFollowEntity follow) {
		this.nav = nav;
		this.follow = follow;
	}

	@Override
	public void onUpdate(Entity entity, long deltaTime) {
		if(Gdx.input.isTouched()) {
			OrthographicCamera camera = ScreenGame.camera;
			float x = (Gdx.input.getX()+camera.position.x-(Gdx.app.getGraphics().getWidth()/2));
			float y = (-Gdx.input.getY()+camera.position.y+(Gdx.app.getGraphics().getHeight()/2));
			for(Entity entity2 : entity.getWorld().getEntities().values()) {
				if(entity2 != null) {
					if(selectedEntity(entity2, x, y)) {
						follow.setTarget(entity2);
						return;
					}
				}
			}
			nav.setDestination(new Vector2(x, y));
		}
	}

	public boolean selectedEntity(Entity entity, float x, float y){
		return (entity.getX() < x && entity.getY() < y &&
				entity.getX() + entity.getWidth() > x  &&
				entity.getY() + entity.getHeight() > y);
	}

}
