package com.sr.world;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sr.main.Main;

public class ShadowCaster {

    private ArrayList<LineSegment> rays;
    private ArrayList<Point> added;

    public ShadowCaster() {
	this.rays = new ArrayList<>();
	this.added = new ArrayList<>();
    }

    public void cast(final double sourceX, final double sourceY,
	    final LinkedList<Rectangle> colliders) {
	this.rays.clear();
	this.added.clear();

	colliders.forEach((final Rectangle collider) -> {
	    final Point[] points = getPoints(collider);

	    for (int i = 0; i < points.length; i++) {
		addRay(sourceX, sourceY, points[i]);
		// TODO: add better corner checking by doing angles
		addRay(sourceX, sourceY, new Point(points[i].x + 1,
			points[i].y + 1));
		addRay(sourceX, sourceY, new Point(points[i].x - 1,
			points[i].y + 1));
		addRay(sourceX, sourceY, new Point(points[i].x - 1,
			points[i].y - 1));
		addRay(sourceX, sourceY, new Point(points[i].x + 1,
			points[i].y - 1));
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

	System.out.println("Num rays: " + this.rays.size());
    }

    private void addRay(final double sourceX, final double sourceY,
	    final Point point) {
	// Check if point has already been added
	boolean unique = true;
	for (int j = 0; j < this.added.size(); j++) {
	    if (this.added.get(j).x == point.x
		    && this.added.get(j).y == point.y) {
		unique = false;
		break;
	    }
	}
	if (!unique) {
	    return;
	}

	final LineSegment ray = new LineSegment(sourceX, sourceY, 0.0, 0.0);
	ray.setIntersectingPoint(point.x + 1, point.y + 1);
	this.rays.add(ray);
	this.added.add(point);
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
	// Sort rays by angle clockwise
	this.rays.sort((final LineSegment r1, final LineSegment r2) -> {
	    final double difference = r1.getAngle() - r2.getAngle();
	    if (difference < -0.0001) {
		return -1;
	    } else if (difference > 0.0001) {
		return 1;
	    }
	    return 0;
	});

	// Create shadow polygon
	final Point[] points = new Point[this.rays.size()];
	for (int i = 0; i < points.length; i++) {
	    points[i] = this.rays.get(i).getIntersectionPoint();
	}
	final int[] xpoints = new int[points.length];
	for (int i = 0; i < xpoints.length; i++) {
	    xpoints[i] = (int) points[i].getX();
	}
	final int[] ypoints = new int[points.length];
	for (int i = 0; i < ypoints.length; i++) {
	    ypoints[i] = (int) points[i].getY();
	}
	final Polygon shadow = new Polygon(xpoints, ypoints, points.length);

	// Create shadow image
	final BufferedImage shadowImage = new BufferedImage(Main.WIDTH,
		Main.HEIGHT, BufferedImage.TYPE_INT_ARGB);
	final Graphics2D sg = (Graphics2D) shadowImage.getGraphics();
	// Antialiasing
	final RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	sg.setRenderingHints(rh);
	sg.setColor(Color.BLACK);
	sg.fillRect(0, 0, shadowImage.getWidth(), shadowImage.getHeight());
	final Composite composite = AlphaComposite
		.getInstance(AlphaComposite.DST_OUT);
	sg.setComposite(composite);
	sg.fillPolygon(shadow);

	// Draw shadow
	g.drawImage(shadowImage, 0, 0, null);

	// Debug rays
	if (Main.DEBUG) {
	    this.rays.forEach((final LineSegment segment) -> {
		segment.render(g);
	    });
	}
    }
}
