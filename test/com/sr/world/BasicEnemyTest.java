package com.sr.world;

import java.awt.Rectangle;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BasicEnemyTest {

    private static final TileType DEFAULT = new TileType(0, 0, false,
	    new Rectangle());
    private static final TileType BLOCKED = new TileType(0, 0, true,
	    new Rectangle());

    private BasicEnemy instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.instance = BasicEnemy.create();
    }

    @Test
    public void canInputWhenNoPatrolRouteSet() {
	this.instance.input();
    }

    @Test
    public void canSetPatrolRouteWhenNoPathfinderSet() {
	final Node start = new Node(0, 0);
	final Node goal = new Node(6, 4);

	this.instance.setPatrolRoute(start, goal);
    }

    @Test
    public void canSetPathfinder() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT),
		new Tile("", DEFAULT), new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	this.instance.setPathfinder(pathfinder);
    }

    @Test
    public void canInputWithPathfinderButNoPatrolRoute() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT),
		new Tile("", DEFAULT), new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	this.instance.setPathfinder(pathfinder);

	this.instance.input();
    }

    @Test
    public void canInputWithValidPatrolRoute() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT),
		new Tile("", DEFAULT), new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	this.instance.setPathfinder(pathfinder);

	final Node start = new Node(0, 0);
	final Node goal = new Node(2, 0);
	this.instance.setPatrolRoute(start, goal);

	this.instance.input();
    }

    @Test
    public void canInputWithInvalidPatrolRoute() {
	final Tile[][] tiles = new Tile[][] { { new Tile("", DEFAULT),
		new Tile("", BLOCKED), new Tile("", DEFAULT) } };

	final Pathfinder pathfinder = new Pathfinder(tiles);
	this.instance.setPathfinder(pathfinder);

	final Node start = new Node(0, 0);
	final Node goal = new Node(2, 0);
	this.instance.setPatrolRoute(start, goal);

	this.instance.input();
    }
}
