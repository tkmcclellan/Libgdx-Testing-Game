package com.mygdx.game.entities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.entities.HotbarActor;
import com.mygdx.game.entities.weapons.WeaponActor;
import com.mygdx.game.inputprocessors.PlayerInputProcessor;

public class Player extends Group {
    private WeaponManager weaponManager;
    private PlayerInputProcessor inputProcessor;
    private HotbarActor hotbar;
    private MeleeActor melee;
    private PlayerActor player;
    private int charX = 0, charY = 0;
    private boolean attacking = false;

    public Player(TextureAtlas playerAtlas, TextureAtlas meleeAtlas, PlayerInputProcessor processor) {
        super();
        player = new PlayerActor(this, playerAtlas, 23);
        melee = new MeleeActor(this, meleeAtlas);
        hotbar = new HotbarActor(10);
        weaponManager = new WeaponManager(10);
        player.setPosition(charX, charY);
        inputProcessor = processor;
        melee.setVisible(false);

        //TODO: remove this after implementing inventory manager
        TextureAtlas bowAtlas = new TextureAtlas(Gdx.files.internal("core/assets/data/bows/bows.atlas"));
        weaponManager.addWeapon(new WeaponActor(player, bowAtlas.findRegion("bows", 1)), 0);

        addActor(player);
        addActor(melee);
        addActor(hotbar);
    }

    @Override
    public void act(float deltaT) {
        //Get input
        PlayerInputProcessor.PlayerInput input = inputProcessor.getMovement(
                getX(),
                getY(),
                0,
                Gdx.graphics.getWidth() - player.getWidth(),
                0,
                Gdx.graphics.getHeight() - player.getHeight()
        );

        //Deconstruct input
        charX += input.deltaX;
        charY += input.deltaY;
        attacking = input.melee;

        //Update inventory managers
        hotbar.update(input.scrolled);
        weaponManager.update(input.direction, input.scrolled);
        weaponManager.setWeaponVisibility(input.melee);

        //Update actors
        player.update(deltaT, input.deltaX, input.deltaY, input.direction, attacking);
        melee.update(deltaT, input.deltaX, input.deltaY, input.direction, input.melee);

        super.act(deltaT);
    }

    public PlayerActor getPlayerActor() {
        return player;
    }

    public MeleeActor getSwooshActor() {
        return melee;
    }

    public boolean isAttacking() {
        return attacking;
    }

    @Override
    public float getX() {
        return charX;
    }

    @Override
    public float getY() {
        return charY;
    }
}
