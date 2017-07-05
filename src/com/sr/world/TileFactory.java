package com.sr.world;

public class TileFactory {

    /**
     * Creates a tile of the specified type and gives it a name that matches one
     * generated by the TextureAtlas.
     * 
     * @param type
     *            the type of tile to create
     * @return the newly created tile
     */
    public static Tile create(final Tile.Type type) {
	return new Tile(Tile.PREFIX + type.ordinal(), type);
    }

}
