package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.sr.asset.TextureAtlas;

public class Player extends Entity {

    private static final String PLAYER_NAME = "player";

    private TextureAtlas atlas;
    private int width;
    private int height;

    public Player(final double x, final double y, final TextureAtlas atlas) {
	super(x, y, PLAYER_NAME);
	this.atlas = atlas;

	// Create default view
	final Rectangle defaultArea = new Rectangle(72 * 4, 72 * 4, 72, 72);
	this.atlas.registerTexture("default", defaultArea);

	this.width = (int) (72 * 1.5);
	this.height = (int) (72 * 1.5);
    }

    @Override
    public void update() {
	// Empty
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture("default"),
		(int) (this.x - this.width / 2.0),
		(int) (this.y - this.height / 2.0), this.width, this.height,
		null);
    }

}
