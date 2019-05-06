package com.mygdx.game.entities.weapons;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.AnimationController;
import com.mygdx.game.entities.player.PlayerActor;

public class WeaponActor extends Actor {
    private PlayerActor player;
    private TextureRegion icon;
    private TextureRegion texture = null;
    private AnimationController animation = null;
    private Rectangle rectangle;
    private int width, height, scale = 1, direction = 1;
    private float rotation = 0;

    public WeaponActor(PlayerActor p, TextureAtlas atlas, String atlasName, int offset) {
        player = p;
        animation = new AnimationController(atlas, atlasName);
        icon = atlas.findRegion(atlasName, offset);

        width = icon.getRegionWidth();
        height = icon.getRegionHeight();
        rectangle = new Rectangle(player.getX() + width, getY(), width, height);
    }

    public WeaponActor(PlayerActor p, TextureRegion region) {
        player = p;
        icon = region;
        texture = region;

        width = icon.getRegionWidth();
        height = icon.getRegionHeight();
        rectangle = new Rectangle(player.getX() + player.getWidth(), getY(), width, height);
    }

    public void update(int direction) {
        this.direction = direction;
    }

    @Override
    public void act(float delta) {
        rectangle.setPosition(direction == 1 ? getX() : player.getX() - width, getY());
        if (animation != null) {
            animation.setTime(animation.getTime() + delta);
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        if (animation != null) {
            batch.draw(
                    animation.getKeyFrame(),
                    player.getX() + (direction == 1 ? player.getWidth()*scale : 0),
                    player.getY(),
                    getOriginX(),
                    getOriginY(),
                    width*scale*direction,
                    height*scale,
                    1,
                    1,
                    rotation
            );
        } else if (texture != null) {
            batch.draw(
                    texture,
                    player.getX() + (direction == 1 ? player.getWidth()*scale : 0),
                    player.getY(),
                    getOriginX(),
                    getOriginY(),
                    width*scale*direction,
                    height*scale,
                    1,
                    1,
                    rotation
            );
        }
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    public void setRotation(float r) {
        rotation = r;
    }
}
