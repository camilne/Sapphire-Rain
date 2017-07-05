package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.sr.asset.TextureAtlas;
import com.sr.main.Main;

public class Level {

    // Contains the tile textures
    private TextureAtlas atlas;
    // Contains all the tiles for the level
    // Access is [row][column]
    private Tile[][] tiles;

    public Level(final TextureAtlas atlas, final Tile[][] tiles) {
	this.atlas = atlas;
	this.tiles = tiles;
    }

    /**
     * Renders the tiles in the level at (0, 0)
     * 
     * @param g
     *            the graphics context to render to
     */
    public void render(final Graphics g) {
	for (int r = 0; r < this.tiles.length; r++) {
	    for (int c = 0; c < this.tiles[0].length; c++) {
		this.tiles[r][c].render(g, this.atlas, c, r);
	    }
	}

	// Draws all the colliders for debugging
	if (Main.DEBUG) {
	    getColliders().forEach(
		    (final Rectangle collider) -> {
			g.drawRect(collider.x, collider.y, collider.width,
				collider.height);
		    });
	}
    }

    /**
     * Returns the width of the level in tiles.
     * 
     * @return the width of the level in tiles
     */
    public int getWidth() {
	return this.tiles[0].length;
    }

    /**
     * Returns the height of the level in tiles.
     * 
     * @return the height of the level in tiles
     */
    public int getHeight() {
	return this.tiles.length;
    }

    /**
     * Gets all the colliders associated with this level
     * 
     * @return a linked list of rectangle colliders
     */
    public LinkedList<Rectangle> getColliders() {
	final LinkedList<Rectangle> colliders = new LinkedList<>();

	for (int r = 0; r < this.tiles.length; r++) {
	    for (int c = 0; c < this.tiles[0].length; c++) {
		final LinkedList<Rectangle> collider = this.tiles[r][c]
			.getType().colliders();

		final int row = r;
		final int col = c;

		collider.forEach((final Rectangle part) -> {
		    part.translate(col * Tile.SIZE, row * Tile.SIZE);
		});

		colliders.addAll(collider);
	    }
	}

	return colliders;
    }

}
