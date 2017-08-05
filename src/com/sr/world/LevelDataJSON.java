package com.sr.world;

public class LevelDataJSON {

    // The name of the tile configuration file
    public String tileConfigName;
    // The width of the level
    public int width;
    // The height of the level
    public int height;
    // The ids of the tiles in the level
    public int[][] tiles;
    // The list of entities present in the world
    public EnemyJSON[] enemies;

}
