package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.entity.EntityParty;
import com.sirolf2009.wartriumph.graphics.Textures;
import com.sirolf2009.wartriumph.world.WorldWarTriumph;

public class PacketSpawnEntity extends Packet {
	
	protected long entityID;
	protected int entityType;
	public static Map<Integer, Class<?>> entityIDToClass;
	public static Map<Class<?>, Integer> entityClassToID;

	public PacketSpawnEntity() {}
	
	public PacketSpawnEntity(long entityID, int entityType) {
		this.entityID = entityID;
		this.entityType = entityType;
	}

	@Override
	protected void write(PrintWriter out) {
		out.println(entityID);
		out.println(entityType);
	}
	
	@Override
	public void receive(BufferedReader in) throws IOException {
		entityID = Long.parseLong(in.readLine());
		entityType = Integer.parseInt(in.readLine());
	}

	@Override
	public void receivedOnClient(IClient client) {
		try {
			System.out.println(entityIDToClass.get(entityType));
			Entity entity = (Entity) entityIDToClass.get(entityType).getConstructor(WorldWarTriumph.class).newInstance(WarTriumph.instance.getGame().getWorld());
			entity.setWorld(WarTriumph.instance.getGame().getWorld());
			entity.setEntityID(entityID);
			entity.setTexture(Textures.KNIGHT_1);
			WarTriumph.instance.getGame().getWorld().spawnEntityInWorld(entity);
			entity.setWidth(64);
			entity.setHeight(64);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	static {
		entityIDToClass = new HashMap<Integer, Class<?>>();
		entityClassToID = new HashMap<Class<?>, Integer>();
		register(0, EntityParty.class);
	}
	
	public static void register(int ID, Class<?> entityClass) {
		entityIDToClass.put(ID, entityClass);
		entityClassToID.put(entityClass, ID);
	}

}
