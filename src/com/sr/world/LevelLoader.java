package com.sr.world;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.sr.asset.TextureAtlas;

public class LevelLoader {

    private static final String PATH = "./resources/level/";

    /**
     * Loads a level from the json file <code>name.dat</code>. This file
     * specifies the TextureAtlas data to use for the tiles in a json file. The
     * .dat file also defines the level width, height, and tile ids. The other
     * configuration file specifies the name of the BufferedImage, the size of
     * the tile on the texture, and each tile's name, id, location, and
     * colliders.
     * 
     * @param name
     *            the name of the bootstrap .dat file
     * @return an instance of the loaded level
     * @throws IOException
     *             if any of the files are malformed or do not exist
     */
    public static Level loadLevel(final String name) throws IOException {
	// Load level data
	final String dataPath = PATH + name + ".dat";
	final String dataJson = loadFile(dataPath);

	final Gson gson = new Gson();
	final LevelDataJSON data = gson.fromJson(dataJson, LevelDataJSON.class);

	// Load configuration file
	final String configName = data.tileConfigName;
	final String configPath = PATH + configName + ".json";
	final String configJson = loadFile(configPath);

	// Setup configuration JSON parser
	final LevelConfigurationJSON config = gson.fromJson(configJson,
		LevelConfigurationJSON.class);

	// Load TextureAtlas
	final String atlasName = config.imageName;
	final String atlasPath = PATH + atlasName;
	final File atlasFile = new File(atlasPath);
	final BufferedImage atlasImage = ImageIO.read(atlasFile);
	final TextureAtlas atlas = new TextureAtlas(atlasImage);

	// Load tile data
	final HashMap<Integer, TileType> tileDataMap = new HashMap<>();
	for (int i = 0; i < config.tileData.length; i++) {
	    final TileTypeJSON tileData = config.tileData[i];
	    final int id = tileData.id;
	    final int imageSize = config.tileSize;

	    // Register textures
	    final String textureName = Tile.PREFIX + String.valueOf(id);
	    if (tileData.location.length != 2) {
		throw new IOException(
			"Malformed texture data. Location length of "
				+ tileData.location.length + " for tile of id "
				+ id);
	    }
	    final int tileSize = config.tileSize;
	    final int startX = tileData.location[0] * tileSize;
	    final int startY = tileData.location[1] * tileSize;
	    final Rectangle area = new Rectangle(startX, startY, tileSize,
		    tileSize);
	    atlas.registerTexture(textureName, area);

	    // Create colliders
	    final Rectangle[] colliders = new Rectangle[tileData.colliders.length];
	    for (int j = 0; j < colliders.length; j++) {
		if (tileData.colliders[j].length == 0) {
		    colliders[j] = new Rectangle();
		} else if (tileData.colliders[j].length == 4) {
		    final int x = tileData.colliders[j][0];
		    final int y = tileData.colliders[j][1];
		    final int width = tileData.colliders[j][2];
		    final int height = tileData.colliders[j][3];
		    colliders[j] = new Rectangle(x, y, width, height);
		} else {
		    throw new IOException(
			    "Malformed collider data. Collider length of "
				    + tileData.colliders[j].length
				    + " for tile of id " + id);
		}
	    }

	    final TileType type = new TileType(id, imageSize, colliders);

	    tileDataMap.put(new Integer(id), type);
	}

	// Create tiles
	final Tile[][] tiles = new Tile[data.height][data.width];
	for (int r = 0; r < tiles.length; r++) {
	    for (int c = 0; c < tiles[0].length; c++) {
		tiles[r][c] = TileFactory.create(tileDataMap.get(new Integer(
			data.tiles[r][c])));
	    }
	}

	// Create level
	final Level result = new Level(atlas, tiles);

	return result;
    }

    /**
     * Makes sure a file exists and then loads the entire file into a string.
     * 
     * @param path
     *            The full (or relative) path of the file
     * @return a string of the file's contents
     * @throws IOException
     *             if the file does not exist
     */
    private static String loadFile(final String path) throws IOException {
	final File file = new File(path);
	if (!file.exists()) {
	    throw new IOException("Failed to load: " + path
		    + ", file does not exist");
	}

	final StringBuilder sb = new StringBuilder();
	try (final Scanner scanner = new Scanner(file)) {
	    while (scanner.hasNextLine()) {
		sb.append(scanner.nextLine());
	    }
	}

	return sb.toString();
    }
}
