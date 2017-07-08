package com.sr.world;

import java.awt.Color;
import java.awt.Graphics;

import com.sr.util.Point;

public class LineSegment {

    private double x;
    private double y;
    private double dx;
    private double dy;
    private double t;
    private double angle;

    public LineSegment(final double x, final double y, final double dx,
	    final double dy) {
	this.x = x;
	this.y = y;
	this.dx = dx;
	this.dy = dy;
	this.t = 1.0;
	this.angle = calcAngle();
    }

    public LineSegment(final double x, final double y, final double angle) {
	this.x = x;
	this.y = y;
	this.angle = angle;
	this.dx = calcDx();
	this.dy = calcDy();
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
	this.angle = calcAngle();
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

    public Point getIntersectionPoint() {
	final double px = (this.x + this.dx * this.t);
	final double py = (this.y + this.dy * this.t);

	return new Point(px, py);
    }

    public double getX() {
	return this.x;
    }

    public double getY() {
	return this.y;
    }

    private double calcDx() {
	return Math.cos(this.angle);
    }

    public double getDx() {
	return this.dx;
    }

    private double calcDy() {
	return Math.sin(this.angle);
    }

    public double getDy() {
	return this.dy;
    }

    private double calcAngle() {
	return Math.atan2((float) this.dy, (float) this.dx);
    }

    public double getAngle() {
	return this.angle;
    }
}
