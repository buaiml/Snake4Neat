package com.buaisociety.snake;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.jetbrains.annotations.Nullable;

/**
 * Treats keyboard input as a joystick input.
 */
public class Joystick implements InputProcessor {

    private Direction direction;

    public Joystick() {
    }

    public @Nullable Direction getDirection() {
        return direction;
    }

    public @Nullable Direction popDirection() {
        Direction direction = this.direction;
        this.direction = null;
        return direction;
    }

    @Override
    public boolean keyDown(int keycode) {
        Direction direction = null;
        switch (keycode) {
            case Input.Keys.UP, Input.Keys.W -> direction = Direction.UP;
            case Input.Keys.DOWN, Input.Keys.S -> direction = Direction.DOWN;
            case Input.Keys.LEFT, Input.Keys.A -> direction = Direction.LEFT;
            case Input.Keys.RIGHT, Input.Keys.D -> direction = Direction.RIGHT;
        }

        // No keyboard input that we care about...
        if (direction == null)
            return false;

        this.direction = direction;
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
