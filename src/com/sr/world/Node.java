package com.sr.world;

public class Node {

    private int x;
    private int y;
    private boolean blocked;
    private double g;
    private double f;

    private Node parent;

    private int hash;

    /**
     * Creates an unblocked node at the x and y coordinate.
     * 
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     */
    public Node(final int x, final int y) {
	this(x, y, false);
    }

    /**
     * Creates a node at the x and y coordinate, and determines if this node is
     * blocked to pathfinding.
     * 
     * @param x
     *            The x coordinate
     * @param y
     *            The y coordinate
     * @param blocked
     *            True if the node should be blocked to pathfinding.
     */
    public Node(final int x, final int y, final boolean blocked) {
	this.x = x;
	this.y = y;
	this.blocked = blocked;
	this.g = 0.0;
	this.f = 0.0;
	this.parent = null;
	this.hash = 0;
    }

    /**
     * Returns the x coordinate of this node.
     * 
     * @return The x coordinate
     */
    public int getX() {
	return this.x;
    }

    /**
     * Returns the y coordinate of this node.
     * 
     * @return The y coordinate
     */
    public int getY() {
	return this.y;
    }

    /**
     * Sets the g value of this node. The g value is used to determine how much
     * the node costs to get to from the starting node.
     * 
     * @param g
     *            The g value
     */
    public void setG(final double g) {
	this.g = g;
    }

    /**
     * Returns the g value of this node. The g value is used to determine how
     * much the node costs to get to from the starting node.
     * 
     * @return The g value
     */
    public double getG() {
	return this.g;
    }

    /**
     * Sets the f value of this node. The f value is used to determine how
     * likely a node is to be the best path. The f value is the sum of the cost
     * and heuristic values.
     * 
     * @param f
     *            The f value
     */
    public void setF(final double f) {
	this.f = f;
    }

    /**
     * Returns the f value of this node. The f value is used to determine how
     * likely a node is to be the best path. The f value is the sum of the cost
     * and heuristic values.
     * 
     * @return The f value
     */
    public double getF() {
	return this.f;
    }

    /**
     * Returns a boolean for whether or not this node is blocked to pathfinding.
     * 
     * @return True if the node is blocked
     */
    public boolean isBlocked() {
	return this.blocked;
    }

    /**
     * Sets the parent node for this node. Used to determine a path back to the
     * start.
     * 
     * @param parent
     *            The parent node
     */
    public void setParent(final Node parent) {
	this.parent = parent;
    }

    /**
     * Sets the parent node for this node. Used to determine a path back to the
     * start.
     * 
     * @return The parent node
     */
    public Node getParent() {
	return this.parent;
    }

    /**
     * This equals returns true if the other object is an
     * <code>instanceof Node</code> and the other object has the same x and y
     * coordinates of this node. If the other object is not an
     * <code>instanceof Node</code>, then the default
     * <code>Object::equals</code> behavior is used.
     */
    @Override
    public boolean equals(final Object other) {
	if (other instanceof Node) {
	    final Node node = (Node) other;
	    return this.x == node.x && this.y == node.y;
	}

	// Revert to default equals behavior if not comparing to another node.
	return super.equals(other);
    }

    @Override
    public int hashCode() {
	if (this.hash == 0) {
	    this.hash += this.y;
	    this.hash <<= 16;
	    this.hash += this.x;
	}

	return this.hash;
    }

    @Override
    public String toString() {
	final StringBuilder sb = new StringBuilder();
	sb.append("node[x: ");
	sb.append(this.x);
	sb.append(", y: ");
	sb.append(this.y);
	sb.append(", blocked: ");
	sb.append(this.blocked);
	sb.append(", f: ");
	sb.append(String.format("%.02f", Double.valueOf(this.f)));
	sb.append(", g: ");
	sb.append(String.format("%.02f", Double.valueOf(this.g)));
	sb.append(", h: ");
	sb.append(String.format("%.02f", Double.valueOf(this.f - this.g)));
	return sb.toString();
    }

}
