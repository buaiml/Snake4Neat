package com.buaisociety.snake;

/**
 * Determines the behavior of the snake (how it should move/change directions).
 */
public abstract class Behavior {

    protected Snake snake;

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    /**
     * Returns the desired direction that the snake should move towards.
     *
     * @return the desired direction for the snake
     */
    public abstract Direction getDirection();
}
