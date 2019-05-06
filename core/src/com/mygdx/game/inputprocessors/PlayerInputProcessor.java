package com.mygdx.game.inputprocessors;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class PlayerInputProcessor implements ApplicationListener, InputProcessor {
    private Vector2 mouseVector;
    private int scrolled = 0;
    private float deltaX = 0, deltaY = 0, mouseX = 0, mouseY = 0;
    private float speed;
    private boolean melee;

    public PlayerInputProcessor(float x, float y, float speed) {
        System.out.println("creating input processor");
        this.speed = speed;
        mouseVector = new Vector2(mouseX - x, mouseY - y);
        Gdx.input.setInputProcessor(this);
    }

    public class PlayerInput {
        public int direction, scrolled;
        public float deltaX, deltaY;
        public boolean melee;

        public PlayerInput(float deltaX, float deltaY, int direction, int scrolled, boolean melee) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
            this.direction = direction;
            this.scrolled = scrolled;
            this.melee = melee;
        }
    }

    public PlayerInput getMovement(float x, float y, float leftBound, float rightBound, float bottomBound, float topBound) {
        mouseVector.set(mouseX - x, Gdx.graphics.getHeight() - mouseY);
        float angle = mouseVector.angle();
        PlayerInput input = new PlayerInput(
                x + deltaX <= rightBound  && x + deltaX >= leftBound ? deltaX : 0,
                y + deltaY <= topBound && y + deltaY >= bottomBound ? deltaY : 0,
                angle <= 90 || angle >= 270 ? 1 : -1,
                scrolled,
                melee
        );
        scrolled = 0;
        return input;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                deltaY += speed;
                break;
            case Input.Keys.S:
                deltaY -= speed;
                break;
            case Input.Keys.D:
                deltaX += speed;
                break;
            case Input.Keys.A:
                deltaX -= speed;
                break;
            case Input.Keys.RIGHT:
                scrolled += 1;
                break;
            case Input.Keys.LEFT:
                scrolled -= 1;
                break;
            case Input.Keys.J:
                melee = true;
                break;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                deltaY -= speed;
                break;
            case Input.Keys.S:
                deltaY += speed;
                break;
            case Input.Keys.D:
                deltaX -= speed;
                break;
            case Input.Keys.A:
                deltaX += speed;
                break;
            case Input.Keys.J:
                melee = false;
                break;
        }
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        scrolled += amount;
        return false;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeed() {
        return speed;
    }

    public Vector2 getMouseVector() {
        return mouseVector;
    }

    @Override
    public void create() {
    }

    @Override
    public void render() {
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseX = screenX;
        mouseY = screenY;
        return false;
    }

    @Override
    public void dispose(){
    }
}
