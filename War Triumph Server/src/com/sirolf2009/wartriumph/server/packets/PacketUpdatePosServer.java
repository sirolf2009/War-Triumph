package com.sirolf2009.wartriumph.server.packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;

import org.json.simple.JSONObject;

import com.sirolf2009.networking.IHost;
import com.sirolf2009.util.neo4j.rest.RestAPI;
import com.sirolf2009.wartriumph.entity.Entity;
import com.sirolf2009.wartriumph.packet.PacketUpdatePos;
import com.sirolf2009.wartriumph.server.WarTriumphServer;

public class PacketUpdatePosServer extends PacketUpdatePos {
	
	private RestAPI rest;

	public PacketUpdatePosServer() {}

	public PacketUpdatePosServer(Entity entity) {
		JSONObject properties = null;
		rest = WarTriumphServer.instance.getRest();
		try {
			properties = rest.nodes.getProperties(new URI(rest.SERVER_ROOT_URI+"node/"+entity.getEntityID()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		posX = properties.get("posX").toString();
		posY = properties.get("posY").toString();
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

	@Override
	public void receivedOnServer(IHost host) {
		try {
			rest = WarTriumphServer.instance.getRest();
			URI node = rest.nodes.fromID(Long.parseLong(entityID));
			rest.nodes.addPropertyToNode(node, "posX", posX.toString());
			rest.nodes.addPropertyToNode(node, "posY", posY.toString());
		} catch (NumberFormatException | URISyntaxException e) {
			e.printStackTrace();
		}
		host.getServer().broadcast(this, host);
		//WarTriumphServer.instance.getWorld().findEntityByID(Integer.parseInt(entityID)).setX(Float.parseFloat(posX));
		//WarTriumphServer.instance.getWorld().findEntityByID(Integer.parseInt(entityID)).setY(Float.parseFloat(posY));
	}

}