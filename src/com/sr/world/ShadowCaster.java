package com.sr.world;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sr.main.Main;
import com.sr.util.Point;

public class ShadowCaster {

    final Object lock = new Object();

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

	synchronized (this.lock) {
	    colliders.forEach((final Rectangle collider) -> {
		// Check if collider is empty
		    if (collider.isEmpty()) {
			return;
		    }

		    final Point[] points = getPoints(collider);

		    for (int i = 0; i < points.length; i++) {
			final LineSegment ray = addRay(sourceX, sourceY,
				points[i]);
			if (ray != null) {
			    // Send adjacent rays to shoot past corners
		    final double angleDelta = 0.0001;
		    addRay(sourceX, sourceY, ray.getAngle() + angleDelta);
		    addRay(sourceX, sourceY, ray.getAngle() - angleDelta);
		}
	    }
	})  ;

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
	}

	// System.out.println("Num rays: " + this.rays.size());
    }

    private LineSegment addRay(final double sourceX, final double sourceY,
	    final Point point) {
	// Check if point has already been added
	boolean unique = true;
	for (int j = 0; j < this.added.size(); j++) {
	    if (Double.compare(this.added.get(j).x, point.x) == 0
		    && Double.compare(this.added.get(j).y, point.y) == 0) {
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

    /**
     * Creates an array of triangles from the border of points provided
     * 
     * @param points
     *            the border of points sorted clockwise around the center
     * @param center
     *            the center point
     * @return an array of triangle polygons
     */
    private static Polygon[] createTriangles(final Point[] points,
	    final Point center) {
	if (points.length == 0) {
	    return null;
	}

	final int numTriangles = points.length;
	final Polygon[] triangles = new Polygon[numTriangles];

	final int npoints = 3;
	final int[] xpoints = new int[npoints];
	final int[] ypoints = new int[npoints];

	// Set center point
	xpoints[0] = (int) center.x;
	ypoints[0] = (int) center.y;

	for (int i = 0; i < numTriangles; i++) {
	    // Set first point clockwise
	    xpoints[1] = (int) points[i].x;
	    ypoints[1] = (int) points[i].y;

	    // Set second point clockwise
	    // Check if it is last polygon
	    if (i + 1 >= numTriangles) {
		xpoints[2] = (int) points[0].x;
		ypoints[2] = (int) points[0].y;
	    } else {
		xpoints[2] = (int) points[i + 1].x;
		ypoints[2] = (int) points[i + 1].y;
	    }

	    // Create polygon
	    triangles[i] = new Polygon(xpoints, ypoints, npoints);
	}

	return triangles;
    }

    public void render(final Graphics g, final int levelWidth,
	    final int levelHeight, final double sourceX, final double sourceY) {

	synchronized (this.lock) {
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
		xpoints[i] = (int) Math.round(points[i].getX());
	    }
	    final int[] ypoints = new int[points.length];
	    for (int i = 0; i < ypoints.length; i++) {
		ypoints[i] = (int) Math.round(points[i].getY());
	    }
	    final Polygon shadow = new Polygon(xpoints, ypoints, points.length);

	    // Create shadow image
	    final BufferedImage shadowImage = new BufferedImage(levelWidth,
		    levelHeight, BufferedImage.TYPE_INT_ARGB);
	    final Graphics2D sg = (Graphics2D) shadowImage.getGraphics();

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
}
