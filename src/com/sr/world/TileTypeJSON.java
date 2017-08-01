package com.sr.world;

public class TileTypeJSON {

    // The name of the tile type
    public String name;
    // The id of the tile type
    public int id;
    // The location of the tile type on the texture
    public int[] location;
    // The collider location data for the rectangle colliders
    public int[][] colliders;
    // Whether or not the tile is occupied for pathfinding
    public boolean occupied;

}
