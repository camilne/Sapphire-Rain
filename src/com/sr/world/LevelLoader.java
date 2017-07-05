package com.sr.world;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import com.google.gson.Gson;

public class LevelLoader {

    private static final String PATH = "./resources/level/";

    public static Level loadLevel(final String name) throws IOException {
	// Load level data
	final String dataPath = PATH + name + ".dat";
	final String dataJson = loadFile(dataPath);

	final Gson gson = new Gson();
	final LevelData data = gson.fromJson(dataJson, LevelData.class);
	final String configName = data.tileConfigName;

	// Load configuration file
	final String configPath = PATH + configName + ".json";
	final String configJson = loadFile(configPath);

	// Setup config JSON parser
	final LevelConfiguration config = gson.fromJson(configJson,
		LevelConfiguration.class);

	// Load TextureAtlas
	final String atlasName = config.imageName;
	final String atlasPath = PATH + atlasName;
	final File atlasFile = new File(atlasPath);
	final BufferedImage atlasImage = ImageIO.read(atlasFile);

	// Load tile data

	final Level result = null;

	return result;
    }

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
