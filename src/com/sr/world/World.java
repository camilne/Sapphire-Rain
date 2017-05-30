package com.sr.world;

import java.awt.Graphics;
import java.util.HashSet;

public class World {

    // Holds all the entities in the world. Does not allow duplicates
    private final HashSet<Entity> entities;

    /**
     * Creates an instance of the world with no level nor entities
     */
    public World() {
	this.entities = new HashSet<>();
    }

    /**
     * Updates everything in the world
     */
    public void update() {
	this.entities.forEach((Entity e) -> {
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
	this.entities.forEach((Entity e) -> {
	    e.render(g);
	});
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
     * Returns the number of entities present in the world
     * 
     * @return
     */
    public final int getEntityCount() {
	return this.entities.size();
    }

}
