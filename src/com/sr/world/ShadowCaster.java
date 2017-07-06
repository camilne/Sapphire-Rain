package com.sr.world;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sr.main.Main;

public class ShadowCaster {

    private ArrayList<LineSegment> rays;

    public ShadowCaster() {
	this.rays = new ArrayList<>();

	/*
	 * final double rayLength = 1.0; for (int i = 0; i < 50; i++) { final
	 * double x = 0.0; final double y = 0.0; final double dx = rayLength *
	 * Math.cos(i / 50.0 * 2.0 * Math.PI); final double dy = rayLength *
	 * Math.sin(i / 50.0 * 2.0 * Math.PI); this.rays.add(new LineSegment(x,
	 * y, dx, dy)); }
	 */
    }

    public void cast(final double sourceX, final double sourceY,
	    final LinkedList<Rectangle> colliders) {
	this.rays.clear();
	colliders.forEach((final Rectangle collider) -> {
	    final Point[] points = getPoints(collider);

	    for (int i = 0; i < points.length; i++) {
		final LineSegment ray = new LineSegment(sourceX, sourceY, 0.0,
			0.0);

		// Send ray to collider corner
		ray.setIntersectingPoint(points[i].x, points[i].y);
		this.rays.add(ray);

		// Send rays adjacent to corner to hit walls behind
		final LineSegment ray2 = new LineSegment(sourceX, sourceY, 0.0,
			0.0);
		ray2.setIntersectingPoint(points[i].x + 1, points[i].y + 1);
		this.rays.add(ray2);

		final LineSegment ray3 = new LineSegment(sourceX, sourceY, 0.0,
			0.0);
		ray3.setIntersectingPoint(points[i].x - 1, points[i].y - 1);
		this.rays.add(ray3);
	    }
	});

	this.rays.forEach((final LineSegment ray) -> {
	    ray.setT(Double.MAX_VALUE);

	    colliders.forEach((final Rectangle collider) -> {
		final LineSegment[] segments = getSegments(collider);

		for (int i = 0; i < segments.length; i++) {
		    ray.intersection(segments[i]);
		}
	    });
	});

    }

    private static LineSegment[] getSegments(final Rectangle r) {
	final LineSegment[] segments = new LineSegment[4];
	// Top
	segments[0] = new LineSegment(r.getX(), r.getY(), r.getWidth(), 0.0);
	// Bottom
	segments[1] = new LineSegment(r.getX(), r.getY() + r.getHeight(),
		r.getWidth(), 0.0);
	// Left
	segments[2] = new LineSegment(r.getX(), r.getY(), 0.0, r.getHeight());
	// Right
	segments[3] = new LineSegment(r.getX() + r.getWidth(), r.getY(), 0.0,
		r.getHeight());

	return segments;
    }

    private static Point[] getPoints(final Rectangle r) {
	final Point[] points = new Point[4];
	// Top left
	points[0] = new Point((int) r.getX(), (int) r.getY());
	// Top right
	points[1] = new Point((int) (r.getX() + r.getWidth()), (int) r.getY());
	// Bottom right
	points[2] = new Point((int) (r.getX() + r.getWidth()),
		(int) (r.getY() + r.getHeight()));
	// Bottom left
	points[3] = new Point((int) r.getX(), (int) (r.getY() + r.getHeight()));

	return points;
    }

    public void render(final Graphics g) {

	// Debug rays
	if (Main.DEBUG) {
	    this.rays.forEach((final LineSegment segment) -> {
		segment.render(g);
	    });
	}
    }

}
