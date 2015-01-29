package com.sirolf2009.wartriumph;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

public class Styles {

	public static TextureAtlas KenneyPack;
	public static Skin KenneySkin;
	public static BitmapFont DefaultFont;

	public static TextButtonStyle ButtonStyleMainMenu;
	public static Drawable ButtonUp;
	public static Drawable ButtonDown;
	
	public static TextFieldStyle TextFieldStyleMainMenu;
	
	public static LabelStyle labelStyleMainMenu;
	
	static {
		KenneyPack = new TextureAtlas("button.pack");
		KenneySkin = new Skin(KenneyPack);
		DefaultFont = new BitmapFont();
		
		ButtonUp = KenneySkin.getDrawable("buttonLong_blue");
		ButtonDown = KenneySkin.getDrawable("buttonLong_blue_pressed");
		
		ButtonStyleMainMenu = new TextButtonStyle();
		ButtonStyleMainMenu.up = ButtonUp;
		ButtonStyleMainMenu.down = ButtonDown;
		ButtonStyleMainMenu.font = DefaultFont;
		
		TextFieldStyleMainMenu = new TextFieldStyle();
		TextFieldStyleMainMenu.font = DefaultFont;
		TextFieldStyleMainMenu.fontColor = Color.BLACK;
		TextFieldStyleMainMenu.background = KenneySkin.getDrawable("buttonLong_beige");
		TextFieldStyleMainMenu.cursor = KenneySkin.getDrawable("cursorGauntlet_blue");
		
		labelStyleMainMenu = new LabelStyle();
		labelStyleMainMenu.font = DefaultFont;
	}
}
