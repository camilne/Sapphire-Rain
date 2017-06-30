package com.sr.world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.sr.asset.TextureAtlas;
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

	this.width = (int) (72 * 1.5);
	this.height = (int) (72 * 1.5);

	// Create default movement conditions
	this.moveLeft = false;
	this.moveRight = false;
	this.moveUp = false;
	this.moveDown = false;
	this.speed = 1.0;
    }

    @Override
    public void update(final double deltaTime) {
	if (this.moveLeft) {

	} else if (this.moveRight) {

	}

	if (this.moveUp) {

	} else if (this.moveDown) {

	}
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture("default"),
		(int) (this.x - this.width / 2.0),
		(int) (this.y - this.height / 2.0), this.width, this.height,
		null);
    }

    @Override
    public void keyDown(final int keyCode) {
	final boolean leftKey = KeyEvent.VK_A == keyCode;
	final boolean rightKey = KeyEvent.VK_D == keyCode;
	final boolean upKey = KeyEvent.VK_W == keyCode;
	final boolean downKey = KeyEvent.VK_S == keyCode;

	if (leftKey) {
	    this.moveLeft = true;
	} else if (rightKey) {
	    this.moveRight = true;
	}

	if (upKey) {
	    this.moveUp = true;
	} else if (downKey) {
	    this.moveDown = true;
	}
    }

    @Override
    public void keyUp(final int keyCode) {
	final boolean leftKey = KeyEvent.VK_A == keyCode;
	final boolean rightKey = KeyEvent.VK_D == keyCode;
	final boolean upKey = KeyEvent.VK_W == keyCode;
	final boolean downKey = KeyEvent.VK_S == keyCode;

	if (leftKey) {
	    this.moveLeft = false;
	} else if (rightKey) {
	    this.moveRight = false;
	}

	if (upKey) {
	    this.moveUp = false;
	} else if (downKey) {
	    this.moveDown = false;
	}

    }

}
