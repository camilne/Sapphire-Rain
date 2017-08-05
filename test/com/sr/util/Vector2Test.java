package com.sr.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Vector2Test {

    private static final double EPSILON = 1e-5;

    private Vector2 instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.instance = new Vector2();
    }

    @Test
    public void createsDefaultVectorWithZeroZeroComponents() {
	assertEquals(0, this.instance.x);
	assertEquals(0, this.instance.y);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateVectorWithComponents() {
	final Vector2 vec = new Vector2(10, -2);

	assertEquals(10, vec.x, EPSILON);
	assertEquals(-2, vec.y, EPSILON);
    }

    @Test
    public void canGetMagnitudeOfVector() {
	this.instance.x = -3;
	this.instance.y = 4;

	final double mag = this.instance.magnitude();

	assertEquals(5, mag, EPSILON);
    }

    @Test
    public void canNormalizeVector() {
	this.instance.x = 3;
	this.instance.y = -5;

	this.instance.normalize();

	final double mag = this.instance.magnitude();

	assertEquals(1.0, mag, EPSILON);
    }

}
