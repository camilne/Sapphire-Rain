package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.sr.asset.TextureAtlas;

public class Tile {

    // The prefix to use in the TextureAtlas
    public static final String PREFIX = "tile";
    // The size of the tile in the game world
    public static final int SIZE = 64;

    private static final int IMG_SIZE = 23;

    // The name of the tile for the texture atlas
    private String name;
    // The type of tile
    private Type type;

    /**
     * Creates a new tile with the specified name for the TextureAtlas and of
     * the specified type.
     * 
     * @param name
     *            the name for the TextureAtlas
     * @param type
     *            the type of tile
     */
    protected Tile(final String name, final Type type) {
	this.name = name;
	this.type = type;
    }

    /**
     * Renders the tile at the specified coordinates to the graphics context
     * from the TextureAtlas.
     * 
     * @param g
     *            the graphics context to render to
     * @param atlas
     *            the atlas to get the graphics from
     * @param x
     *            the x coordinate in pixels
     * @param y
     *            the y coordinate in pixels
     */
    public void render(final Graphics g, final TextureAtlas atlas, final int x,
	    final int y) {
	g.drawImage(atlas.getTexture(this.name), x * SIZE, y * SIZE, SIZE,
		SIZE, null);
    }

    /**
     * Returns the TextureAtlas name of the tile
     * 
     * @return the TextureAtlas name of the tile
     */
    protected String getName() {
	return this.name;
    }

    /**
     * Returns the type of the tile
     * 
     * @return the type of the tile
     */
    public Type getType() {
	return this.type;
    }

    public enum Type {
	EMPTY(0, 0, 0, 0), TOP_LEFT_EDGE(0, 0, 6, 23);

	private Rectangle collision;

	Type(final int x, final int y, final int width, final int height) {
	    this.collision = new Rectangle(x, y, width, height);
	    final double scale = (double) SIZE / IMG_SIZE;
	    this.collision.x *= scale;
	    this.collision.y *= scale;
	    this.collision.width *= scale;
	    this.collision.height *= scale;
	}

	public Rectangle collision() {
	    return (Rectangle) this.collision.clone();
	}
    }

}
