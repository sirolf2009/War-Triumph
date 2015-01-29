package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.IHost;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;

public class PacketConversationChat extends Packet {
	
	private long entityID;
	private long sourceID;
	private String message;

	public PacketConversationChat() {}

	public PacketConversationChat(long targetID, long sourceID, String message) {
		this.entityID = targetID;
		this.sourceID = sourceID;
		this.message = message;
	}

	@Override
	protected void write(PrintWriter out) {
		out.println(entityID);
		out.println(sourceID);
		out.println(message);
	}

	@Override
	public void receive(BufferedReader in) throws IOException {
		entityID = Long.parseLong(in.readLine());
		sourceID = Long.parseLong(in.readLine());
		message = in.readLine();
	}

	@Override
	public void receivedOnClient(IClient client) {
		if(WarTriumph.instance.getGame().getWorld().getPlayer().getEntityID() == entityID) {
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					WarTriumph.instance.getGame().getWorld().getPlayer().getConversation().addMessageToChat(message);
				}
			});
		}
	}

	@Override
	public void receivedOnServer(IHost host) {
		host.getServer().broadcast(this, host);
	}

}
