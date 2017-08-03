package com.sr.world;

import java.util.Collection;
import java.util.HashSet;

public class Pathfinder {

    private final Node[][] nodes;

    public Pathfinder(final Tile[][] tiles) {
	this.nodes = new Node[tiles.length][tiles[0].length];

	for (int x = 0; x < tiles.length; x++) {
	    for (int y = 0; y < tiles[0].length; y++) {
		final boolean occupied = tiles[x][y].getType().isOccupied();
		this.nodes[x][y] = new Node(x, y, occupied);
	    }
	}
    }

    private boolean isValid(final Node node) {
	if (node.getX() < 0 || node.getX() >= this.nodes.length) {
	    return false;
	}

	if (node.getY() < 0 || node.getY() >= this.nodes[0].length) {
	    return false;
	}

	return true;
    }

    private static Node findMinF(final Collection<Node> list) {
	Node min = new Node(0, 0);
	min.setF(Integer.MAX_VALUE);

	for (final Node node : list) {
	    if (node.getF() < min.getF()) {
		min = node;
	    }
	}

	return min;
    }

    /**
     * Calculates the heuristic between the node and the goal. Uses the Diagonal
     * Distance heuristic.
     * 
     * @param node
     *            The current node
     * @param goal
     *            The goal node
     * @return The double value of the heuristic.
     */
    private static double calculateHValue(final Node node, final Node goal) {
	final int dx = Math.abs(node.getX() - goal.getX());
	final int dy = Math.abs(node.getY() - goal.getY());
	return Math.max(dx, dy);
    }

    private boolean isBlocked(final Node node) {
	final int x = node.getX();
	final int y = node.getY();
	return this.nodes[x][y].isBlocked();
    }

    private static Node getNode(final HashSet<Node> nodes, final Node newNode) {
	for (final Node node : nodes) {
	    if (node.equals(newNode)) {
		return node;
	    }
	}

	return null;
    }

    public Path getPath(final Node start, final Node goal)
	    throws InvalidPathException {
	// Check if start and goal nodes are valid.
	if (!isValid(start)) {
	    throw new InvalidPathException("Start node is invalid: "
		    + start.toString());
	}

	if (!isValid(goal)) {
	    throw new InvalidPathException("Goal node is invalid: "
		    + goal.toString());
	}

	if (start.equals(goal)) {
	    return new Path(start);
	}

	final HashSet<Node> open = new HashSet<>();
	final HashSet<Node> closed = new HashSet<>();

	open.add(start);

	while (!open.isEmpty()) {
	    final Node q = findMinF(open);
	    open.remove(q);

	    // Generate successors
	    for (int i = -1; i <= 1; i++) {
		for (int j = -1; j <= 1; j++) {
		    if (i == 0 && j == 0) {
			continue;
		    }

		    final Node newNode = new Node(q.getX() + i, q.getY() + j);

		    // Check if new node is on the grid.
		    if (!isValid(newNode)) {
			continue;
		    }

		    // Check if the new node has already been visited.
		    if (closed.contains(newNode)) {
			continue;
		    }

		    // Check to see if the node is blocked.
		    if (isBlocked(newNode)) {
			continue;
		    }

		    // Set parent to trace back path.
		    newNode.setParent(q);

		    // Return node if it is the goal. Used to generate the path
		    // back to the start by traversing through parents.
		    if (newNode.equals(goal)) {
			return new Path(newNode);
		    }

		    // Calculate the cost
		    double cost = 1.4141;
		    if (i == 0 || j == 0) {
			cost = 1.0;
		    }
		    final double gNew = q.getG() + cost;
		    final double hNew = calculateHValue(newNode, goal);
		    final double fNew = gNew + hNew;

		    newNode.setG(gNew);
		    newNode.setF(fNew);

		    // Check to see if node is already on open list.
		    if (open.contains(newNode)) {
			// Update the node if it has a better f value.
			final Node existingNode = getNode(open, newNode);
			if (newNode.getF() < existingNode.getF()) {
			    existingNode.setParent(newNode.getParent());
			    existingNode.setG(newNode.getG());
			    existingNode.setF(newNode.getF());
			}
		    } else {
			open.add(newNode);
		    }
		}
	    }

	    closed.add(q);
	}

	return null;
    }
}
