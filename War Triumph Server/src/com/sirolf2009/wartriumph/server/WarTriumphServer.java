package com.sirolf2009.wartriumph.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;

import com.sirolf2009.networking.Connector;
import com.sirolf2009.networking.Events;
import com.sirolf2009.networking.Events.EventClientJoining;
import com.sirolf2009.networking.Events.EventConnectionLost;
import com.sirolf2009.networking.Events.EventInvalidPacketIDReceived;
import com.sirolf2009.networking.Events.EventPacketReceived;
import com.sirolf2009.networking.Host;
import com.sirolf2009.networking.IHost;
import com.sirolf2009.networking.IServer;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.util.neo4j.NeoUtil;
import com.sirolf2009.util.neo4j.rest.RestAPI;
import com.sirolf2009.wartriumph.server.packets.PacketDespawnEntityServer;
import com.sirolf2009.wartriumph.server.packets.PacketSendPlayer;
import com.sirolf2009.wartriumph.server.packets.PacketSendWorldInfo;
import com.sirolf2009.wartriumph.server.packets.PacketSpawnEntityServer;
import com.sirolf2009.wartriumph.server.packets.PacketUpdatePosServer;
import com.sirolf2009.wartriumph.server.world.WorldServer;

public class WarTriumphServer implements IServer, Observer {

	private ServerSocket server;
	private Connector connector;
	private List<IHost> hosts;

	private WorldServer world;
	private RestAPI rest;

	public static WarTriumphServer instance;
	public static Log log = new SimpleLog("WarTriumph Server");

	public WarTriumphServer(String path) throws IOException, URISyntaxException {
		instance = this;
		log.info("starting database");
		setRest(new RestAPI(new URI(path)));
		log.info("starting server");
		setWorld(new WorldServer());
		server = new ServerSocket(1200);
		connector = new Connector(this, this);
		hosts = new ArrayList<IHost>();
		new Thread(connector).start();
		
		NeoUtil.log.setLevel(SimpleLog.LOG_LEVEL_ALL);
		
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNext()) {
			String line = scanner.next();
			if(line.equalsIgnoreCase("exit")) {
				break;
			} else if(line.equalsIgnoreCase("print")) {
				if(scanner.hasNext()) {
					String par = scanner.next();
					if(par.equalsIgnoreCase("all")) {
						getRest().printCypher("MATCH (n) RETURN n");
					} else {
						getRest().printCypher("MATCH (n:"+par+") RETURN n");
					}
				}
			}
		}
		scanner.close();
		System.exit(0);
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Events.EventListeningForConnections) {
			log.info("Listening for connections");
		} else if(arg instanceof Events.EventClientJoining) {
			EventClientJoining event = (EventClientJoining) arg;
			Socket socket = event.getClient().getSocket();
			log.info(socket.getInetAddress()+" is connecting");
		} else if(arg instanceof Events.EventPacketReceived) {
			EventPacketReceived event = (EventPacketReceived) arg;
			if(event.getPacket() instanceof PacketUpdatePosServer) {
			} else {
				log.info("Received packet "+event.getPacket());
			}
		} else if(arg instanceof Events.EventConnectionLost) {
			EventConnectionLost event = (EventConnectionLost) arg;
			IHost host = (IHost) event.getCommunicator();
			Socket socket = ((IHost)event.getCommunicator()).getSocket();
			log.info(((Host)host).name+"@"+socket.getInetAddress()+" disconnected");
			host.disconnect();
		} else if(arg instanceof Events.EventPacketSend) {
		} else if(arg instanceof Events.EventInvalidPacketIDReceived) {
			EventInvalidPacketIDReceived event = (EventInvalidPacketIDReceived) arg;
			log.fatal("Invalid packet ID received: "+event.getPacketID());
			event.getException().printStackTrace();
		} else {
			log.error("unknown update "+arg+" from "+o);
		}
	}

	@Override
	public ServerSocket getSocket() {
		return server;
	}

	@Override
	public boolean isRemote() {
		return true;
	}

	@Override
	public boolean isConnected() {
		return false;
	}

	@Override
	public void disconnect() {
	}

	@Override
	public void broadcast(Packet packet) {
		for(IHost host : hosts) {
			host.getSender().send(packet);
		}
	}

	@Override
	public void broadcast(Packet packet, IHost hostToSkip) {
		for(IHost host : hosts) {
			if(host == hostToSkip) {
				continue;
			}
			host.getSender().send(packet);
		}
	}

	@Override
	public void onClientConnect(IHost host) {
		log.info(host.getSocket().getInetAddress()+" connected");
		hosts.add(host);
	}

	@Override
	public IHost createHost(Socket client) {
		return new Host(client, this, this);
	}

	@Override
	public Connector getConnector() {
		return connector;
	}

	public WorldServer getWorld() {
		return world;
	}

	public void setWorld(WorldServer world) {
		this.world = world;
	}

	public static void main(String[] args) {
		String uri;
		if(args.length > 0) {
			uri = args[0];
		} else {
			uri = "http://localhost:7474/db/data/";
		}
		try {
			new WarTriumphServer(uri);
		} catch (IOException e) {
			log.fatal("Could not set up a server socket");
			e.printStackTrace();
		} catch (URISyntaxException e) {
			log.fatal("Could not connect to the database");
			e.printStackTrace();
		}
	}

	public RestAPI getRest() {
		return rest;
	}

	public void setRest(RestAPI rest) {
		this.rest = rest;
	}

	static {
		Packet.registerPacket(1, PacketSendPlayer.class);
		Packet.registerPacket(2, PacketUpdatePosServer.class);
		Packet.registerPacket(3, PacketSpawnEntityServer.class);
		Packet.registerPacket(4, PacketSendWorldInfo.class);
		Packet.registerPacket(5, PacketDespawnEntityServer.class);
	}

}
