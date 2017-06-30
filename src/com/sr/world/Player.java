package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.sr.asset.TextureAtlas;
import com.sr.coverage.CoverageIgnore;
import com.sr.input.Controllable;

public class Player extends Entity implements Controllable {

    private static final String PLAYER_NAME = "player";

    private TextureAtlas atlas;
    private int width;
    private int height;

    private boolean moveLeft;
    private boolean moveRight;
    private boolean moveUp;
    private boolean moveDown;
    private double speed;

    public Player(final double x, final double y, final TextureAtlas atlas) {
	super(x, y, PLAYER_NAME);
	this.atlas = atlas;

	// Create default view
	final Rectangle defaultArea = new Rectangle(72 * 4, 72 * 4, 72, 72);
	this.atlas.registerTexture("default", defaultArea);

	this.width = (int) (72 * 1.7);
	this.height = (int) (72 * 1.7);

	// Create default movement conditions
	this.moveLeft = false;
	this.moveRight = false;
	this.moveUp = false;
	this.moveDown = false;
	this.speed = 60.0;
    }

    @Override
    public void update(final double deltaTime) {
	if (this.moveLeft) {
	    this.x -= this.speed * deltaTime;
	} else if (this.moveRight) {
	    this.x += this.speed * deltaTime;
	}

	if (this.moveUp) {
	    this.y -= this.speed * deltaTime;
	} else if (this.moveDown) {
	    this.y += this.speed * deltaTime;
	}
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture("default"),
		(int) (this.x - this.width / 2.0),
		(int) (this.y - this.height / 2.0), this.width, this.height,
		null);
    }

    @CoverageIgnore
    @Override
    public void keyDown(final int keyCode) {
	switch (keyCode) {
	case KeyEvent.VK_A:
	    this.moveLeft = true;
	    break;
	case KeyEvent.VK_D:
	    this.moveRight = true;
	    break;
	case KeyEvent.VK_W:
	    this.moveUp = true;
	    break;
	case KeyEvent.VK_S:
	    this.moveDown = true;
	    break;
	default:

	}
    }

    @CoverageIgnore
    @Override
    public void keyUp(final int keyCode) {
	switch (keyCode) {
	case KeyEvent.VK_A:
	    this.moveLeft = false;
	    break;
	case KeyEvent.VK_D:
	    this.moveRight = false;
	    break;
	case KeyEvent.VK_W:
	    this.moveUp = false;
	    break;
	case KeyEvent.VK_S:
	    this.moveDown = false;
	    break;
	default:

	}
    }

}
