package com.sirolf2009.wartriumph.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sirolf2009.wartriumph.graphics.Animations;
import com.sirolf2009.wartriumph.graphics.Textures;
import com.sirolf2009.wartriumph.input.InputAdapterGame;
import com.sirolf2009.wartriumph.network.NetworkManager;
import com.sirolf2009.wartriumph.packet.PacketDespawnEntity;
import com.sirolf2009.wartriumph.world.WorldWarTriumph;

public class ScreenGame implements Screen {

	private SpriteBatch batch;
	private WorldWarTriumph world;
	private NetworkManager networkManager;
	private Stage hud;
	private TextButtonStyle buttonStyle;
	public static BitmapFont font;
	public static OrthographicCamera camera;
	private Viewport viewport;
	private long lastTime;
	private boolean isPaused;
	public TiledMap map;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public static Logger log = new Logger("WarTriumph");

	public ScreenGame(String host, String playername) {
		batch = new SpriteBatch();
		new Textures();
		new Animations();
		camera = new OrthographicCamera(Gdx.app.getGraphics().getWidth(), Gdx.app.getGraphics().getHeight());
		viewport = new ScreenViewport(camera);

		world = new WorldWarTriumph();

		setNetworkManager(new NetworkManager(host, playername));

		hud = new Stage(new ExtendViewport(1024, 800));
		TextureRegion upRegion = new TextureRegion(new Texture("CAMTATZ_assets/Buttons/BTN_BLUE_RECT_OUT.png"));
		TextureRegion downRegion =  new TextureRegion(new Texture("CAMTATZ_assets/Buttons/BTN_BLUE_RECT_IN.png"));
		BitmapFont buttonFont =  new BitmapFont();
		buttonFont.setScale(1);
		buttonStyle = new TextButtonStyle();
		buttonStyle.up = new TextureRegionDrawable(upRegion);
		buttonStyle.down = new TextureRegionDrawable(downRegion);
		buttonStyle.checked = new TextureRegionDrawable(downRegion);
		buttonStyle.font = buttonFont;
		font =  new BitmapFont();

		Table table = new Table();
		table.align(Align.left | Align.bottom);

		TextButton button = new TextButton("test me", buttonStyle);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
			}
		});

		TextButton button2 = new TextButton("don't test me", buttonStyle);

		table.add(button2);
		table.add(button);
		hud.addActor(table);
		
		TmxMapLoader loader = new TmxMapLoader();
		map = loader.load("map.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(map);
		mapRenderer.setView(camera);

		lastTime = System.currentTimeMillis();

		InputMultiplexer inputMultiPlexer = new InputMultiplexer();
		inputMultiPlexer.addProcessor(new InputAdapterGame());
		Gdx.input.setInputProcessor(inputMultiPlexer);
	}

	@Override
	public void dispose() {
		log.info("Shutting down");
		networkManager.getSender().send(new PacketDespawnEntity(getWorld().getPlayer().getEntityID()));
		batch.dispose();
		world.dispose();
		map.dispose();
		mapRenderer.dispose();
	}

	public WorldWarTriumph getWorld() {
		return world;
	}

	public void setWorld(WorldWarTriumph world) {
		this.world = world;
	}

	public NetworkManager getNetworkManager() {
		return networkManager;
	}

	public void setNetworkManager(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	@Override
	public void render(float delta) {
		if(!isPaused) {
			long time = System.currentTimeMillis();
			long deltaTime = time - lastTime;
			world.onUpdate(deltaTime);
			hud.act(deltaTime);
			lastTime = time;

			if(world.getPlayer() != null) {
				camera.position.set(world.getPlayer().getX(), world.getPlayer().getY(), 0);
				camera.update();
			}
			batch.setProjectionMatrix(camera.combined);

			Gdx.gl.glClearColor(1, 0, 0, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

			mapRenderer.setView(camera);
			mapRenderer.render();
			batch.begin();
			world.renderWorld(batch);
			font.draw(batch, "*0w, 0w", 0, 0);
			font.draw(batch, "*1024w, 0w", 1024, 0);
			font.draw(batch, "*0w, 1024w", 0, 1024);
			batch.end();
			hud.draw();
			world.getPhysicsDebugRenderer().render(world.getPhysicsWorld(), camera.combined);
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		camera.update();
	}

	@Override
	public void show() {
	}

	@Override
	public void hide() {
		dispose();
	}

	@Override
	public void pause() {
		isPaused = true;
	}

	@Override
	public void resume() {
		isPaused = false;
	}

}
