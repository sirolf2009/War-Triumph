package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.entity.Entity;

public class PacketUpdatePos extends Packet {
	
	public String posX;
	public String posY;
	public String entityID;

	public PacketUpdatePos() {}
	
	public PacketUpdatePos(Entity entity) {
		posX = entity.getX()+"";
		posY = entity.getY()+"";
		entityID = entity.getEntityID()+"";
	}
	
	@Override
	protected void write(PrintWriter out) {
		out.println(posX);
		out.println(posY);
		out.println(entityID);
	}
	
	@Override
	public void receive(BufferedReader in) throws IOException {
		posX = in.readLine();
		posY = in.readLine();
		entityID = in.readLine();
	}
	
	public void receivedOnClient(IClient client) {
		Entity entity = WarTriumph.instance.getGame().getWorld().findEntityByID(Integer.parseInt(entityID));
		entity.setX(Float.parseFloat(posX));
		entity.setY(Float.parseFloat(posY));
	}

}
