package com.sirolf2009.wartriumph.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.entity.EntityPlayer;

public class WorldWarTriumph {

	protected Map<Long, Entity> entities;
	protected EntityPlayer player;
	protected Map<Point, Chunk> chunks;
	private World physicsWorld;
	private Box2DDebugRenderer physicsDebugRenderer;
	private boolean isRemote;
	
	public WorldWarTriumph() {
		init();
		initClient();
	}
	
	public void init() {
		entities = new HashMap<Long, Entity>();
		chunks = new HashMap<Point, Chunk>();
	}
	
	public void initClient() {
		setPhysicsWorld(new World(new Vector2(0, 0), true));
		setPhysicsDebugRenderer(new Box2DDebugRenderer());
	}

	public synchronized void onUpdate(long deltaTime) {
		List<Entity> entitiesMarked = new ArrayList<Entity>();

		getPhysicsWorld().step(deltaTime, 6, 2);
		
		for(Entity entity : entities.values()) {
			if(entity == null) {
				continue;
			}
			entity.onUpdate(deltaTime);
			if(entity.shouldRemove()) {
				entitiesMarked.add(entity);
			}
			Point chunkPos = Chunk.getChunkPosFromPoint(entity.getX(), entity.getY());
			if(chunks.get(chunkPos) == null) {
				chunks.put(chunkPos, new Chunk(this, chunkPos));
			}
			for(Entity entity2 : entities.values()) {
				if(entity2 != null && !entity.equals(entity2) && entity.getBounds().overlaps(entity2.getBounds())) {
					entity.collide(entity2);
				}
			}
			//chunks.get(chunkPos).addEntity(entity);
		}
		/*for(Chunk chunk : chunks.values()) {
			//chunk.collide();
		}
		for(Chunk chunk : chunks.values()) {
			chunk.clear();
		}*/
		for(Entity entity : entitiesMarked) {
			entity.dispose();
			entities.remove(entity);
		}
		entitiesMarked.clear();
		entitiesMarked = null;
	}

	public synchronized void renderWorld(SpriteBatch batch) {
		for(Entity entity : entities.values()) {
			if(entity == null) {
				continue;
			}
			entity.render(batch);
		}
	}

	public synchronized void spawnEntityInWorld(Entity entity) {
		entities.put(entity.getEntityID(), entity);
		entity.onSpawnInWorld(this);
	}

	public synchronized void despawnEntity(Entity entity) {
		despawnEntity(entity.getEntityID());
	}

	public synchronized void despawnEntity(long entityID) {
		entities.remove(entityID);
	}

	public void dispose() {
		for(Entity entity : entities.values()) {
			if(entity != null) {
				entity.dispose();
			}
		}
	}

	public Entity findEntityByID(long ID) {
		return entities.get(ID);
	}

	public Map<Long, Entity> getEntities() {
		return entities;
	}

	public void setEntities(Map<Long, Entity> entities) {
		this.entities = entities;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public void setPlayer(EntityPlayer player) {
		this.player = player;
	}

	public Map<Point, Chunk> getChunks() {
		return chunks;
	}

	public void setChunks(Map<Point, Chunk> chunks) {
		this.chunks = chunks;
	}

	public World getPhysicsWorld() {
		return physicsWorld;
	}

	public void setPhysicsWorld(World physicsWorld) {
		this.physicsWorld = physicsWorld;
	}

	public boolean isRemote() {
		return isRemote;
	}

	public void setRemote(boolean isRemote) {
		this.isRemote = isRemote;
	}

	public Box2DDebugRenderer getPhysicsDebugRenderer() {
		return physicsDebugRenderer;
	}

	public void setPhysicsDebugRenderer(Box2DDebugRenderer physicsDebugRenderer) {
		this.physicsDebugRenderer = physicsDebugRenderer;
	}

}
