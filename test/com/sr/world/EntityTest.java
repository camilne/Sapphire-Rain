package com.sr.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.Graphics;

import org.junit.jupiter.api.Test;

public class EntityTest {

    private static final double EPSILON = 1e-4;

    @SuppressWarnings("static-method")
    @Test
    public void canCreateDefaultEntity() {
	final Entity instance = new Entity() {

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	assertEquals(0.0, instance.getX(), EPSILON);
	assertEquals(0.0, instance.getY(), EPSILON);
	assertEquals("", instance.getName());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateEntityAtPositiveArbitraryPoint() {
	final double x = 92805.245762;
	final double y = 12352.312325;

	final Entity instance = new Entity(x, y) {

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	assertEquals(x, instance.getX(), EPSILON);
	assertEquals(y, instance.getY(), EPSILON);
	assertEquals("", instance.getName());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateEntityAtNegativeArbitraryPoint() {
	final double x = -237923.293802;
	final double y = -121592.938038;

	final Entity instance = new Entity(x, y) {

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	assertEquals(x, instance.getX(), EPSILON);
	assertEquals(y, instance.getY(), EPSILON);
	assertEquals("", instance.getName());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateEntityAtPointWithName() {
	final double x = 15;
	final double y = 10;
	final String name = "test-entity";

	final Entity instance = new Entity(x, y, name) {

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	assertEquals(x, instance.getX(), EPSILON);
	assertEquals(y, instance.getY(), EPSILON);
	assertEquals(name, instance.getName());
    }

}
