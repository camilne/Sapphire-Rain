package com.sr.world;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sr.main.Main;
import com.sr.util.Point;

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
	    // Check if collider is empty
		if (collider.isEmpty()) {
		    return;
		}

		final Point[] points = getPoints(collider);

		for (int i = 0; i < points.length; i++) {
		    final LineSegment ray = addRay(sourceX, sourceY, points[i]);
		    if (ray != null) {
			// Send adjacent rays to shoot past corners
			final double angleDelta = 0.01;
			addRay(sourceX, sourceY, ray.getAngle() + angleDelta);
			addRay(sourceX, sourceY, ray.getAngle() - angleDelta);
		    }
		}
	    });

	this.rays.forEach((final LineSegment ray) -> {
	    ray.setT(Double.MAX_VALUE);

	    colliders.forEach((final Rectangle collider) -> {
		// Check if collider is empty
		    if (collider.isEmpty()) {
			return;
		    }

		    final LineSegment[] segments = getSegments(collider);

		    for (int i = 0; i < segments.length; i++) {
			ray.intersection(segments[i]);
		    }
		});
	});

	System.out.println("Num rays: " + this.rays.size());
    }

    private LineSegment addRay(final double sourceX, final double sourceY,
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
	    return null;
	}

	final LineSegment ray = new LineSegment(sourceX, sourceY, 0.0, 0.0);
	ray.setIntersectingPoint(point.x + 1, point.y + 1);
	this.rays.add(ray);
	this.added.add(point);

	return ray;
    }

    private void addRay(final double sourceX, final double sourceY,
	    final double angle) {
	final LineSegment ray = new LineSegment(sourceX, sourceY, angle);
	this.rays.add(ray);
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
	points[0] = new Point(r.getX(), r.getY());
	// Top right
	points[1] = new Point((r.getX() + r.getWidth()), r.getY());
	// Bottom right
	points[2] = new Point((r.getX() + r.getWidth()),
		(r.getY() + r.getHeight()));
	// Bottom left
	points[3] = new Point(r.getX(), (r.getY() + r.getHeight()));

	return points;
    }

    public void render(final Graphics g, final int levelWidth,
	    final int levelHeight) {
	// Sort rays by angle clockwise
	final double epsilon = 1e-7;
	this.rays.sort((final LineSegment r1, final LineSegment r2) -> {
	    final double difference = r1.getAngle() - r2.getAngle();
	    if (difference < -epsilon) {
		return -1;
	    } else if (difference > epsilon) {
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
	final BufferedImage shadowImage = new BufferedImage(levelWidth,
		levelHeight, BufferedImage.TYPE_INT_ARGB);
	final Graphics2D sg = (Graphics2D) shadowImage.getGraphics();
	// Antialiasing
	final RenderingHints rh = new RenderingHints(
		RenderingHints.KEY_TEXT_ANTIALIASING,
		RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION,
		RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
	rh.put(RenderingHints.KEY_RENDERING,
		RenderingHints.VALUE_RENDER_QUALITY);
	sg.setRenderingHints(rh);
	sg.setColor(Color.DARK_GRAY);
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
