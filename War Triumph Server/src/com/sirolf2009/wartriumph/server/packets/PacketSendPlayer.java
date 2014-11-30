package com.sirolf2009.wartriumph.server.packets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.sirolf2009.networking.Host;
import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.IHost;
import com.sirolf2009.util.neo4j.rest.RestAPI;
import com.sirolf2009.wartriumph.packet.PacketGetPlayer;
import com.sirolf2009.wartriumph.server.WarTriumphServer;

public class PacketSendPlayer extends PacketGetPlayer {

	@Override
	public void receive(BufferedReader in) throws IOException {
		playername = in.readLine();
	}

	@Override
	protected void write(PrintWriter out) {
		out.println(posX);
		out.println(posY);
		out.println(entityID);
		out.println(playername);
	}

	@Override
	public void receivedOnServer(IHost host) {
		RestAPI rest = WarTriumphServer.instance.getRest();
		JSONObject players = rest.sendCypher("MATCH (n:Player) WHERE n.name=\\\""+playername+"\\\" RETURN n, id(n)");
		List<JSONArray> playerRows = rest.json.getRowsFromCypherQuery(players);
		if(playerRows.size() == 0) {
			WarTriumphServer.log.info("Creating new player");
			URI player = rest.nodes.createNode();
			try {
				rest.nodes.addLabelToNode(player, "Player");
				rest.nodes.addLabelToNode(player, "Entity");
				rest.nodes.addLabelToNode(player, "Hero");
				rest.nodes.addPropertyToNode(player, "name", playername);
				rest.nodes.addPropertyToNode(player, "posX", 0+"");
				rest.nodes.addPropertyToNode(player, "posY", 0+"");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			receivedOnServer(host);
			return;
		}
		JSONObject player = (JSONObject) playerRows.get(0).get(0);
		posX = player.get("posX").toString();
		posY = player.get("posY").toString();
		entityID = playerRows.get(0).get(1).toString();
		((Host)host).name = playername;
		((IClient)host).getSender().send(this);
	}

}
