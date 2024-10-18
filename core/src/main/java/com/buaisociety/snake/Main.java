package com.buaisociety.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.buaisociety.snake.behaviors.JoystickBehavior;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameLoop gameLoop;

    private List<Board> games;
    private Vector2i visibleGames = new Vector2i(20, 15);
    private int totalGames = 1000;

    @Override
    public void create() {
        Joystick joystick = new Joystick();
        Gdx.input.setInputProcessor(joystick);

        games = new ArrayList<>();
        for (int i = 0; i < totalGames; i++) {
            Board board = new Board(24, 24, new JoystickBehavior(joystick));
            games.add(board);
        }
        batch = new SpriteBatch();

        // Initialize the camera with a viewport of 24x24 units
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 24 * visibleGames.x(), 24 * visibleGames.y());

        gameLoop = new GameLoop(5.0f);
    }

    @Override
    public void render() {
        // Only update the game state when enough time has passed
        if (gameLoop.update()) {
            for (Board board : games) {
                if (board.getSnake().isDead())
                    continue;

                board.update();
            }
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        // Update the camera and set the batch's projection matrix
        //camera.update();
        batch.begin();

        int renderCount = 0;
        for (Board board : games) {
            if (renderCount >= visibleGames.x * visibleGames.y)
                break;

            int x = renderCount % visibleGames.x;
            int y = renderCount / visibleGames.x;
            renderCount++;

            batch.setProjectionMatrix(camera.combined.cpy().translate(x * 24, y * 24, 0));
            board.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
