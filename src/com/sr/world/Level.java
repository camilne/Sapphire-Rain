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
     * <code>height</code> with textures from the <code>textureAtlas</code>.
     * Creates walls around the edges of the level.
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
		this.tiles[r][c] = TileFactory.create(Tile.Type.EMPTY);
	    }
	}
	// Update tiles
	for (int r = 0; r < this.tiles.length; r++) {
	    for (int c = 0; c < this.tiles[0].length; c++) {
		final Tile[][] neighbors = new Tile[3][3];
		// Get above neighbors
		if (r > 0 && c > 0) {
		    neighbors[0][0] = this.tiles[r - 1][c - 1];
		}
		if (r > 0) {
		    neighbors[0][1] = this.tiles[r - 1][c];
		}
		if (r > 0 && c < getWidth() - 1) {
		    neighbors[0][2] = this.tiles[r - 1][c + 1];
		}
		// Get left and right neighbors
		if (c > 0) {
		    neighbors[1][0] = this.tiles[r][c - 1];
		}
		if (c < getWidth() - 1) {
		    neighbors[1][2] = this.tiles[r][c + 1];
		}
		// Get below neighbors
		if (r < getHeight() - 1 && c > 0) {
		    neighbors[2][0] = this.tiles[r + 1][c - 1];
		}
		if (r < getHeight() - 1) {
		    neighbors[2][1] = this.tiles[r + 1][c];
		}
		if (r < getHeight() - 1 && c < getWidth() - 1) {
		    neighbors[2][2] = this.tiles[r + 1][c + 1];
		}

		this.tiles[r][c].setType(getTileType(neighbors));
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
		this.tiles[r][c].render(g, this.atlas, c, r);
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

    /**
     * Returns the type of tile based on surrounding neighbors
     * 
     * @param neighbors
     *            the 3x3 grid of neighboring tiles
     * @return the tile type of the center tile
     */
    private static Tile.Type getTileType(final Tile[][] neighbors) {
	// Top
	if (neighbors[0][1] == null && neighbors[1][0] == null) {
	    return Tile.Type.TOP_LEFT_EDGE;
	} else if (neighbors[0][1] == null && neighbors[1][2] == null) {
	    return Tile.Type.TOP_RIGHT_EDGE;
	} else if (neighbors[0][1] == null) {
	    return Tile.Type.TOP_EDGE;
	}

	// Bottom
	if (neighbors[2][1] == null && neighbors[1][0] == null) {
	    return Tile.Type.BOTTOM_LEFT_EDGE;
	} else if (neighbors[2][1] == null && neighbors[1][2] == null) {
	    return Tile.Type.BOTTOM_RIGHT_EDGE;
	} else if (neighbors[2][1] == null) {
	    return Tile.Type.BOTTOM_EDGE;
	}

	// Left
	if (neighbors[1][0] == null) {
	    return Tile.Type.LEFT_EDGE;
	}

	// Right
	if (neighbors[1][2] == null) {
	    return Tile.Type.RIGHT_EDGE;
	}

	return Tile.Type.EMPTY;
    }

}
