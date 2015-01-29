package com.sirolf2009.wartriumph.packet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import com.badlogic.gdx.Gdx;
import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.IHost;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.WarTriumph;

public class PacketStartConversation extends Packet {

	private long entityID;
	private long sourceID;

	public PacketStartConversation() {}

	public PacketStartConversation(long targetID, long sourceID) {
		this.entityID = targetID;
		this.sourceID = sourceID;
	}

	@Override
	protected void write(PrintWriter out) {
		out.println(entityID);
		out.println(sourceID);
	}

	@Override
	public void receive(BufferedReader in) throws IOException {
		entityID = Long.parseLong(in.readLine());
		sourceID = Long.parseLong(in.readLine());
	}

	@Override
	public void receivedOnClient(IClient client) {
		if(WarTriumph.instance.getGame().getWorld().getPlayer().getEntityID() == entityID) {
			Gdx.app.postRunnable(new Runnable() {
				public void run() {
					WarTriumph.instance.getGame().getWorld().getPlayer().startConversationWith(sourceID);
				}
			});
		}
	}

	@Override
	public void receivedOnServer(IHost host) {
		host.getServer().broadcast(this, host);
	}

}
