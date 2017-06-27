package com.sr.game;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateMachineTest {

    private StateMachine instance;
    private State emptyState;

    @BeforeEach
    public void setUp() throws Exception {
	final JFrame dummyJFrame = new JFrame();
	this.instance = new StateMachine(dummyJFrame);

	this.emptyState = new State() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void init() {
		// Empty
	    }

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	};
    }

    @Test
    public void canRegisterNewStateWithName() {
	this.instance.registerState("test", this.emptyState);
    }

    @Test
    public void canAddStateToStackByName() {
	this.instance.registerState("test", this.emptyState);
	this.instance.pushState("test");

	assertEquals(this.emptyState, this.instance.getCurrentState());
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenInvalidNameIsPushed() {
	this.instance.registerState("test", this.emptyState);
	assertThrows(IllegalArgumentException.class,
		() -> this.instance.pushState("does-not-exist"));
    }

    @Test
    public void canRegisterMulitpleNewStatesWithName() {
	this.instance.registerState("test", this.emptyState);

	final State state = new State() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void init() {
		// Empty
	    }

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	};

	this.instance.registerState("new-test", state);
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenDuplicateStateIsRegistered() {
	this.instance.registerState("test", this.emptyState);

	assertThrows(IllegalArgumentException.class,
		() -> this.instance.registerState("test", this.emptyState));
    }

    @Test
    public void canAddStateToStackByInstance() {
	this.instance.pushState(this.emptyState);

	assertEquals(this.emptyState, this.instance.getCurrentState());
    }

    @Test
    public void canGetCurrentState() {
	this.instance.registerState("test", this.emptyState);
	this.instance.pushState("test");

	assertEquals(this.emptyState, this.instance.getCurrentState());
    }

    @Test
    public void returnsNullWhenStateStackIsEmpty() {
	assertEquals(null, this.instance.getCurrentState());
    }

    @Test
    public void doesNotPopStateWhenGettingCurrentState() {
	this.instance.registerState("test", this.emptyState);
	this.instance.pushState("test");

	assertEquals(this.emptyState, this.instance.getCurrentState());
	assertEquals(this.emptyState, this.instance.getCurrentState());
    }

    @Test
    public void canSetJFrame() {
	final JFrame dummyJFrame = new JFrame();
	this.instance.setJFrame(dummyJFrame);
    }

}
