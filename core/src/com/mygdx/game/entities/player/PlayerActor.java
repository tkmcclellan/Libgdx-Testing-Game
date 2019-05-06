package com.mygdx.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import com.mygdx.game.AnimationController;

public class PlayerActor extends Actor {
    private Player player;
    private AnimationController animationController;
    private int width, height, direction = 1;
    private float scale;
    private boolean immobile = false;
    private Rectangle rectangle;

    public PlayerActor(Player player, TextureAtlas atlas, int offset) {
        super();
        this.player = player;
        animationController = new AnimationController(atlas, "characters");
        animationController.addAnimation("idle", new int[] {1, 5}, offset, 1/4f, true);
        animationController.addAnimation("walk", new int[] {1, 2, 3, 4}, offset, 1/10f, true);
        animationController.addAnimation("slash", new int[] {12, 11, 12, 13}, offset, 1/20f, true);
        animationController.addAnimation("punch", new int[] {12, 14, 12}, offset, 1/15f, true);
        animationController.addAnimation("jump", new int[] {5, 6, 7, 8}, offset, 1/20f, true);
        animationController.addAnimation("hit", new int[] {9, 10, 9}, offset, 1/20f, true);

        TextureRegion region = animationController.getKeyFrame();
        width = region.getRegionWidth();
        height = region.getRegionHeight();
        scale = 1;
        rectangle = new Rectangle(getX(), getY(), width, height);
        setBounds(getX(), getY(), width, height);
    }

    public void update(float delta, float deltaX, float deltaY, int direction, boolean attacking) {
        this.direction = direction;
        animationController.setTime(animationController.getTime() + delta);
        setPosition(getX() + deltaX, getY() + deltaY);
        rectangle.setPosition(getX(), getY());
        if (!immobile && animationController.isAnimationFinished()) {
            if (attacking) {
                animationController.changeAnimation("slash");
            } else {
                if (deltaY != 0 || deltaX != 0) {
                    animationController.changeAnimation("walk");
                } else {
                    animationController.changeAnimation("idle");
                }
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(
                animationController.getKeyFrame(),
                getX() + (direction == -1 ? width*scale : 0),
                getY(),
                getOriginX(),
                getOriginY(),
                width*scale*direction,
                height*scale,
                1,
                1,
                getRotation()
        );
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    @Override
    public float getHeight() {
        return height;
    }

    @Override
    public float getWidth() {
        return width;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float s) {
        scale = s;
    }
}
