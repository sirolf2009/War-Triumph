package com.sirolf2009.wartriumph.entity;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.ai.IEntityIntelligence;
import com.sirolf2009.wartriumph.graphics.Textures;
import com.sirolf2009.wartriumph.packet.PacketUpdatePos;
import com.sirolf2009.wartriumph.world.WorldWarTriumph;

public class Entity {

	private Texture texture;
	private float posX;
	private float posY;
	private float lastX;
	private float lastY;
	private float width;
	private float height;
	private boolean isMoving;
	private boolean shouldRemove;
	private float speed;
	private List<IEntityIntelligence> AIList;
	private long entityID;
	private WorldWarTriumph world;
	private Rectangle bounds;
	private long updatePacketBurnout;

	public Entity() {
		AIList = new ArrayList<IEntityIntelligence>();
		speed = 2;
		texture = Textures.KNIGHT_1;
		bounds = new Rectangle();
	}

	public Entity(WorldWarTriumph world) {
		this();
		this.world = world;
	}

	public void onUpdate(long deltaTime) {
		lastX = getX();
		lastY = getY();
		for(IEntityIntelligence ai : AIList) {
			ai.onUpdate(this, deltaTime);
		}
		updatePacketBurnout -= deltaTime;
		if(getX() != lastX || getY() != lastY) {
			if(updatePacketBurnout <= 0) {
				WarTriumph.instance.getGame().getNetworkManager().getSender().send(new PacketUpdatePos(this));
				updatePacketBurnout = 1000;
			}
			setMoving(true);
		} else {
			setMoving(false);
		}
	}

	public void onSpawnInWorld(WorldWarTriumph world) {
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	private void updateBounds() {
		updateBounds(posX, posY, width, height);
	}

	private void updateBounds(float posX, float posY, float width, float height) {
		bounds.set(posX, posY, width, height);
	}

	public void collide(Entity entity) {
		System.out.println(this + " collides with "+entity);
	}

	public void render(SpriteBatch batch) {
		batch.draw(texture, getX()-(getWidth()/2), getY()-(getHeight()/2), width, height);
	}

	public void dispose() {
		texture.dispose();
	}

	public void markEntityForRemoval() {
		shouldRemove = true;
	}

	public void registerAI(IEntityIntelligence ai) {
		AIList.add(ai);
	}

	public void registerAI(IEntityIntelligence ai, int index) {
		AIList.add(index, ai);
	}

	@Override
	public String toString() {
		return getX()+", "+getY()+" "+getClass().getSimpleName()+":"+getEntityID();
	}

	public List<IEntityIntelligence> getAIList() {
		return AIList;
	}

	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float getX() {
		return posX;
	}

	public void setX(float x) {
		posX = x;
		updateBounds();
	}

	public float getY() {
		return posY;
	}

	public void setY(float y) {
		posY = y;
		updateBounds();
	}

	public boolean shouldRemove() {
		return shouldRemove;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public long getEntityID() {
		return entityID;
	}

	public void setEntityID(long l) {
		this.entityID = l;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
		updateBounds();
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
		updateBounds();
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public float getLastX() {
		return lastX;
	}

	public void setLastX(float lastX) {
		this.lastX = lastX;
	}

	public float getLastY() {
		return lastY;
	}

	public void setLastY(float lastY) {
		this.lastY = lastY;
	}

	public WorldWarTriumph getWorld() {
		return world;
	}

	public void setWorld(WorldWarTriumph world) {
		this.world = world;
	}

}
