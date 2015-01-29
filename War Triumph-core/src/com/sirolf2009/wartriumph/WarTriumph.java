package com.sirolf2009.wartriumph;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sirolf2009.networking.Packet;
import com.sirolf2009.wartriumph.packet.PacketConversationChat;
import com.sirolf2009.wartriumph.packet.PacketDespawnEntity;
import com.sirolf2009.wartriumph.packet.PacketGetPlayer;
import com.sirolf2009.wartriumph.packet.PacketGetWorldInfo;
import com.sirolf2009.wartriumph.packet.PacketSpawnEntity;
import com.sirolf2009.wartriumph.packet.PacketStartConversation;
import com.sirolf2009.wartriumph.packet.PacketUpdatePos;
import com.sirolf2009.wartriumph.screens.ScreenGame;
import com.sirolf2009.wartriumph.screens.ScreenMainMenu;

public class WarTriumph extends Game {

	public static WarTriumph instance;

	@Override
	public void create () {
		instance = this;
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		setScreen(new ScreenMainMenu(this));
	}
	
	public ScreenGame getGame() {
		return (ScreenGame) getScreen();
	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	static {
		Packet.registerPacket(1, PacketGetPlayer.class);
		Packet.registerPacket(2, PacketUpdatePos.class);
		Packet.registerPacket(3, PacketSpawnEntity.class);
		Packet.registerPacket(4, PacketGetWorldInfo.class);
		Packet.registerPacket(5, PacketDespawnEntity.class);
		Packet.registerPacket(6, PacketStartConversation.class);
		Packet.registerPacket(7, PacketConversationChat.class);
	}

}
