package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import com.sr.asset.TextureAtlas;

public class Level {

    // Contains the tile textures
    private TextureAtlas atlas;
    // Contains all the tiles for the level
    // Access is [row][column]
    private Tile[][] tiles;

    /**
     * Create a new empty level of size <code>width</code> and
     * <code>height</code> with textures from the <code>textureAtlas</code>
     * 
     * @param textureAtlas
     *            the image from which to draw the tiles
     * @param firstTile
     *            where the textureAtlas should start reading in the texture
     *            data from, as well as the width and height of tiles on the
     *            image
     * @param width
     *            the width of the level in tiles
     * @param height
     *            the height of the level in tiles
     */
    public Level(final BufferedImage textureAtlas, final Rectangle firstTile,
	    final int width, final int height) {
	this.atlas = new TextureAtlas(textureAtlas);

	// Register tile textures
	final String prefix = Tile.PREFIX;
	final int amount = Tile.Type.values().length;
	this.atlas.registerRepeated(prefix, firstTile, amount);

	// Create tiles
	this.tiles = new Tile[height][width];
	for (int r = 0; r < this.tiles.length; r++) {
	    for (int c = 0; c < this.tiles[0].length; c++) {
		this.tiles[r][c] = TileFactory
			.create((Math.random() > 0.25 ? Tile.Type.EMPTY
				: Tile.Type.TOP_LEFT_EDGE));
	    }
	}
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
		this.tiles[r][c].render(g, this.atlas, r, c);
	    }
	}

	getColliders().forEach(
		(final Rectangle collider) -> {
		    g.drawRect(collider.x, collider.y, collider.width,
			    collider.height);
		});
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

    public LinkedList<Rectangle> getColliders() {
	final LinkedList<Rectangle> colliders = new LinkedList<>();

	for (int r = 0; r < this.tiles.length; r++) {
	    for (int c = 0; c < this.tiles[0].length; c++) {
		final LinkedList<Rectangle> collider = this.tiles[r][c]
			.getType().colliders();

		final int row = r;
		final int col = c;

		collider.forEach((final Rectangle part) -> {
		    part.translate(row * Tile.SIZE, col * Tile.SIZE);
		});

		colliders.addAll(collider);
	    }
	}

	return colliders;
    }

}
