package com.mygdx.game.entities.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.mygdx.game.AnimationController;

public class MeleeActor extends Actor {
    private TextureRegion icon;
    private Player player;
    private AnimationController animationController;
    private int width, height, direction = 1;
    private Rectangle rectangle;

    public MeleeActor(Player player, TextureAtlas atlas) {
        super();
        this.player = player;
        animationController = new AnimationController(atlas, "swoosh");
        animationController.addAnimation("swoosh", new int[] {1, 2, 3, 4}, 0, 1/20f, false);
        TextureRegion swooshTexture = animationController.getKeyFrame();
        icon = swooshTexture;
        height = swooshTexture.getRegionHeight();
        width = swooshTexture.getRegionWidth();
        PlayerActor playerActor = player.getPlayerActor();
        setPosition(playerActor.getX() + playerActor.getWidth(), playerActor.getY());
        rectangle = new Rectangle(getX(), getY(), width, height);
        setBounds(
                getX(),
                getY(),
                width,
                height
        );
    }

    public void update(float delta, float deltaX, float deltaY, int direction, boolean melee) {
        this.direction = direction;
        animationController.setTime(animationController.getTime() + delta);
        setPosition(getX() + deltaX, getY() + deltaY);
        rectangle.setPosition(direction == 1 ? getX() : player.getX() - width, getY());
        if (animationController.isAnimationFinished()) {
            if(melee) {
                setVisible(true);
                animationController.setTime(0);
            } else {
                setVisible(false);
            }
        }
    }

    @Override
    public void draw(Batch batch, float alpha) {
        PlayerActor playerActor = player.getPlayerActor();
        batch.draw(
                animationController.getKeyFrame(),
                getX() - (direction == -1 ? playerActor.getWidth()*playerActor.getScale() : 0),
                getY(),
                getOriginX(),
                getOriginY(),
                width*playerActor.getScale()*direction,
                height*playerActor.getScale(),
                1,
                1,
                getRotation()
        );
    }

    public TextureRegion getIcon() {
        return icon;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
}
