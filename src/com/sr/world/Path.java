package com.sr.world;

import java.util.ArrayList;
import java.util.LinkedList;

public class Path {

    private ArrayList<Node> path;

    /**
     * Creates a path from start to end using the parents of the end node in the
     * path.
     * 
     * @param end
     *            The end node in a path which has a linked list of parents to
     *            the start node.
     */
    public Path(final Node end) {
	final LinkedList<Node> linkedPath = new LinkedList<>();

	// Traverse the linked list to the start node.
	Node current = end;
	while (current != null) {
	    linkedPath.addFirst(current);
	    current = current.getParent();
	}

	this.path = new ArrayList<>(linkedPath);
    }

    /**
     * Returns the complete path from start to goal.
     * 
     * @return An <code>ArrayList</code> of nodes of the path.
     */
    public ArrayList<Node> getPath() {
	return this.path;
    }

    /**
     * Returns the next node in the path given the current node.
     * 
     * @param current
     *            The current node along the path.
     * @return The next node in the path. Returns <code>null</code> if there is
     *         no next node or if the current node does not exist on the path.
     */
    public Node getNext(final Node current) {
	for (int i = 0; i < this.path.size(); i++) {
	    // If we found the current node, return the next node in the path.
	    if (this.path.get(i).equals(current)) {
		if (i < this.path.size() - 1) {
		    return this.path.get(i + 1);
		}
		return null;
	    }
	}

	return null;
    }

    /**
     * Returns the start node in the path.
     * 
     * @return The first node in the path, which is the starting node.
     */
    public Node getStart() {
	return this.path.get(0);
    }

    /**
     * Returns the goal node in the path.
     * 
     * @return The last node in the path, which is the goal node.
     */
    public Node getGoal() {
	return this.path.get(this.path.size() - 1);
    }

}
