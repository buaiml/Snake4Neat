package com.buaisociety.snake.behaviors;

import com.buaisociety.snake.Direction;
import com.cjcrafter.neat.Client;

public class NeatBehavior extends Behavior {

    private Client client;

    public NeatBehavior(Client client) {
        this.client = client;
    }

    @Override
    public Direction getDirection() {
        // TODO: implement this
        return Direction.RIGHT;
    }
}
