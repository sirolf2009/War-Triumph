package com.sirolf2009.wartriumph.server.packets;

import com.sirolf2009.networking.IHost;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.packet.PacketSpawnEntity;
import com.sirolf2009.wartriumph.server.WarTriumphServer;

public class PacketSpawnEntityServer extends PacketSpawnEntity {

	public PacketSpawnEntityServer() {}

	public PacketSpawnEntityServer(long entityID, int entityType) {
		super(entityID, entityType);
	}
	
	@Override
	public void receivedOnServer(IHost host) {
		host.getServer().broadcast(this, host);
		try {
			Entity entity = (Entity) entityIDToClass.get(entityType).newInstance();
			entity.setEntityID(entityID);
			WarTriumphServer.instance.getWorld().spawnEntityInWorld(entity);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
