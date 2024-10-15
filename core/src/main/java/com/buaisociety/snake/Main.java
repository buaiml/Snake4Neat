package com.buaisociety.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Board board;
    private OrthographicCamera camera;
    private GameLoop gameLoop;

    @Override
    public void create() {
        Joystick joystick = new Joystick();
        Gdx.input.setInputProcessor(joystick);

        batch = new SpriteBatch();
        board = new Board(24, 24, new JoystickBehavior(joystick));
        board.getSnake().setColor(Color.GREEN);

        // Initialize the camera with a viewport of 24x24 units
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 24, 24);

        gameLoop = new GameLoop(5.0f);
    }

    @Override
    public void render() {
        // Only update the game state when enough time has passed
        if (gameLoop.update()) {
            board.update();
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Update the camera and set the batch's projection matrix
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        board.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
