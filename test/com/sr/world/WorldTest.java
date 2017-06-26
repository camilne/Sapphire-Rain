package com.sr.world;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Test;

public class WorldTest {

    private static final double EPSILON = 1e-4;

    @SuppressWarnings("static-method")
    @Test
    public void canCreateNewDefaultWorld() {
	final World instance = new World();

	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canAddEntityToWorld() {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	final boolean addResult = instance.addEntity(entity);

	assertTrue("Did not add entity to world successfully", addResult);
	assertEquals(1, instance.getEntityCount());
	assertEquals(0.0, instance.getX(), EPSILON);
	assertEquals(0.0, instance.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void cannotAddDuplicateEntityToWorld() {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);
	final boolean addResult = instance.addEntity(entity);

	assertFalse("Should not have added to world but did", addResult);
	assertEquals(1, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canRemoveEntityFromWorld() {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);

	final boolean removeResult = instance.removeEntity(entity);

	assertTrue("Should have removed entity from world successfully",
		removeResult);
	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canSafelyRemoveNonExistentEntityFromWorld() {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	final boolean removeResult = instance.removeEntity(entity);

	assertFalse("Should not have removed entity from world successfully",
		removeResult);
	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canTranslateWorldWithPositiveArbitraryOffsets() {
	final World instance = new World();
	final double x1 = 1052.352340;
	final double y1 = 25082.23058;

	instance.translate(x1, y1);

	assertEquals(x1, instance.getX(), EPSILON);
	assertEquals(y1, instance.getY(), EPSILON);

	final double x2 = 12094.0289;
	final double y2 = 48579.28973;

	instance.translate(x2, y2);

	assertEquals(x1 + x2, instance.getX(), EPSILON);
	assertEquals(y1 + y2, instance.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canTranslateWorldWithNegativeArbitraryOffsets() {
	final World instance = new World();
	final double x1 = -2232.12352;
	final double y1 = -2583.345938;

	instance.translate(x1, y1);

	assertEquals(x1, instance.getX(), EPSILON);
	assertEquals(y1, instance.getY(), EPSILON);

	final double x2 = -2325.2894;
	final double y2 = -87981.4380;

	instance.translate(x2, y2);

	assertEquals(x1 + x2, instance.getX(), EPSILON);
	assertEquals(y1 + y2, instance.getY(), EPSILON);
    }

}
