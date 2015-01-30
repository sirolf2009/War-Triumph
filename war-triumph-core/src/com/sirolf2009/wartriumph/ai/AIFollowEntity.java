package com.sirolf2009.wartriumph.ai;

import com.badlogic.gdx.math.Vector2;
import com.sirolf2009.wartriumph.entity.Entity;

public class AIFollowEntity implements IEntityIntelligence {

	public Entity target;
	
	@Override
	public void onUpdate(Entity entity, long deltaTime) {
		if(target != null) {
			Vector2 currentPosition = new Vector2();
	        currentPosition.set((int) entity.getX(), (int) entity.getY());
			Vector2 targetPosition = new Vector2();
			targetPosition.set((int) target.getX(), (int) target.getY());
	        Vector2 nextPosition = AINavigatorSimple.getVelocity(currentPosition, targetPosition, entity.getSpeed());
	        entity.setX(nextPosition.x);
	        entity.setY(nextPosition.y);
	        if(nextPosition == targetPosition) {
	        	target = null;
	        }
		}
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}
    
}
