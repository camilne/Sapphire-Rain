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
     * @param y
     */
    public Entity(final double x, final double y) {
	this(x, y, "");
    }

    /**
     * Creates an entity with the given name at (x,y)
     * 
     * @param x
     * @param y
     * @param name
     */
    public Entity(final double x, final double y, final String name) {
	this.x = x;
	this.y = y;
	this.name = name;
    }

    public abstract void update();

    public abstract void render(final Graphics g);

    public final double getX() {
	return this.x;
    }

    public final double getY() {
	return this.y;
    }

    public final String getName() {
	return this.name;
    }

}
