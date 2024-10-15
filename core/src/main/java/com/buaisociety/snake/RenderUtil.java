package com.buaisociety.snake;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Utility class for rendering.
 */
public final class RenderUtil {

    private static final Texture PIXEL;

    static {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        PIXEL = new Texture(pixmap);
        pixmap.dispose();
    }

    private RenderUtil() {
    }

    /**
     * Draws a pixel at the specified location.
     *
     * @param batch The sprite batch to render to.
     * @param x The x-coordinate of the pixel.
     * @param y The y-coordinate of the pixel.
     */
    public static void drawPixel(SpriteBatch batch, int x, int y, Color color) {
        batch.setColor(color);
        batch.draw(PIXEL, x, y);
        // Reset tint color
        batch.setColor(Color.WHITE);
    }
}
