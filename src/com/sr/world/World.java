package com.sr.world;

import java.awt.Graphics;
import java.util.HashSet;

public class World {

    // Holds all the entities in the world. Does not allow duplicates
    private final HashSet<Entity> entities;
    // Holds the position of the world relative to the game window
    private double x;
    private double y;

    /**
     * Creates an instance of the world with no level nor entities
     */
    public World() {
	this.entities = new HashSet<>();
	this.x = 0;
	this.y = 0;
    }

    /**
     * Updates everything in the world
     */
    public void update() {
	this.entities.forEach((final Entity e) -> {
	    e.update();
	});
    }

    /**
     * Renders everything in the world
     * 
     * @param g
     *            The graphics context from which to render
     */
    public void render(final Graphics g) {
	// Translates the graphics origin to match that of the world offset
	g.translate((int) this.x, (int) this.y);

	this.entities.forEach((final Entity e) -> {
	    e.render(g);
	});

	// Restores the graphics origin
	g.translate((int) (-this.x), (int) (-this.y));
    }

    /**
     * Adds an entity to the world if it is not already in the world
     * 
     * @param e
     *            The entity to add to the world
     * @return True if the entity was added to the world, false otherwise
     */
    public boolean addEntity(final Entity e) {
	return this.entities.add(e);
    }

    /**
     * Removes an entity from the world if it exists
     * 
     * @param e
     *            The entity to remove from the world
     * @return True if the entity was removed, false otherwise
     */
    public boolean removeEntity(final Entity e) {
	return this.entities.remove(e);
    }

    /**
     * Translates the world from (x,y)->(x+dx,y+dy)
     * 
     * @param dx
     * @param dy
     */
    public void translate(final double dx, final double dy) {
	this.x += dx;
	this.y += dy;
    }

    /**
     * Returns the number of entities present in the world
     * 
     * @return
     */
    public final int getEntityCount() {
	return this.entities.size();
    }

    /**
     * Returns the x-offset of the world
     * 
     * @return
     */
    public final double getX() {
	return this.x;
    }

    /**
     * Returns the y-offset of the world
     * 
     * @return
     */
    public final double getY() {
	return this.y;
    }

}