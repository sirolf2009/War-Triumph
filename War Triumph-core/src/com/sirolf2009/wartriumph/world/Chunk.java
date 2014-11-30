package com.sirolf2009.wartriumph.world;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import com.sirolf2009.wartriumph.entity.Entity;

public class Chunk {

	public static final int CHUNK_SIZE = 1024;
	private List<Entity> entities;
	private WorldWarTriumph world;
	private Point pos;

	public Chunk(WorldWarTriumph world, Point pos) {
		this.world = world;
		this.pos = pos;
		entities = new ArrayList<Entity>();
	}

	public void collide() {
		for(int i = -1; i < 1; i++) {
			for(int j = -1; j < 1; j++) {
				Point otherChunk = new Point(pos);
				otherChunk.translate(i, j);
				if(world.getChunks().containsKey(otherChunk)) {
					for(Entity entity : entities) {
						for(Entity entity2 : entities) {
								entity.collide(entity2);
						}
					}
				}
			}
		}
	}


	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void clear() {
		entities.clear();
	}

	public static Point getChunkPosFromPoint(float x, float y) {
		Point point = new Point();
		point.x = (int) Math.floor(x / CHUNK_SIZE); 
		point.y = (int) Math.floor(y / CHUNK_SIZE); 
		return point;
	}

}
