package com.sirolf2009.wartriumph.entity;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sirolf2009.wartriumph.Styles;
import com.sirolf2009.wartriumph.WarTriumph;
import com.sirolf2009.wartriumph.ai.AIFollowEntity;
import com.sirolf2009.wartriumph.ai.AINavigatorSimple;
import com.sirolf2009.wartriumph.ai.AIPlayer;
import com.sirolf2009.wartriumph.graphics.Animations;
import com.sirolf2009.wartriumph.graphics.Textures;
import com.sirolf2009.wartriumph.packet.PacketStartConversation;
import com.sirolf2009.wartriumph.packet.PacketUpdatePos;
import com.sirolf2009.wartriumph.stages.StageConversation;
import com.sirolf2009.wartriumph.world.WorldWarTriumph;

public class EntityPlayer extends EntityHero {

	private AINavigatorSimple navAI;
	private AIFollowEntity followAI;
	private Animation anim;
	private long stateTime;
	private String username;
	private boolean inConversation;
	private StageConversation conversation;
	private long updatePacketBurnout;
	private float lastUpdateX;
	private float lastUpdateY;

	public EntityPlayer(WorldWarTriumph world) {
		super(world);
		this.registerAI(navAI = new AINavigatorSimple());
		this.registerAI(followAI = new AIFollowEntity());
		this.registerAI(new AIPlayer(navAI, followAI));
		anim = Animations.KNIGHT;
		anim.setFrameDuration(100f);
		anim.setPlayMode(PlayMode.LOOP);
		stateTime = 0;
		setSpeed(2);
		setWidth(64);
		setHeight(64);
	}

	@Override
	public void onUpdate(long deltaTime) {
		super.onUpdate(deltaTime);
		if(isMoving()) {
			stateTime += deltaTime;
			setTexture(anim.getKeyFrame(stateTime, true).getTexture());
			if(getLastX() > getX()) {
				setWidth(-64);
			} else {
				setWidth(64);
			}
		} else {
			setTexture(Textures.KNIGHT_1);
		}

		updatePacketBurnout -= deltaTime;
		if(updatePacketBurnout < 0 && (getX() != lastUpdateX || getY() != lastUpdateY)) {
			if(updatePacketBurnout <= 0) {
				WarTriumph.instance.getGame().getNetworkManager().getSender().send(new PacketUpdatePos(this));
				updatePacketBurnout = 1000;
				lastUpdateX = getX();
				lastUpdateY = getY();
			}
		}
	}

	public void collide(Entity entity) {
		super.collide(entity);
		if(!isInConversation() && followAI.getTarget() != null && followAI.getTarget().equals(entity)) {
			setInConversation(true);
			WarTriumph.instance.getGame().getNetworkManager().getSender().send(new PacketStartConversation(entity.getEntityID(), getEntityID()));
			setConversation(new StageConversation(entity.getEntityID()));
			WarTriumph.instance.getGame().setStageConversation(getConversation());
		}
	}
	
	public void startConversationWith(long sourceID) {
		setInConversation(true);
		setConversation(new StageConversation(sourceID));
		WarTriumph.instance.getGame().setStageConversation(getConversation());
	}

	public void render(SpriteBatch batch) {
		super.render(batch);
		String tag = toString() + " " + username;
		Styles.DefaultFont.draw(batch, tag, getX()-Styles.DefaultFont.getBounds(tag).width/2, getY()-getHeight());
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isInConversation() {
		return inConversation;
	}

	public void setInConversation(boolean inConversation) {
		this.inConversation = inConversation;
	}

	public StageConversation getConversation() {
		return conversation;
	}

	public void setConversation(StageConversation conversation) {
		this.conversation = conversation;
	}

}
