package com.buaisociety.snake;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.buaisociety.snake.behaviors.Behavior;
import com.buaisociety.snake.behaviors.JoystickBehavior;
import com.buaisociety.snake.behaviors.NeatBehavior;
import com.cjcrafter.neat.Neat;
import com.cjcrafter.neat.NeatImpl;
import com.cjcrafter.neat.NeatPrinter;
import com.cjcrafter.neat.NeatSaver;
import com.cjcrafter.neat.Parameters;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private GameLoop gameLoop;
    private Neat neat;
    private NeatPrinter printer;
    private NeatSaver saver;

    private List<Board> games;
    private Vector2i visibleGames = new Vector2i(20, 15);
    private int totalGames = 1000;

    public void createNeat() {
        // TODO: create a neat instance here
        Parameters parameters = new Parameters();
        neat = new NeatImpl(4, 4, totalGames, parameters);
    }

    public @NotNull File getSaveFolder() {
        // Create the "saves" directory if it doesn't exist
        File saveFolder = new File("saves");
        saveFolder.mkdirs();

        // Get the current date formatted as "oct26"
        LocalDate now = LocalDate.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("MMMdd")).toLowerCase();

        // Initialize the maximum number found for the current date
        int maxNumber = 0;

        // List all files in the "saves" directory
        File[] files = saveFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                String name = file.getName();
                // Check if the file name starts with the date pattern
                if (name.startsWith(datePart + "-")) {
                    // Extract the number part after the date
                    String numberPart = name.substring((datePart + "-").length());
                    try {
                        int num = Integer.parseInt(numberPart);
                        if (num > maxNumber) {
                            maxNumber = num;
                        }
                    } catch (NumberFormatException e) {
                        // Ignore files that don't have a valid number suffix
                    }
                }
            }
        }

        // The next available number is maxNumber + 1
        String newFolderName = datePart + "-" + (maxNumber + 1);
        File newFolder = new File(saveFolder, newFolderName);
        newFolder.mkdirs();

        System.out.println("Created folder: " + newFolder.getPath());
        return newFolder;
    }

    @Override
    public void create() {
        Joystick joystick = new Joystick();
        Gdx.input.setInputProcessor(joystick);

        // Instantiate the neat stuff
        createNeat();
        printer = new NeatPrinter(neat);
        saver = new NeatSaver(neat, getSaveFolder());

        // Instantiate all the games
        games = new ArrayList<>();
        for (int i = 0; i < totalGames; i++) {
            Behavior behavior = new NeatBehavior(neat.getClients().get(i));
            Board board = new Board(24, 24, behavior);
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
        boolean anyAlive = true;
        if (gameLoop.update()) {
            anyAlive = false;
            for (Board board : games) {
                if (board.getSnake().isDead())
                    continue;

                anyAlive = true;
                board.update();
            }
        }

        // If all games are dead, evolve the population
        if (!anyAlive) {
            neat.evolve();
            System.out.println(printer.render());
            saver.save();

            // Remake all games
            games.clear();
            for (int i = 0; i < totalGames; i++) {
                Behavior behavior = new NeatBehavior(neat.getClients().get(i));
                Board board = new Board(24, 24, behavior);
                games.add(board);
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

            // Checkerboard pattern
            if ((x + y) % 2 == 0) {
                RenderUtil.drawRect(batch, 0, 0, 24, 24, Color.DARK_GRAY);
            }

            board.render(batch);
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
