package com.sr.world;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sr.asset.TextureAtlas;

public class BasicEnemy extends Enemy {

    /**
     * Creates an instance of a basic enemy. <code>defaultTexture</code> is
     * assigned to "default", and <code>atlasLocation</code> is assigned to
     * "./resources/basic_enemy.png".
     * 
     * @return A new instance of a BasicEnemy.
     * @throws IOException
     *             If the texture atlas location does not exist.
     */
    public static BasicEnemy create() throws IOException {
	final String defaultTexture = "default";
	final String atlasLocation = "./resources/basic_enemy.png";
	final BufferedImage atlasTexture = ImageIO
		.read(new File(atlasLocation));
	final TextureAtlas atlas = new TextureAtlas(atlasTexture);
	final Rectangle defaultArea = new Rectangle(0, 16, 32, 48);
	atlas.registerTexture(defaultTexture, defaultArea);

	return new BasicEnemy(defaultTexture, atlas);
    }

    protected BasicEnemy(final String defaultTexture, final TextureAtlas atlas) {
	super(defaultTexture, atlas);
    }

    @Override
    protected void ai() {
	// TODO: Remove and update with better ai.
	// Keep the basic enemy moving between two points.
	if (this.x > 8 * Tile.SIZE) {
	    this.moveDirection = MoveDirection.LEFT;
	}

	if (this.x < 1 * Tile.SIZE) {
	    this.moveDirection = MoveDirection.RIGHT;
	}

	if (this.moveDirection == MoveDirection.NONE) {
	    this.moveDirection = MoveDirection.LEFT;
	}
    }

}
