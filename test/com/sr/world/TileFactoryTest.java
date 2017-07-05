package com.sr.world;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TileFactoryTest {

    @SuppressWarnings("static-method")
    @Test
    public void createsCorrectTypeOfTile() {
	final Tile.Type type = Tile.Type.EMPTY;
	final Tile tile = TileFactory.create(type);

	assertNotEquals(null, tile);
	assertEquals(type, tile.getType());
    }

}
