package com.sr.world;

import java.awt.Graphics;
import java.io.IOException;

import com.sr.asset.TextureAtlas;

public abstract class Enemy extends Entity {

    /**
     * Creates an instance of the specified enemy and sets it at the location
     * (x, y).
     * 
     * @param enemyClass
     *            A string of the simple name of the class that inherits from
     *            enemy of which to create an instance.
     * @param x
     *            The x coordinate (in pixels) of the new enemy.
     * @param y
     *            The y coordinate (in pixels) of the new enemy.
     * @return An instance of the <code>enemyClass</code>.
     * @throws IOException
     *             If the enemy fails to instantiate.
     * @throws ClassNotFoundException
     *             If the <code>enemyClass</code> does not exist.
     */
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

    /**
     * The direction in which to move the enemy during the update loop.
     */
    protected enum MoveDirection {
	LEFT, UP, RIGHT, DOWN, NONE
    }

    // The speed of the enemy in pixels/second
    private static double speed = 60.0;

    // The internal position of the player. Used for decision making and path
    // finding.
    protected static double playerX = 0;
    protected static double playerY = 0;
    // The direction this enemy is moving in.
    protected MoveDirection moveDirection;

    // The name of the default texture on the TextureAtlas.
    private String defaultTexture;
    private TextureAtlas atlas;

    /**
     * Creates an instance of the enemy with <code>defaultTexture</code> as the
     * default texture in the <code>atlas</code>. Sets the move direction to
     * <code>NONE</code>.
     * 
     * @param defaultTexture
     *            A string of the name of the default texture in the atlas.
     * @param atlas
     *            A TextureAtlas of all possible texture states of the enemy.
     */
    protected Enemy(final String defaultTexture, final TextureAtlas atlas) {
	this.defaultTexture = defaultTexture;
	this.atlas = atlas;
	this.moveDirection = MoveDirection.NONE;
    }

    /**
     * Used to process the ai in a child class. Should update state variables
     * and the <code>moveDirection</code> if needed.
     */
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

    /**
     * Used to updated the internal player data to make behavioral decisions.
     * 
     * @param player
     *            An instance of the player.
     */
    public static void updatePlayerData(final Player player) {
	playerX = player.getCX();
	playerY = player.getCY();
    }

}
