package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.sr.asset.TextureAtlas;
import com.sr.coverage.CoverageIgnore;
import com.sr.input.Controllable;
import com.sr.main.Main;

public class Player extends Entity implements Controllable {

    private static final String PLAYER_NAME = "player";
    private static final double PLAYER_SCALE = 1.7;

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

	this.width = (int) (72 * PLAYER_SCALE);
	this.height = (int) (72 * PLAYER_SCALE);

	// Create default movement conditions
	this.moveLeft = false;
	this.moveRight = false;
	this.moveUp = false;
	this.moveDown = false;
	this.speed = 60.0;

	// Setup bounding box
	this.boundingBox.setRect(27 * PLAYER_SCALE, 21 * PLAYER_SCALE,
		20 * PLAYER_SCALE, 30 * PLAYER_SCALE);
    }

    @Override
    public void input() {
	double frameSpeed = this.speed;

	// Reduce movement if character is traveling diagonally by a factor of
	// 1/sqrt(2)
	if ((this.moveLeft || this.moveRight) && (this.moveUp || this.moveDown)) {
	    final double oneOverSqrtTwo = 0.7071;
	    frameSpeed *= oneOverSqrtTwo;
	}

	if (this.moveLeft) {
	    this.dx = -frameSpeed;
	} else if (this.moveRight) {
	    this.dx = frameSpeed;
	} else {
	    this.dx = 0;
	}

	if (this.moveUp) {
	    this.dy = -frameSpeed;
	} else if (this.moveDown) {
	    this.dy = frameSpeed;
	} else {
	    this.dy = 0;
	}
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture("default"), (int) (this.x),
		(int) (this.y), this.width, this.height, null);

	// Render bounding box for debug
	if (Main.DEBUG) {
	    g.drawRect((int) this.getRelativeBoundingBox().getX(), (int) this
		    .getRelativeBoundingBox().getY(), (int) this
		    .getRelativeBoundingBox().getWidth(), (int) this
		    .getRelativeBoundingBox().getHeight());
	}
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
