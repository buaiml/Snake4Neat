package com.buaisociety.snake;

/**
 * Handles a typical game loop (e.g. a game that updates at a fixed rate). Users
 * of this class should call {@link #update()} as often as possible, and when it
 * returns true, update the game state.
 */
public class GameLoop {

    private final double nanosecondsBetweenTicks;
    private double delta;
    private long lastTime;

    /**
     * Creates a new game loop that updates at the specified rate.
     *
     * @param perSecond The desired number of updates/ticks per second
     */
    public GameLoop(double perSecond) {
        this.nanosecondsBetweenTicks = 1000000000.0 / perSecond;
        this.lastTime = System.nanoTime();
    }

    /**
     * Returns the time in nanoseconds before the next tick should occur.
     *
     * @return The time in nanoseconds before the next tick should occur.
     */
    public long nanoBeforeNextTick() {
        long then = lastTime + (long) nanosecondsBetweenTicks;
        return then - System.nanoTime();
    }

    /**
     * Updates the game loop. This method should be called as often as possible.
     *
     * @return true if the game should update, false otherwise.
     */
    public boolean update() {
        long now = System.nanoTime();
        delta += (now - lastTime) / nanosecondsBetweenTicks;
        lastTime = now;

        if (delta >= 1.0) {
            delta--;
            return true;
        } else {
            return false;
        }
    }
}
