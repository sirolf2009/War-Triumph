package com.sirolf2009.wartriumph.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Observable;
import java.util.Observer;

import com.badlogic.gdx.utils.Logger;
import com.sirolf2009.networking.Connector;
import com.sirolf2009.networking.Events.EventInvalidPacketIDReceived;
import com.sirolf2009.networking.Events.EventPacketReceived;
import com.sirolf2009.networking.Events.EventPacketSend;
import com.sirolf2009.networking.IClient;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.networking.Receiver;
import com.sirolf2009.networking.Sender;
import com.sirolf2009.networking.Events.EventConnectionLost;
import com.sirolf2009.wartriumph.packet.PacketGetPlayer;
import com.sirolf2009.wartriumph.packet.PacketGetWorldInfo;
import com.sirolf2009.wartriumph.packet.PacketUpdatePos;

public class NetworkManager implements IClient, Observer {

	private Socket socket;
	private boolean isConnected;
	private Sender sender;
	private Receiver receiver;
	private Connector connector;
	private String playername;

	public static Logger log = new Logger("NetworkManager");

	public NetworkManager(String host, String playername) {
		try {
			socket = new Socket(host, 1200);
			connector = new Connector(this, this);
			this.playername = playername;
			log = new Logger("Client - "+playername);
			new Thread(connector).start();
			log.setLevel(Logger.DEBUG);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof EventConnectionLost) {
			System.exit(-1);
		} else if(arg instanceof EventPacketReceived) {
			EventPacketReceived event = (EventPacketReceived) arg;
			if(event.getPacket() instanceof PacketUpdatePos) {
				log.info("Received packet "+event.getPacket());
			} else {
				log.info("Received packet "+event.getPacket());
			}
		} else if(arg instanceof EventInvalidPacketIDReceived) {
			logError("Invalid Packet with ID "+((EventInvalidPacketIDReceived)arg).getPacketID());
		} else if(arg instanceof EventPacketSend) {
			EventPacketSend event = (EventPacketSend) arg;
			Packet packet = event.getPacket();
			logDebug("Sending "+packet.getClass().getName()+":"+Packet.packetsPackettoID.get(packet.getClass()));
		} else {
			logError("Unkown update "+arg+" from "+o);
		}
	}

	public void logDebug(String msg) {
		log.debug(msg);
	}

	public void logError(String msg) {
		log.error(msg);
	}

	@Override
	public void onConnected() {
		sender.send(new PacketGetWorldInfo());
		sender.send(new PacketGetPlayer(playername));
	}

	public Socket getSocket() {
		return socket;
	}

	@Override
	public boolean isRemote() {
		return false;
	}

	@Override
	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public void disconnect() {
		isConnected = false;
		sender.disconnect();
		receiver.disconnect();
	}

	@Override
	public Sender getSender() {
		return sender;
	}

	@Override
	public void setSender(Sender sender) {
		this.sender = sender;
	}

	@Override
	public Receiver getReceiver() {
		return receiver;
	}

	@Override
	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

}
