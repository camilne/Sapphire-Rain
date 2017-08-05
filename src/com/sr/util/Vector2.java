package com.sr.util;

public class Vector2 {

    public double x;
    public double y;

    /**
     * Creates a new Vector with components (0, 0).
     */
    public Vector2() {
	this(0, 0);
    }

    /**
     * Creates a new Vector with components <code>x</code> and <code>y</code>.
     * 
     * @param x
     *            The x component
     * @param y
     *            The y component
     */
    public Vector2(final double x, final double y) {
	this.x = x;
	this.y = y;
    }

    /**
     * Returns the magnitude of the Vector. The magnitude is computed using by
     * taking the square root of the sum of the squares of the components.
     * 
     * @return The magnitude of the vector
     */
    public double magnitude() {
	return Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Normalizes the vector. Normalizing is when the vector is made to have a
     * length of one.
     * 
     * @return An instance of this vector for chaining.
     */
    public Vector2 normalize() {
	final double magnitude = magnitude();

	if (magnitude != 0.0) {
	    this.x /= magnitude;
	    this.y /= magnitude;
	}

	return this;
    }

}
