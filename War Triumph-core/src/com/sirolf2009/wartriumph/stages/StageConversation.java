package com.sirolf2009.wartriumph.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sirolf2009.wartriumph.Styles;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.packet.PacketConversationChat;

public class StageConversation extends Stage {
	
	private TextArea chat;
	private TextField chatMessage;

	public StageConversation(long targetID) {
		super();
		init(targetID);
	}

	public StageConversation(Viewport viewport, long targetID) {
		super(viewport);
		init(targetID);
	}

	public StageConversation(Viewport viewport, Batch batch, long targetID) {
		super(viewport, batch);
		init(targetID);
	}
	
	public void init(final long targetID) {
		Table table = new Table();
		table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		table.align(Align.center);
		table.setFillParent(true);
		
		chat = new TextArea("new chat started", Styles.TextFieldStyleMainMenu);
		chat.setDisabled(true);

		chatMessage = new TextField("", Styles.TextFieldStyleMainMenu);
		
		TextButton button = new TextButton("send", Styles.ButtonStyleMainMenu);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String message = WarTriumph.instance.getGame().getWorld().getPlayer().getUsername()+": "+chatMessage.getText();
				WarTriumph.instance.getGame().getNetworkManager().getSender().send(new PacketConversationChat(targetID, WarTriumph.instance.getGame().getWorld().getPlayer().getEntityID(), message));
				addMessageToChat(message);
				chatMessage.setText("");
			}
		});
		
		table.defaults().width(800f);
		table.row().height(200f);
		table.add(chat);
		table.row().height(50f);
		table.add(chatMessage);
		table.add(button).width(50f);
		addActor(table);
	}

	public void addMessageToChat(String message) {
		chat.setText(chat.getText()+"\n"+message);
	}

}
