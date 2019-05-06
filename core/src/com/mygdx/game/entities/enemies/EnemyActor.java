package com.mygdx.game.entities.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.AnimationController;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveTo;

public class EnemyActor extends Actor {
    private AnimationController animationController;
    private Rectangle rectangle;
    private int scale, charX, charY, width, height, direction = 1, speed = 50;
    private boolean immobile = true;
    private Random random;

    public EnemyActor(TextureAtlas atlas, int offset, int x, int y) {
        super();
        animationController = new AnimationController(atlas, "characters");
        animationController.addAnimation("idle", new int[] {1, 5}, offset, 1/4f, true);
        animationController.addAnimation("walk", new int[] {1, 2, 3, 4}, offset, 1/10f, true);
        animationController.addAnimation("slash", new int[] {12, 11, 12, 13}, offset, 1/20f, true);
        animationController.addAnimation("punch", new int[] {12, 14, 12}, offset, 1/15f, true);
        animationController.addAnimation("jump", new int[] {5, 6, 7, 8}, offset, 1/20f, true);
        animationController.addAnimation("hit", new int[] {9, 10, 9}, offset, 1/20f, true);

        random = new Random();
        TextureRegion region = animationController.getKeyFrame();
        width = region.getRegionWidth();
        height = region.getRegionHeight();
        scale = 1;
        charX = x;
        charY = y;
        rectangle = new Rectangle(charX, charY, width, height);
        setBounds(x, y, width, height);
    }

    @Override
    public void act(float delta) {
        animationController.setTime(animationController.getTime() + delta);
        if (!immobile) {
            int deltaX = 0, deltaY = 0;

            if (random.nextBoolean() && random.nextBoolean() && random.nextBoolean()) {
                deltaX = random.nextInt() % speed * (random.nextInt() % 2 == 1 ? 1 : -1);
                deltaY = random.nextInt() % speed * (random.nextInt() % 2 == 1 ? 1 : -1);
                if (charX + deltaX > Gdx.graphics.getWidth() - (width * scale) || charX + deltaX < 0) deltaX = 0;
                if (charY + deltaY > Gdx.graphics.getHeight() - (height * scale) || charY + deltaY < 0) deltaY = 0;
            }
            if (deltaX > 0) {
                direction = 1;
            } else if (deltaX < 0) {
                direction = -1;
            }

            if (deltaX != 0 || deltaY != 0) {
                charX += deltaX;
                charY += deltaY;
                addAction(moveTo(charX, charY));
                animationController.changeAnimation("walk");
            } else {
                animationController.changeAnimation("idle");
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        batch.draw(animationController.getKeyFrame(), charX + (direction == -1 ? width*scale : 0), charY, getOriginX(), getOriginY(), width*scale*direction, height*scale, 1, 1, getRotation());
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


    public int getScale() {
        return scale;
    }

    public void setScale(int s) {
        scale = s;
    }

    public boolean getImmobile() {
        return immobile;
    }

    public void setImmobile(boolean i) {
        immobile = i;
    }
}
