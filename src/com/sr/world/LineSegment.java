package com.sr.world;

import java.awt.Color;
import java.awt.Graphics;

public class LineSegment {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private double t;

    public LineSegment(final double x, final double y, final double dx,
	    final double dy) {
	this.x = x;
	this.y = y;
	this.dx = dx;
	this.dy = dy;
	this.t = 1.0;
    }

    /**
     * Returns whether this line segment as a ray, intersects the other line
     * segment (as a line segment).
     * 
     * @param other
     *            the line segment
     * @return the distance from the start of this ray to the intersection point
     *         (if any)
     */
    public double intersection(final LineSegment other) {
	// Check if lines are parallel
	if (Double.compare(this.dx, other.dx) == 0
		&& Double.compare(this.dy, other.dy) == 0) {
	    return Double.MAX_VALUE;
	}

	// Check for intersection point
	final double t2denom = other.dx * this.dy - other.dy * this.dx;
	if (t2denom == 0) {
	    return Double.MAX_VALUE; // TODO: think of a better resolution for
				     // divide by zero
	}
	final double t2 = (this.dx * (other.y - this.y) + this.dy
		* (this.x - other.x))
		/ t2denom;

	final double t1denom = this.dx;
	if (t1denom == 0) {
	    return Double.MAX_VALUE; // TODO: think of a better resolution for
				     // divide by zero
	}
	final double t1 = (other.x + other.dx * t2 - this.x) / this.dx;

	// Intersection occurred. Return t1 to compare for the smallest
	if (t1 >= 0.0 && t2 >= 0.0 && t2 <= 1.0) {
	    if (t1 < this.t) {
		this.t = t1;
	    }

	    return t1;
	}

	return Double.MAX_VALUE;
    }

    public void render(final Graphics g) {
	final int x1 = (int) this.x;
	final int y1 = (int) this.y;
	final int x2 = x1 + (int) (this.dx * this.t);
	final int y2 = y1 + (int) (this.dy * this.t);
	g.setColor(Color.RED);
	g.drawLine(x1, y1, x2, y2);
    }

    public void setIntersectingPoint(final double px, final double py) {
	this.dx = px - this.x;
	this.dy = py - this.y;
    }

    public void setX(final double x) {
	this.x = x;
    }

    public void setY(final double y) {
	this.y = y;
    }

    public double getT() {
	return this.t;
    }

    public void setT(final double t) {
	this.t = t;
    }
}
