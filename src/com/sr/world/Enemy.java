package com.sr.world;

import java.awt.Graphics;
import java.io.IOException;

import com.sr.asset.TextureAtlas;

public abstract class Enemy extends Entity {

    public static Enemy createEnemy(final String enemyClass, final double x,
	    final double y) throws IOException, ClassNotFoundException {
	if (enemyClass.equals(BasicEnemy.class.getSimpleName())) {
	    final BasicEnemy enemy = BasicEnemy.create();
	    enemy.x = x;
	    enemy.y = y;
	    return enemy;
	}

	throw new ClassNotFoundException("Enemy class not found: " + enemyClass);
    }

    protected enum MoveDirection {
	LEFT, UP, RIGHT, DOWN, NONE
    }

    private static double speed = 60.0;

    protected static double playerX = 0;
    protected static double playerY = 0;

    protected MoveDirection moveDirection;

    private String defaultTexture;
    private TextureAtlas atlas;

    protected Enemy(final String defaultTexture, final TextureAtlas atlas) {
	this.defaultTexture = defaultTexture;
	this.atlas = atlas;
	this.moveDirection = MoveDirection.NONE;
    }

    protected abstract void ai();

    @Override
    public void input() {
	ai();

	this.dx = 0;
	this.dy = 0;

	switch (this.moveDirection) {
	case LEFT:
	    this.dx = -speed;
	    break;
	case UP:
	    this.dy = -speed;
	    break;
	case RIGHT:
	    this.dx = speed;
	    break;
	case DOWN:
	    this.dy = -speed;
	    break;
	case NONE:
	    break;
	}
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture(this.defaultTexture), (int) this.x,
		(int) this.y, null);
    }

    public static void updatePlayerData(final Player player) {
	playerX = player.getCX();
	playerY = player.getCY();
    }

}
