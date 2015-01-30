package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.entity.EntityParty;
import com.sirolf2009.wartriumph.entity.EntityPlayer;

public class PacketGetPlayer extends Packet {
	
	public String playername;
	
	public String posX;
	public String posY;
	public String entityID;

	public PacketGetPlayer() {}
	
	public PacketGetPlayer(String playername) {
		this.playername = playername;
	}
	
	@Override
	protected void write(PrintWriter out) {
		System.out.println("sending "+playername);
		out.println(playername);
	}
	
	@Override
	public void receive(BufferedReader in) throws IOException {
		posX = in.readLine();
		posY = in.readLine();
		entityID = in.readLine();
		playername = in.readLine();
	}

	@Override
	public void receivedOnClient(IClient client) {
		EntityPlayer player = new EntityPlayer(WarTriumph.instance.getGame().getWorld());
		player.setEntityID(Long.parseLong(entityID));
		WarTriumph.instance.getGame().getWorld().spawnEntityInWorld(player);
		player.setX(Float.parseFloat(posX));
		player.setY(Float.parseFloat(posY));
		player.setUsername(playername);
		WarTriumph.instance.getGame().getWorld().setPlayer(player);
		client.getSender().send(new PacketSpawnEntity(player.getEntityID(), PacketSpawnEntity.entityClassToID.get(EntityParty.class)));
		client.getSender().send(new PacketUpdatePos(player));
	}

}
