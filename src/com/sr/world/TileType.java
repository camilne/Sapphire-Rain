package com.sr.world;

import java.awt.Rectangle;
import java.util.LinkedList;

/**
 * Holds the type information of the tile. Holds information regarding the
 * colliding rectangles of the tile type.
 */
public class TileType {
    private int id;
    private LinkedList<Rectangle> colliders;
    private boolean occupied;

    /**
     * Create a type with the specified colliding rectangles
     * 
     * @param colliders
     *            the colliding rectangles
     */
    TileType(final int id, final int imageSize, final boolean occupied,
	    final Rectangle... colliders) {
	this.id = id;
	this.colliders = new LinkedList<>();
	this.occupied = occupied;

	final double scale = (double) Tile.SIZE / imageSize;

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

    /**
     * Returns the id of the tile
     * 
     * @return the integer id of the tile
     */
    public int id() {
	return this.id;
    }

    /**
     * Returns whether or not the tile is occupied for pathfinding.
     * 
     * @return True if the tile is occupied, false otherwise.
     */
    public boolean isOccupied() {
	return this.occupied;
    }
}