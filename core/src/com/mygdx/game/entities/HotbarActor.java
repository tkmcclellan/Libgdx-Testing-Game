package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class HotbarActor extends Actor {
    private BitmapFont font;
    private static int numBoxes;
    private TextureRegion[] icons;
    private ShapeRenderer shapeRenderer;
    private int selectedBox = 0, x, y, boxWidth;

    public HotbarActor(int numBoxes) {
        super();
        this.numBoxes = numBoxes;
        int windowWidth = Gdx.graphics.getWidth();
        int windowHeight = Gdx.graphics.getHeight();

        boxWidth = windowWidth / 6 * 4 / 10;
        x = (windowWidth / 6);
        y = windowHeight - boxWidth - 5;
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont();
        icons = new TextureRegion[numBoxes];
    }

    public void update(int selected) {
        selectedBox = (selectedBox + selected + numBoxes) % numBoxes;
    }

    public int getNumBoxes() {
        return numBoxes;
    }

    @Override
    public void draw(Batch batch, float alpha) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        font.setColor(Color.WHITE);
        for (int i = 0; i < numBoxes; i++) {
            if (i != selectedBox) {
                shapeRenderer.rect(x + i * boxWidth, y, boxWidth, boxWidth);
                font.draw(batch, Integer.toString((i+1)%numBoxes), x + i*boxWidth + 5, y + boxWidth - 5);
            }

            if (icons[i] != null) {
                batch.draw(icons[i], x + i * boxWidth + boxWidth*0.2f, y + boxWidth * 0.2f, icons[i].getRegionWidth() * 0.6f, icons[i].getRegionHeight() * 0.6f);
            }
        }
        shapeRenderer.setColor(Color.RED);
        font.setColor(Color.RED);
        shapeRenderer.rect(x + selectedBox*boxWidth, y, boxWidth, boxWidth);
        font.draw(batch, Integer.toString((selectedBox+1)%numBoxes), x + selectedBox*boxWidth + 5, y + boxWidth - 5);
        if (icons[selectedBox] != null) batch.draw(icons[selectedBox], x + selectedBox * boxWidth + boxWidth*0.2f, y + boxWidth * 0.2f, icons[selectedBox].getRegionWidth() * 0.6f, icons[selectedBox].getRegionHeight() * 0.6f);
        shapeRenderer.end();
    }

    @Override
    public boolean remove() {
        font.dispose();
        shapeRenderer.dispose();
        return super.remove();
    }

    public void setBox(TextureRegion texture, int index) {
        if (index == -1) {
            for (int i = 0; i < icons.length; i++) {
                if (icons[i] == null) {
                    icons[i] = texture;
                    index = i;
                    break;
                }
            }
        } else if (index >= 0 && index < icons.length) {
            icons[index] = texture;
        }
    }

    public void removeBox(int index) {
        if (index >= 0 && index < icons.length) {
            icons[index] = null;
        }
    }

}
