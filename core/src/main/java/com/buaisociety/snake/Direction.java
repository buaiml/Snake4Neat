package com.buaisociety.snake;

import org.joml.Vector2i;

/**
 * Represents a cardinal direction. Useful for considering relative directions.
 */
public enum Direction {

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Returns the x-component of this direction. Can be either -1, 0, or 1.
     *
     * @return the x-component of this direction.
     */
    public int getDx() {
        return dx;
    }

    /**
     * Returns the y-component of this direction. Can be either -1, 0, or 1.
     *
     * @return the y-component of this direction.
     */
    public int getDy() {
        return dy;
    }

    /**
     * Returns a new vector with the same components as this direction.
     *
     * @return a new vector with the same components as this direction.
     */
    public Vector2i getVector() {
        return new Vector2i(dx, dy);
    }

    /**
     * Returns the direction that is 90 degrees counter-clockwise from this
     * direction.
     *
     * @return the direction that is 90 degrees counter-clockwise from this.
     */
    public Direction left() {
        return switch (this) {
            case UP -> LEFT;
            case DOWN -> RIGHT;
            case LEFT -> DOWN;
            case RIGHT -> UP;
        };
    }

    /**
     * Returns the direction that is 90 degrees clockwise from this direction.
     *
     * @return the direction that is 90 degrees clockwise from this.
     */
    public Direction right() {
        return switch (this) {
            case UP -> RIGHT;
            case DOWN -> LEFT;
            case LEFT -> UP;
            case RIGHT -> DOWN;
        };
    }

    /**
     * Returns the direction that is 180 degrees from this direction.
     *
     * @return the direction that is 180 degrees from this.
     */
    public Direction opposite() {
        return switch (this) {
            case UP -> DOWN;
            case DOWN -> UP;
            case LEFT -> RIGHT;
            case RIGHT -> LEFT;
        };
    }

    @Override
    public String toString() {
        return "Direction{" +
            "dx=" + dx +
            ", dy=" + dy +
            '}';
    }
}
