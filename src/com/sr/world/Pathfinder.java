package com.sr.world;

import java.util.Collection;
import java.util.HashSet;

import com.sr.coverage.CoverageIgnore;

public class Pathfinder {

    private final Node[][] nodes;

    /**
     * Creates a new <code>PathFinder</code> that uses the provided tile map to
     * find paths between tiles.
     * 
     * @param tiles
     *            The tile map in which to find paths.
     */
    public Pathfinder(final Tile[][] tiles) {
	this.nodes = new Node[tiles[0].length][tiles.length];

	for (int x = 0; x < tiles.length; x++) {
	    for (int y = 0; y < tiles[0].length; y++) {
		final boolean occupied = tiles[x][y].getType().isOccupied();
		this.nodes[y][x] = new Node(x, y, occupied);
	    }
	}
    }

    /**
     * Determines if the provided node is valid by checking it against the
     * bounds of the node array.
     * 
     * @param node
     *            The node to test.
     * @return True if the node is within the node array.
     */
    @CoverageIgnore
    private boolean isValid(final Node node) {
	if (node.getX() < 0 || node.getX() >= this.nodes.length) {
	    return false;
	}

	if (node.getY() < 0 || node.getY() >= this.nodes[0].length) {
	    return false;
	}

	return true;
    }

    /**
     * Returns a {@link Node} with the smallest <code>f</code> value in the
     * {@link Collection}.
     * 
     * @param list
     *            The <code>Collection</code> through which to search.
     * @return A <code>Node</code> with the smallest <code>f</code> value in the
     *         <code>Collection</code>. Returns a <code>Node</code> with the
     *         maximum <code>f</code> value if the list is empty.
     */
    @CoverageIgnore
    private static Node findMinF(final Collection<Node> list) {
	Node min = new Node(0, 0);
	min.setF(Double.MAX_VALUE);

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
    @CoverageIgnore
    private static double calculateHValue(final Node node, final Node goal) {
	final int dx = Math.abs(node.getX() - goal.getX());
	final int dy = Math.abs(node.getY() - goal.getY());
	return Math.max(dx, dy);
    }

    /**
     * Returns whether or not the node is blocked.
     * 
     * @param node
     *            The node to check.
     * @return True if the node is blocked.
     */
    @CoverageIgnore
    private boolean isBlocked(final Node node) {
	final int x = node.getX();
	final int y = node.getY();
	return this.nodes[x][y].isBlocked();
    }

    /**
     * Gets the node instance that equals the <code>newNode</code> from the
     * {@link HashSet}.
     * 
     * @param nodes
     *            The HashSet to search through.
     * @param newNode
     *            The node to search for.
     * @return The node instance if it is found. <code>null</code> otherwise.
     */
    @CoverageIgnore
    private static Node getNode(final HashSet<Node> nodes, final Node newNode) {
	for (final Node node : nodes) {
	    if (node.equals(newNode)) {
		return node;
	    }
	}

	return null;
    }

    /**
     * Returns the shortest path between the start and goal nodes.
     * 
     * Uses the a-star algorithm to find the shortest path between the start
     * node and the goal node using the tiles passed to the constructor. Returns
     * a path of nodes from the start node to the end node if a path is found.
     * Returns <code>null</code> otherwise.
     * 
     * @param start
     *            The start node.
     * @param goal
     *            The goal node.
     * @return The shortest path from the start node to the goal node if a path
     *         is found. Null otherwise.
     * @throws InvalidPathException
     *             If the start node or goal node is invalid.
     */
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

	    // Add q to the closed list since we are done with it.
	    closed.add(q);
	}

	return null;
    }

    public static Node getNode(final double x, final double y) {
	final int nodeX = (int) (x / Tile.SIZE);
	final int nodeY = (int) (y / Tile.SIZE);
	return new Node(nodeX, nodeY);
    }
}
