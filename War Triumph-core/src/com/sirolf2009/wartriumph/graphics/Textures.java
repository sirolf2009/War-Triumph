package com.sirolf2009.wartriumph.graphics;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Textures {
	
	public static final String KNIGHT = "Scribe_knight";

	public static Texture KNIGHT_1;
	public static Texture KNIGHT_2;
	public static Texture KNIGHT_3;
	public static Texture KNIGHT_4;

	public static TextureRegion KNIGHT_1_R;
	public static TextureRegion KNIGHT_2_R;
	public static TextureRegion KNIGHT_3_R;
	public static TextureRegion KNIGHT_4_R;
	
	public static Texture COLOR_BLACK;
	
	public Textures() {
		KNIGHT_1 = new Texture(KNIGHT+"/knight walk 1_0.png");
		KNIGHT_2 = new Texture(KNIGHT+"/knight walk 2_0.png");
		KNIGHT_3 = new Texture(KNIGHT+"/knight walk 3_1.png");
		KNIGHT_4 = new Texture(KNIGHT+"/knight walk 4_0.png");

		KNIGHT_1_R = new TextureRegion(KNIGHT_1);
		KNIGHT_2_R = new TextureRegion(KNIGHT_2);
		KNIGHT_3_R = new TextureRegion(KNIGHT_3);
		KNIGHT_4_R = new TextureRegion(KNIGHT_4);
		
		Pixmap black = new Pixmap(1, 1, Format.RGB888);
		black.setColor(Color.BLACK);
		COLOR_BLACK = new Texture(black);
	}

}
