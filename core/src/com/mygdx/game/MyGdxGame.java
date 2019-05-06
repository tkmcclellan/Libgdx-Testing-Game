package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.entities.player.Player;
import com.mygdx.game.inputprocessors.PlayerInputProcessor;
import com.mygdx.game.managers.EnemyManager;

public class MyGdxGame extends ApplicationAdapter {
	private PlayerInputProcessor processor;
	private Stage stage;
	private Player player;
	private EnemyManager enemies;
	private TextureAtlas characterAtlas, swooshAtlas;
	private ShapeRenderer shapeRenderer;
	private boolean debug = true;

	@Override
	public void create () {
		if (debug) shapeRenderer = new ShapeRenderer();
		characterAtlas = new TextureAtlas(Gdx.files.internal("core/assets/data/characters/characters.atlas"));
		swooshAtlas = new TextureAtlas(Gdx.files.internal("core/assets/data/swoosh/swoosh.atlas"));
		stage = new Stage();
		processor = new PlayerInputProcessor(0,0,15);
		player = new Player(characterAtlas, swooshAtlas, processor);
		enemies = new EnemyManager(player);

		stage.addActor(enemies);
		stage.addActor(player);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		stage.act(Gdx.graphics.getDeltaTime());

		if (debug) {
			Rectangle swooshRect = player.getSwooshActor().getRectangle();
			Rectangle playerRect = player.getPlayerActor().getRectangle();
			shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
			shapeRenderer.setColor(Color.RED);
			shapeRenderer.rect(swooshRect.getX(), swooshRect.getY(), swooshRect.getWidth(), swooshRect.getHeight());
			shapeRenderer.rect(playerRect.getX(), playerRect.getY(), playerRect.getWidth(), playerRect.getHeight());
			for (Actor enemy : enemies.getChildren()) {
				shapeRenderer.rect(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
			}
			shapeRenderer.line(player.getX(), player.getY(), Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY());
			shapeRenderer.end();
		}

		stage.draw();
	}

	@Override
	public void dispose () {
		characterAtlas.dispose();
		swooshAtlas.dispose();
		shapeRenderer.dispose();
		stage.dispose();
	}
}
