package com.buaisociety.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.buaisociety.snake.behaviors.Behavior;
import org.joml.Vector2i;
import org.joml.Vector2ic;

import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 * Represents a snake in the game. The snake is stored as a queue of positions.
 * The head of the snake is the first element in the queue, and the tail is the
 * last element. The snake can move by adding a new head to the front of the
 * queue and removing the tail. The snake can grow by not removing the tail.
 *
 * <p>You can iterate over the snake's body segments using a for-each loop:
 * <pre>{@code
 *     Snake snake = new Snake(3);
 *     snake.add(new Vector2i(0, 0));  // will be our tail
 *     snake.add(new Vector2i(1, 0));
 *     snake.add(new Vector2i(2, 0));  // will be our head
 *     for (Vector2i segment : snake) {
 *         System.out.println(segment);
 *     }
 * }</pre>
 */
public class Snake implements Iterable<Vector2i> {

    private final Board board;
    private final Deque<Vector2i> bodySegments;
    private final Set<Vector2i> positions;
    private int maxLength;
    private Direction currentDirection;
    private Behavior behavior;
    private Color color;
    private boolean isDead;

    /**
     * Creates a new snake with the given maximum length.
     *
     * @param board The board that the snake is on.
     * @param maxLength The starting maximum length of the snake.
     * @param behavior The behavior of the snake.
     */
    public Snake(Board board, int maxLength, Behavior behavior) {
        this.board = board;
        this.maxLength = maxLength;
        this.bodySegments = new LinkedList<>();
        this.positions = new HashSet<>();
        this.currentDirection = Direction.RIGHT;
        this.behavior = behavior;
        this.color = Color.WHITE;
    }

    /**
     * Returns the board that the snake is on.
     *
     * @return the board that the snake is on.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Returns the current max length of the snake.
     *
     * @return the current max length of the snake.
     */
    public int getMaxLength() {
        return maxLength;
    }

    /**
     * Sets the max length of the snake. The new max length must be greater than the
     * current length of the snake, else an {@link IllegalArgumentException} will be
     * thrown.
     *
     * @param maxLength the new max length of the snake.
     */
    public void setMaxLength(int maxLength) {
        if (maxLength < this.maxLength)
            throw new IllegalArgumentException("Cannot set max length to less than current length");

        this.maxLength = maxLength;

    }

    /**
     * Returns the current direction of the snake. This is the direction that
     * the snake is moving in. The snake will move in this direction when
     * {@link #update()} is called.
     *
     * @return the current direction of the snake.
     */
    public Direction getCurrentDirection() {
        return currentDirection;
    }

    /**
     * Sets the current direction of the snake. The snake will move in this
     * direction when {@link #update()} is called.
     *
     * @param currentDirection the new direction of the snake.
     */
    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    /**
     * Returns the behavior of the snake. The behavior determines how the snake
     * should move/change directions.
     *
     * @return the behavior of the snake.
     */
    public Behavior getBehavior() {
        return behavior;
    }

    /**
     * Sets the behavior of the snake. The behavior determines how the snake
     * should move/change directions.
     *
     * @param behavior the new behavior of the snake.
     */
    public void setBehavior(Behavior behavior) {
        this.behavior = behavior;
    }

    /**
     * Sets the color of the snake. The color will be used to render the snake.
     *
     * @param color the new color of the snake.
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Returns whether the snake is dead.
     *
     * @return whether the snake is dead.
     */
    public boolean isDead() {
        return isDead;
    }

    /**
     * Returns the current position of the head of the snake.
     *
     * @return the current position of the head of the snake.
     */
    public Vector2ic getHead() {
        return bodySegments.getFirst();
    }

    /**
     * Returns whether the snake contains the given position.
     *
     * @param position the position to check.
     * @return whether the snake contains the given position.
     */
    public boolean contains(Vector2i position) {
        return positions.contains(position);
    }

    /**
     * "Moves" the snake by adding a new head to the front of the snake and removing
     * the tail. If the snake is at its maximum length, the tail will be removed
     * after the new head is added.
     *
     * @param newHead the new head of the snake.
     */
    public void add(Vector2i newHead) {
        if (contains(newHead)) {
            throw new IllegalArgumentException("Cannot move to a position already occupied by the snake");
        }

        // Add new head to the front of the deque and the positions set
        bodySegments.addFirst(new Vector2i(newHead));
        positions.add(new Vector2i(newHead));

        // If the snake exceeds its maximum length, remove the tail
        if (bodySegments.size() > maxLength) {
            Vector2i tail = bodySegments.removeLast();
            positions.remove(tail);
        }
    }

    /**
     * Moves the snake in the current direction.
     */
    public void update() {
        currentDirection = behavior.getDirection();
        Vector2i newHead = currentDirection.getVector().add(getHead());

        // Wrap new locations around the board
        if (newHead.x() < 0) {
            newHead.x = board.getWidth() - 1;
        } else if (newHead.x() >= board.getWidth()) {
            newHead.x = 0;
        }

        if (newHead.y() < 0) {
            newHead.y = board.getHeight() - 1;
        } else if (newHead.y() >= board.getHeight()) {
            newHead.y = 0;
        }

        if (contains(newHead)) {
            isDead = true;
            return;
        }

        add(newHead);
    }

    /**
     * Renders the snake to the screen.
     *
     * @param batch the sprite batch to render to.
     */
    public void render(SpriteBatch batch) {
        for (Vector2i segment : bodySegments) {
            RenderUtil.drawPixel(batch, segment.x(), segment.y(), color);
        }
    }

    @Override
    public Iterator<Vector2i> iterator() {
        return bodySegments.iterator();
    }
}
