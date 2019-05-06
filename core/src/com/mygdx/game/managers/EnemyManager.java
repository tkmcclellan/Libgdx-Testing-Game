package com.mygdx.game.managers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.mygdx.game.entities.enemies.EnemyActor;
import com.mygdx.game.entities.player.Player;

public class EnemyManager extends Group {
    private Player playerController;
    private int numEnemies = 0;

    public EnemyManager(Player p) {
        super();

        playerController = p;
    }

    @Override
    public void addActor(Actor actor) {
        numEnemies += 1;
        super.addActor(actor);
    }

    @Override
    public void act(float delta) {
        if (playerController.isAttacking()) {
            for (Actor enemy : getChildren()) {
                if (collides((EnemyActor)enemy)) {
                    enemy.remove();
                    numEnemies -= 1;
                }
            }
        }
        super.act(delta);
    }

    private boolean collides(EnemyActor enemy) {
        return playerController.getSwooshActor().getRectangle().overlaps(enemy.getRectangle());
    }

    public int getNumEnemies() {
        return numEnemies;
    }
}
