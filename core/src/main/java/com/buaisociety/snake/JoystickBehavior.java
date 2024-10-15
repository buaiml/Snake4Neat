package com.buaisociety.snake;

/**
 * A snake behavior that works off of keyboard input.
 */
public class JoystickBehavior extends Behavior {

    private final Joystick joystick;

    public JoystickBehavior(Joystick joystick) {
        this.joystick = joystick;
    }

    @Override
    public Direction getDirection() {
        Direction direction = joystick.getDirection();
        if (direction != null && snake.getCurrentDirection().opposite() != direction) {
            joystick.popDirection();
            return direction;
        }
        return snake.getCurrentDirection();
    }
}
