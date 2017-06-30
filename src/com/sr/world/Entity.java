package com.sr.world;

import java.awt.Graphics;

public abstract class Entity {

    protected double x;
    protected double y;
    protected String name;

    /**
     * Creates an unnamed entity at (0,0)
     */
    public Entity() {
	this(0, 0);
    }

    /**
     * Creates an unnamed entity at (x,y)
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     */
    public Entity(final double x, final double y) {
	this(x, y, "");
    }

    /**
     * Creates an entity with the given name at (x,y)
     * 
     * @param x
     *            x-coordinate
     * @param y
     *            y-coordinate
     * @param name
     *            screen name of the entity
     */
    public Entity(final double x, final double y, final String name) {
	this.x = x;
	this.y = y;
	this.name = name;
    }

    public abstract void update(final double deltaTime);

    public abstract void render(final Graphics g);

    /**
     * Returns the x-coordinate for this entity
     * 
     * @return the x-coordinate for this entity
     */
    public final double getX() {
	return this.x;
    }

    /**
     * Returns the y-coordinate for this entity
     * 
     * @return the y-coordinate for this entity
     */
    public final double getY() {
	return this.y;
    }

    /**
     * Returns the name of this entity
     * 
     * @return the name of this entity
     */
    public final String getName() {
	return this.name;
    }

}
