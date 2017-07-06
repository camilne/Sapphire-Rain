package com.sr.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class LineSegmentTest {

    private static final double EPSILON = 1e-4;

    @SuppressWarnings("static-method")
    @Test
    public void confirmsValidIntersectionInMiddle() {
	final LineSegment ray = new LineSegment(0, 0, 1, 0);
	final LineSegment segment = new LineSegment(1, -1, 0, 2);

	final double result = ray.intersection(segment);

	assertEquals(1.0, result, EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void confirmsStartPointValidIntersection() {
	final LineSegment ray = new LineSegment(0, 0, 1, 0);
	final LineSegment segment = new LineSegment(2, 0, 0, 1);

	final double result = ray.intersection(segment);

	assertEquals(2.0, result, EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void confirmsEndPointValidIntersection() {
	final LineSegment ray = new LineSegment(0, 0, 1, 0);
	final LineSegment segment = new LineSegment(2, -2, 0, 2);

	final double result = ray.intersection(segment);

	assertEquals(2.0, result, EPSILON);
    }

}
