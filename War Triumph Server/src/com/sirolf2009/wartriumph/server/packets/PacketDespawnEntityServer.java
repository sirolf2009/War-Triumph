package com.sirolf2009.wartriumph.server.packets;

import com.sirolf2009.networking.IHost;
import com.sirolf2009.wartriumph.packet.PacketDespawnEntity;
import com.sirolf2009.wartriumph.server.WarTriumphServer;

public class PacketDespawnEntityServer extends PacketDespawnEntity {

	public PacketDespawnEntityServer() {}

	public PacketDespawnEntityServer(long entityID) {
		super(entityID);
	}
	
	@Override
	public void receivedOnServer(IHost host) {
		WarTriumphServer.instance.getWorld().despawnEntity(entityID);
		host.getServer().broadcast(this);
	}

}
