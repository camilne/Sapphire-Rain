package com.sr.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sr.asset.TextureAtlas;
import com.sr.coverage.CoverageIgnore;
import com.sr.main.Main;

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

    private enum Behavior {
	PATROL
    }

    private Behavior behavior;
    private Path patrolRoute;
    private Node targetNode;

    protected BasicEnemy(final String defaultTexture, final TextureAtlas atlas) {
	super(defaultTexture, atlas);
	this.behavior = Behavior.PATROL;
    }

    @Override
    protected void ai() {
	switch (this.behavior) {
	case PATROL:
	    if (this.patrolRoute != null) {
		// Start the route if it is not started.
		if (this.targetNode == null) {
		    this.targetNode = this.patrolRoute.getStart();
		}

		// Set the direction of the enemy to the target node.
		final double ndx = this.targetNode.getX() * Tile.SIZE
			- this.getCX();
		final double ndy = this.targetNode.getY() * Tile.SIZE
			- this.getCY();

		this.direction.x = ndx;
		this.direction.y = ndy;

		// Move to next node if the target node has been reached.
		if (reachedNode(this.targetNode)) {
		    this.targetNode = this.patrolRoute.getNext(this.targetNode);

		    if (this.targetNode == null) {
			this.patrolRoute.reverse();
		    }
		}
	    }
	    break;
	default:
	    this.behavior = Behavior.PATROL;
	}
    }

    /**
     * Returns whether or not the node has been reached. The enemy reaches the
     * node when it is within 2 pixels of the node.
     * 
     * @param node
     *            The node to check against.
     * @return True if the enemy is within the radius of the node.
     */
    @CoverageIgnore
    private boolean reachedNode(final Node node) {
	final double radius = 2; // Pixels

	final double ndx = node.getX() * Tile.SIZE - this.getCX();
	final double ndy = node.getY() * Tile.SIZE - this.getCY();

	return ndx * ndx + ndy * ndy < radius * radius;
    }

    @Override
    public void render(final Graphics g) {
	super.render(g);

	if (Main.DEBUG) {
	    if (this.patrolRoute != null) {
		g.setColor(Color.BLUE);

		Node cNode = this.patrolRoute.getStart();
		Node nNode = this.patrolRoute.getNext(cNode);
		while (nNode != null) {
		    final int x1 = cNode.getX() * Tile.SIZE;
		    final int y1 = cNode.getY() * Tile.SIZE;
		    final int x2 = nNode.getX() * Tile.SIZE;
		    final int y2 = nNode.getY() * Tile.SIZE;

		    g.drawLine(x1, y1, x2, y2);

		    cNode = nNode;
		    nNode = this.patrolRoute.getNext(cNode);
		}
	    }
	}
    }

    @Override
    public void setPatrolRoute(final Node start, final Node goal) {
	try {
	    this.patrolRoute = this.pathfinder.getPath(start, goal);
	} catch (final InvalidPathException e) {
	    e.printStackTrace();
	}
    }

}
