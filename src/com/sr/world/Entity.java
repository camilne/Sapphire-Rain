package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;

public abstract class Entity {

    protected double x;
    protected double y;
    protected double dx;
    protected double dy;
    protected String name;

    protected Rectangle boundingBox;

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
	this.dx = 0.0;
	this.dy = 0.0;
	this.boundingBox = new Rectangle();
    }

    public abstract void input();

    public void update(final double deltaTime,
	    final LinkedList<Rectangle> colliders) {
	colliders
		.forEach((final Rectangle otherBounds) -> {
		    // Check horizontal collision
		    final Rectangle horizontalCollider = getRelativeBoundingBox();
		    horizontalCollider.x += this.dx * deltaTime;
		    horizontalCollider.y += 1;
		    horizontalCollider.height -= 2;

		    if (horizontalCollider.intersects(otherBounds)) {
			// If traveling right
			if (this.dx > 0.001) {
			    this.dx = Math
				    .max(0,
					    otherBounds.x
						    - (getRelativeBoundingBox().x + getRelativeBoundingBox().width))
				    * deltaTime;
			} else if (this.dx < 0.001) {
			    // Traveling left
			    this.dx = Math
				    .max(0,
					    (getRelativeBoundingBox().x + getRelativeBoundingBox().width)
						    - otherBounds.x)
				    * deltaTime;
			}
		    }

		    // Check vertical collision
		    final Rectangle verticalCollider = getRelativeBoundingBox();
		    verticalCollider.y += this.dy * deltaTime;
		    verticalCollider.x += 1;
		    verticalCollider.width -= 2;

		    if (verticalCollider.intersects(otherBounds)) {
			// If traveling up
			if (this.dy < 0.001) {
			    this.dy = Math
				    .max(0,
					    otherBounds.y
						    - (getRelativeBoundingBox().y + getRelativeBoundingBox().height))
				    * deltaTime;
			} else if (this.dy > 0.001) {
			    // Traveling down
			    this.dy = Math
				    .max(0,
					    (getRelativeBoundingBox().y + getRelativeBoundingBox().height)
						    - otherBounds.y)
				    * deltaTime;
			}
		    }
		});

	this.x += this.dx * deltaTime;
	this.y += this.dy * deltaTime;
    }

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

    public Rectangle getRelativeBoundingBox() {
	final Rectangle relativeBounds = (Rectangle) this.boundingBox.clone();
	relativeBounds.x += this.x;
	relativeBounds.y += this.y;
	return relativeBounds;
    }

    /**
     * Returns whether or not this entity is colliding with another entity.
     * 
     * @param other
     *            the entity to test against
     * @return true if the entities are colliding, false otherwise
     */
    public boolean collides(final Entity other) {
	final Rectangle thisBounds = this.getRelativeBoundingBox();
	final Rectangle otherBounds = other.getRelativeBoundingBox();

	return thisBounds.intersects(otherBounds);
    }

}
