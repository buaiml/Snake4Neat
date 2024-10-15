package com.buaisociety.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.Random;

/**
 * Represents a game board
 */
public class Board {

    private final Random random;

    private final int width;
    private final int height;

    private Snake snake;
    private Vector2i foodLocation;

    public Board(int width, int height, Behavior snakeBehavior) {
        this.random = new Random();

        this.width = width;
        this.height = height;

        // Create the snake
        this.snake = new Snake(this, 3, snakeBehavior);
        snakeBehavior.setSnake(snake);

        // Build the snake
        int y = height / 2;
        this.snake.add(new Vector2i(0, y));
        this.snake.add(new Vector2i(1, y));
        this.snake.add(new Vector2i(2, y));
    }

    /**
     * Returns the random number generator used in this board. You can reseed
     * this generator using {@link Random#setSeed(long)}.
     *
     * @return the random number generator.
     */
    public Random getRandom() {
        return random;
    }

    /**
     * Returns the width of the board.
     *
     * @return the width of the board.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the board.
     *
     * @return the height of the board.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the snake on the board.
     *
     * @return the snake on the board.
     */
    public Snake getSnake() {
        return snake;
    }

    /**
     * Returns the location of the food.
     *
     * @return the location of the food.
     */
    public Vector2ic getFoodLocation() {
        return foodLocation;
    }

    /**
     * Sets the location of the food.
     *
     * @param foodLocation The new location of the food.
     */
    public void setFoodLocation(Vector2i foodLocation) {
        this.foodLocation = foodLocation;
    }

    /**
     * Returns a possible new location for the food to spawn. The location is
     * guaranteed to not be inside the snake.
     *
     * @return a possible new location for the food to spawn.
     */
    public Vector2i newFoodLocation() {
        Vector2i newFoodLocation;
        do {
            newFoodLocation = new Vector2i(random.nextInt(width), random.nextInt(height));
        } while (snake.contains(newFoodLocation));
        return newFoodLocation;
    }

    /**
     * Updates the state of the game.
     */
    public void update() {
        // Happens when the Board is first initialized. We don't set the food
        // location in the constructor because we need the snake to be initialized.
        // This also allows the Random number generator to be reseeded.
        if (foodLocation == null) {
            foodLocation = newFoodLocation();
        }

        // When we eat food, we increase the length of the snake and move the food
        if (snake.getHead().equals(foodLocation)) {
            snake.setMaxLength(snake.getMaxLength() + 1);
            foodLocation = newFoodLocation();
        }

        // Move the snake
        snake.update();
    }

    public void render(SpriteBatch batch) {
        if (foodLocation != null) {
            RenderUtil.drawPixel(batch, foodLocation.x, foodLocation.y, Color.RED);
        }

        snake.render(batch);
    }
}
