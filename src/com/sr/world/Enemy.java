package com.sr.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.sr.asset.TextureAtlas;
import com.sr.util.Vector2;

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
	    final double y, final Pathfinder pathfinder) throws IOException,
	    ClassNotFoundException {
	if (enemyClass.equals(BasicEnemy.class.getSimpleName())) {
	    final BasicEnemy enemy = BasicEnemy.create();
	    enemy.x = x;
	    enemy.y = y;
	    enemy.setPathfinder(pathfinder);
	    return enemy;
	}

	throw new ClassNotFoundException("Enemy class not found: " + enemyClass);
    }

    // The speed of the enemy in pixels/second
    private static double speed = 60.0;

    // The internal position of the player. Used for decision making and path
    // finding.
    protected static double playerX = 0;
    protected static double playerY = 0;
    // The direction this enemy is moving in.
    protected Vector2 direction;

    protected double width;
    protected double height;

    // The name of the default texture on the TextureAtlas.
    private String defaultTexture;
    private TextureAtlas atlas;

    // The pathfinder
    protected Pathfinder pathfinder;

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
	final BufferedImage image = this.atlas.getTexture(defaultTexture);
	this.width = image.getWidth();
	this.height = image.getHeight();
	// this.boundingBox = new Rectangle();
	this.pathfinder = null;
	this.direction = new Vector2();
    }

    /**
     * Used to process the ai. Should update state variables and the
     * <code>moveDirection</code> if needed.
     */
    protected abstract void ai();

    @Override
    public void input() {
	ai();

	this.direction.normalize();

	this.dx = this.direction.x * speed;
	this.dy = this.direction.y * speed;
    }

    @Override
    public void render(final Graphics g) {
	g.drawImage(this.atlas.getTexture(this.defaultTexture), (int) this.x,
		(int) this.y, null);
    }

    /**
     * Sets the pathfinder for this enemy to use. Should be a pathfinder for the
     * world tileset.
     * 
     * @param pathfinder
     *            The pathfinder instance
     */
    public void setPathfinder(final Pathfinder pathfinder) {
	this.pathfinder = pathfinder;
    }

    /**
     * Returns the x-value for the center of this enemy.
     * 
     * @return The x-value for the center of this enemy in pixels.
     */
    public double getCX() {
	return this.x + this.width / 2.0;
    }

    /**
     * Returns the y-value for the center of this enemy.
     * 
     * @return The y-value for the center of this enemy in pixels.
     */
    public double getCY() {
	return this.y + this.height / 2.0;
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

    /**
     * Sets the route this enemy should use to patrol. If no route is found
     * between the two nodes, then the enemy does not follow any path.
     * 
     * @param start
     *            The start of the route.
     * @param goal
     *            The end of the route.
     */
    public abstract void setPatrolRoute(final Node start, final Node goal);

}
