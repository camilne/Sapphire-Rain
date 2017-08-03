package com.sr.world;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;

import org.junit.jupiter.api.Test;

public class PathfinderTest {

    private static final TileType DEFAULT = new TileType(0, 0, false,
	    new Rectangle());
    private static final TileType BLOCKED = new TileType(0, 0, true,
	    new Rectangle());

    @SuppressWarnings("static-method")
    @Test
    public void canLocateSimpleValidPath() throws InvalidPathException {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT) },
		{ new Tile("", DEFAULT) }, { new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(0, 0);
	final Node goal = new Node(2, 0);

	final Path path = pathfinder.getPath(start, goal);
	assertNotEquals(null, path);
	assertEquals(start, path.getStart());
	assertEquals(goal, path.getGoal());
    }

    @SuppressWarnings("static-method")
    @Test
    public void returnsNullWhenNoValidPathExists() throws InvalidPathException {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT) },
		{ new Tile("", BLOCKED) }, { new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(0, 0);
	final Node goal = new Node(2, 0);

	final Path path = pathfinder.getPath(start, goal);
	assertEquals(null, path);
    }

    @SuppressWarnings("static-method")
    @Test
    public void throwsInvalidPathExceptionWithInvalidStartNode() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT) },
		{ new Tile("", DEFAULT) }, { new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(-1, 5);
	final Node goal = new Node(2, 0);

	assertThrows(InvalidPathException.class, () -> {
	    pathfinder.getPath(start, goal);
	});
    }

    @SuppressWarnings("static-method")
    @Test
    public void throwsInvalidPathExceptionWithInvalidGoalNode() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT) },
		{ new Tile("", DEFAULT) }, { new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(0, 0);
	final Node goal = new Node(10, -2);

	assertThrows(InvalidPathException.class, () -> {
	    pathfinder.getPath(start, goal);
	});
    }

    @SuppressWarnings("static-method")
    @Test
    public void returnsStartNodeWhenStartNodeIsTheGoalNode()
	    throws InvalidPathException {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT) },
		{ new Tile("", DEFAULT) }, { new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(0, 0);

	final Path path = pathfinder.getPath(start, start);
	assertNotEquals(null, path);
	assertEquals(start, path.getStart());
	assertEquals(start, path.getGoal());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canLocateComplexValidPath() throws InvalidPathException {
	final Tile[][] tiles = new Tile[][] {
		{ new Tile("", DEFAULT), new Tile("", BLOCKED),
			new Tile("", DEFAULT), new Tile("", DEFAULT) },
		{ new Tile("", DEFAULT), new Tile("", BLOCKED),
			new Tile("", DEFAULT), new Tile("", BLOCKED) },
		{ new Tile("", DEFAULT), new Tile("", DEFAULT),
			new Tile("", DEFAULT), new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	final Node start = new Node(0, 0);
	final Node goal = new Node(0, 3);

	final Path path = pathfinder.getPath(start, goal);
	assertNotEquals(null, path);
	assertEquals(start, path.getStart());
	assertEquals(goal, path.getGoal());
	assertEquals(5, path.getPath().size());
    }
}
