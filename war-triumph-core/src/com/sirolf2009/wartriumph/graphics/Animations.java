package com.sirolf2009.wartriumph.graphics;

import com.badlogic.gdx.graphics.g2d.Animation;

public class Animations {

	public static Animation KNIGHT;
	
	public Animations() {
		KNIGHT = new Animation(1f, Textures.KNIGHT_1_R, Textures.KNIGHT_2_R, Textures.KNIGHT_3_R, Textures.KNIGHT_4_R);
	}

}
