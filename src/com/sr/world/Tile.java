package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

import com.sr.asset.TextureAtlas;

public class Tile {

    // The prefix to use in the TextureAtlas
    public static final String PREFIX = "tile";
    // The size of the tile in the game world
    public static final int SIZE = 64;
    // The size of the tile in the texture atlas
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

    /**
     * Sets the type of the tile
     * 
     * @param type
     *            the type of the tile
     */
    public void setType(final Type type) {
	this.type = type;
	this.name = PREFIX + type.ordinal();
    }

    /**
     * Holds the type information of the tile. Holds information regarding the
     * colliding rectangles of the tile type.
     */
    public enum Type {
	EMPTY(new Rectangle()), TOP_LEFT_EDGE(new Rectangle(0, 0, 6, 23),
		new Rectangle(0, 0, 23, 6)), TOP_EDGE(
		new Rectangle(0, 0, 23, 6)), TOP_RIGHT_EDGE(new Rectangle(0, 0,
		23, 6), new Rectangle(17, 0, 6, 23)), LEFT_EDGE(new Rectangle(
		0, 0, 6, 23)), RIGHT_EDGE(new Rectangle(17, 0, 6, 23)), BOTTOM_LEFT_EDGE(
		new Rectangle(0, 0, 6, 23), new Rectangle(0, 17, 23, 6)), BOTTOM_EDGE(
		new Rectangle(0, 17, 23, 6)), BOTTOM_RIGHT_EDGE(new Rectangle(
		0, 17, 23, 6), new Rectangle(17, 0, 6, 23));

	private LinkedList<Rectangle> colliders;

	/**
	 * Create a type with the specified colliding rectangles
	 * 
	 * @param colliders
	 *            the colliding rectangles
	 */
	Type(final Rectangle... colliders) {
	    this.colliders = new LinkedList<>();
	    final double scale = (double) SIZE / IMG_SIZE;

	    for (int i = 0; i < colliders.length; i++) {
		final Rectangle collision = colliders[i];
		collision.x *= scale;
		collision.y *= scale;
		collision.width *= scale;
		collision.height *= scale;
		this.colliders.add(collision);
	    }
	}

	/**
	 * Returns all the colliding rectangles associated with this tile type
	 * 
	 * @return a linked list of rectangle colliders
	 */
	public LinkedList<Rectangle> colliders() {
	    final LinkedList<Rectangle> result = new LinkedList<>();
	    this.colliders.forEach((final Rectangle collider) -> {
		result.add((Rectangle) collider.clone());
	    });
	    return result;
	}
    }

}
