package com.sr.world;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NodeTest {

    private static final double EPSILON = 1e-5;

    private Node instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.instance = new Node(1, -2);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateNewNodeWithX() {
	final Node node = new Node(10, 0);

	assertEquals(10, node.getX());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canCreateNewNodeWithY() {
	final Node node = new Node(0, -2);

	assertEquals(-2, node.getY());
    }

    @Test
    public void equalsIsCheckedWithCoordinates() {
	final Node shouldEqual = new Node(1, -2);
	final Node firstNode = new Node(10, -5);
	final Node secondNode = new Node(10, -5);
	final Node shouldNotEqual = new Node(2, -3);

	assertEquals(this.instance, shouldEqual);
	assertEquals(firstNode, secondNode);
	assertNotEquals(this.instance, shouldNotEqual);
    }

    @Test
    public void nonNodeObjectEqualsRevertsToDefaultBehavior() {
	final Object object = new Object();

	assertNotEquals(this.instance, object);
    }

    @SuppressWarnings("boxing")
    @Test
    public void hashCodeReturnsUniqueIdentifiers() {
	final int node1 = new Node(1, 0).hashCode();
	final int node2 = new Node(0, 1).hashCode();
	final int node3 = new Node(-1, 0).hashCode();
	final int node4 = new Node(0, -1).hashCode();
	final int node5 = new Node(1, 1).hashCode();

	assertNotEquals(this.instance.hashCode(), node1);
	assertNotEquals(this.instance.hashCode(), node2);
	assertNotEquals(this.instance.hashCode(), node3);
	assertNotEquals(this.instance.hashCode(), node4);
	assertNotEquals(this.instance.hashCode(), node5);

	assertNotEquals(node1, node2);
	assertNotEquals(node1, node3);
	assertNotEquals(node1, node4);
	assertNotEquals(node1, node5);

	assertNotEquals(node2, node3);
	assertNotEquals(node2, node4);
	assertNotEquals(node2, node5);

	assertNotEquals(node3, node4);
	assertNotEquals(node3, node5);

	assertNotEquals(node4, node5);
    }

    @SuppressWarnings("static-method")
    @Test
    public void hashCodeIsConstant() {
	final Node node1 = new Node(2, 5);
	final int node2Hash = new Node(2, 5).hashCode();

	final int node1Hash = node1.hashCode();
	final int node1Repeat = node1.hashCode();

	assertEquals(node1Hash, node2Hash);
	assertEquals(node1Hash, node1Repeat);
    }

    @Test
    public void canSetG() {
	final double g = 2.1;
	this.instance.setG(g);

	assertEquals(2.1, this.instance.getG(), EPSILON);
    }

    @Test
    public void canSetF() {
	final double f = 1.8;
	this.instance.setF(f);

	assertEquals(1.8, this.instance.getF(), EPSILON);
    }

    @Test
    public void canGetIfNodeIsBlocked() {
	final Node blockedNode = new Node(0, 1, true);

	assertFalse(this.instance.isBlocked());
	assertTrue(blockedNode.isBlocked());
    }

    @Test
    public void canSetParentOfNode() {
	final Node parent = new Node(0, 12);
	this.instance.setParent(parent);

	final Node resultParent = this.instance.getParent();

	assertEquals(parent, resultParent);
    }
}
