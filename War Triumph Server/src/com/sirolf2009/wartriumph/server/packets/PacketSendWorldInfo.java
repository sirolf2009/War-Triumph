package com.sirolf2009.wartriumph.server.packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sirolf2009.networking.IHost;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.packet.PacketSpawnEntity;
import com.sirolf2009.wartriumph.server.WarTriumphServer;

public class PacketSendWorldInfo extends Packet {

	public PacketSendWorldInfo() {}
	
	@Override
	public void send(PrintWriter out) {
	}
	
	@Override
	public void receive(BufferedReader in) throws IOException {
	}
	
	@Override
	public void receivedOnServer(IHost host) {
		for(int i = 0; i < WarTriumphServer.instance.getWorld().getEntities().size(); i++) {
			Entity entity = WarTriumphServer.instance.getWorld().getEntities().get(i);
			if(entity == null) {
				continue;
			}
			long entityID = entity.getEntityID();
			int entityType = PacketSpawnEntity.entityClassToID.get(entity.getClass());
			host.getSender().send(new PacketSpawnEntityServer(entityID, entityType));
			host.getSender().send(new PacketUpdatePosServer(entity));
		}
	}

}
