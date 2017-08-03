package com.sr.world;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathTest {

    private Node node1;
    private Node node2;
    private Node node3;

    private Path instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.node1 = new Node(0, 0);
	this.node2 = new Node(1, 0);
	this.node3 = new Node(2, 0);

	this.node3.setParent(this.node2);
	this.node2.setParent(this.node1);

	this.instance = new Path(this.node3);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreatePathWhereStartNodeIsGoalNode() {
	final Node node = new Node(0, 0);
	final Path path = new Path(node);

	assertEquals(1, path.getPath().size());
	assertEquals(node, path.getStart());
	assertEquals(node, path.getGoal());
    }

    @Test
    public void canGetListOfNodesInPath() {
	final ArrayList<Node> nodes = this.instance.getPath();

	assertNotEquals(null, nodes);
	assertEquals(3, nodes.size());
    }

    @Test
    public void canGetNextValidNode() {
	final Node nextNode = this.instance.getNext(this.node1);

	assertEquals(this.node2, nextNode);
    }

    @Test
    public void returnsNullWhenGettingNextNodeWithLastNode() {
	final Node nextNode = this.instance.getNext(this.node3);

	assertEquals(null, nextNode);
    }

    @Test
    public void returnsNullWhenGettingNextNodeWithNonExistentNode() {
	final Node invalid = new Node(-10, 0);

	final Node nextNode = this.instance.getNext(invalid);

	assertEquals(null, nextNode);
    }

    @Test
    public void canGetStartOfPath() {
	final Node start = this.instance.getStart();

	assertEquals(this.node1, start);
    }

    @Test
    public void canGetGoalOfPath() {
	final Node goal = this.instance.getGoal();

	assertEquals(this.node3, goal);
    }

}
