package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;

import com.sr.main.Main;

public class World {

    // Holds all the entities in the world. Does not allow duplicates
    private final HashSet<Entity> entities;
    // Holds the position of the world relative to the game window
    private double x;
    private double y;
    // The tiles of the current level
    private Level currentLevel;
    // The shadow caster
    private ShadowCaster shadowCaster;

    /**
     * Creates an instance of the default world with the default level but no
     * entities
     * 
     * @throws IOException
     *             if the default level is not found
     * 
     */
    public World() throws IOException {
	this.entities = new HashSet<>();
	this.x = 0;
	this.y = 0;

	// Create the level
	this.currentLevel = LevelLoader.loadLevel("level1");

	// Add enemies to world
	final Enemy[] enemies = this.currentLevel.enemies;
	for (int i = 0; i < enemies.length; i++) {
	    this.entities.add(enemies[i]);
	}

	// Create the shadow caster
	this.shadowCaster = new ShadowCaster();
    }

    /**
     * Gather input from every entity.
     */
    public void input() {
	this.entities.forEach((final Entity e) -> {
	    e.input();
	});
    }

    /**
     * Updates everything in the world
     */
    public void update(final double deltaTime, final double sourceX,
	    final double sourceY) {
	final LinkedList<Rectangle> colliders = this.currentLevel
		.getColliders();

	this.entities.forEach((final Entity e) -> {
	    e.update(deltaTime, colliders);
	});

	this.shadowCaster.cast(sourceX, sourceY, colliders);
    }

    /**
     * Renders everything in the world
     * 
     * @param g
     *            The graphics context from which to render
     */
    public void render(final Graphics g, final double sourceX,
	    final double sourceY) {
	// Translates the graphics origin to match that of the world offset
	g.translate((int) this.x, (int) this.y);

	// Render the tiles
	this.currentLevel.renderBackground(g);

	this.entities.forEach((final Entity e) -> {
	    e.render(g);
	});

	this.shadowCaster.render(g, this.currentLevel.getWidth() * Tile.SIZE,
		this.currentLevel.getHeight() * Tile.SIZE, sourceX, sourceY);

	this.currentLevel.render(g);

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
     * Removes all the entities from the world
     */
    public void removeAllEntities() {
	this.entities.clear();
    }

    /**
     * Translates the world from (x,y) to (x+dx,y+dy)
     * 
     * @param dx
     *            the delta x offset
     * @param dy
     *            the delta y offset
     */
    public void translate(final double dx, final double dy) {
	this.x += dx;
	this.y += dy;
    }

    /**
     * Sets the translation of the world to center at (x, y)
     * 
     * @param x
     *            the x offset
     * @param y
     *            the y offset
     */
    public void setTranslation(final double x, final double y) {
	this.x = x + Main.WIDTH / 2.0;
	this.y = y + Main.HEIGHT / 2.0;
    }

    /**
     * Returns the number of entities present in the world
     * 
     * @return the number of entities in the world
     */
    public final int getEntityCount() {
	return this.entities.size();
    }

    /**
     * Returns the x-offset of the world
     * 
     * @return the x-offset of the world
     */
    public final double getX() {
	return this.x;
    }

    /**
     * Returns the y-offset of the world
     * 
     * @return the y-offset of the world
     */
    public final double getY() {
	return this.y;
    }

}
