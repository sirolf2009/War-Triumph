package com.sirolf2009.wartriumph.ai;

import com.badlogic.gdx.math.Vector2;
import com.sirolf2009.wartriumph.entity.Entity;

public class AINavigatorSimple implements IEntityIntelligence {

	public Vector2 destination;
	
	@Override
	public void onUpdate(Entity entity, long deltaTime) {
		if(destination != null) {
			Vector2 currentPosition = new Vector2();
	        currentPosition.set((int) entity.getX(), (int) entity.getY());
	        Vector2 nextPosition = getVelocity(currentPosition, destination, entity.getSpeed());
	        entity.setX(nextPosition.x);
	        entity.setY(nextPosition.y);
	        if(nextPosition == destination) {
	        	destination = null;
	        }
		}
	}
	
	public static Vector2 getVelocity(Vector2 currentPosition, Vector2 destinationPosition, double speed) {
		if(calcDistanceBetweenVector2s(currentPosition, destinationPosition) < speed) {
			return destinationPosition;
		}
        Vector2 nextPosition = new Vector2();
        double angle = calcAngleBetweenVector2s(currentPosition, destinationPosition);
        Vector2 velocityVector2 = getVelocity(angle, speed);
        nextPosition.x = currentPosition.x + velocityVector2.x;
        nextPosition.y = currentPosition.y + velocityVector2.y;
        return nextPosition;
    }

    public static double calcAngleBetweenVector2s(Vector2 p1, Vector2 p2) {
        return Math.toDegrees(Math.atan2( p2.y-p1.y, p2.x-p1.x ));
    }

    public static Vector2 getVelocity(double angle, double speed) {
        int x = (int) (Math.cos(Math.toRadians(angle))*speed);
        int y = (int) (Math.sin(Math.toRadians(angle))*speed);
        return (new Vector2(x, y));
    }
    
    public static double calcDistanceBetweenVector2s(Vector2 p1, Vector2 p2) {
    	double deltaX = Math.pow(p1.x - p2.x, 2);
    	double deltaY = Math.pow(p1.y - p2.y, 2);
    	return Math.sqrt(deltaX + deltaY);
    }
	
	public void setDestination(Vector2 destination) {
		this.destination = destination;
	}
	
	public Vector2 getDestination() {
		return destination;
	}
    
}
