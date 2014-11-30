package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;

public class PacketDespawnEntity extends Packet {
	
	protected long entityID;

	public PacketDespawnEntity() {}
	
	public PacketDespawnEntity(long entityID) {
		this.entityID = entityID;
	}
	
	@Override
	protected void write(PrintWriter out) {
		out.println(entityID);
	}
	
	@Override
	public void receive(BufferedReader in) throws IOException {
		entityID = Long.parseLong(in.readLine());
	}
	
	@Override
	public void receivedOnClient(IClient client) {
		WarTriumph.instance.getGame().getWorld().despawnEntity(entityID);
	}

}
