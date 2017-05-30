package com.sr.world;

import static org.junit.Assert.*;

import java.awt.Graphics;

import org.junit.Test;

public class WorldTest {

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
	    public void render(Graphics g) {
		// Empty
	    }

	};

	boolean addResult = instance.addEntity(entity);

	assertTrue("Did not add entity to world successfully", addResult);
	assertEquals(1, instance.getEntityCount());
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
	    public void render(Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);
	boolean addResult = instance.addEntity(entity);

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
	    public void render(Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);

	boolean removeResult = instance.removeEntity(entity);

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
	    public void render(Graphics g) {
		// Empty
	    }

	};

	boolean removeResult = instance.removeEntity(entity);

	assertFalse("Should not have removed entity from world successfully",
		removeResult);
	assertEquals(0, instance.getEntityCount());
    }

}
