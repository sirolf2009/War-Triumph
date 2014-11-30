package com.sirolf2009.wartriumph.entity;

import java.util.ArrayList;
import java.util.List;

import com.sirolf2009.wartriumph.graphics.Textures;
import com.sirolf2009.wartriumph.world.WorldWarTriumph;

public class EntityParty extends Entity {
	
	private EntityHero hero;
	private List<EntityTroop> troops;
	
	public EntityParty() {}
	
	public EntityParty(WorldWarTriumph world) {
		super(world);
		setTroops(new ArrayList<EntityTroop>());
		setWidth(64);
		setHeight(64);
		setTexture(Textures.KNIGHT_1);
	}

	public EntityHero getHero() {
		return hero;
	}

	public void setHero(EntityHero hero) {
		this.hero = hero;
	}

	public List<EntityTroop> getTroops() {
		return troops;
	}

	public void setTroops(List<EntityTroop> troops) {
		this.troops = troops;
	}

}
