package com.sr.game;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

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
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	    @Override
	    public void keyDown(final int keyCode) {
		// Empty
	    }

	    @Override
	    public void keyUp(final int keyCode) {
		// Empty
	    }

	};
    }

    @Test
    public void canRegisterNewStateWithName() {
	this.instance.registerState("test", this.emptyState);
    }

    @Test
    public void throwsNullPointerExceptionWhenNullNameIsRegistered() {
	assertThrows(NullPointerException.class,
		() -> this.instance.registerState(null, this.emptyState));
    }

    @Test
    public void throwsNullPointerExceptionWhenNullStateIsRegisteredWithName() {
	assertThrows(NullPointerException.class,
		() -> this.instance.registerState("test", null));
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
    public void throwsNullPointerExceptionWhenNullStateIsPushed() {
	assertThrows(NullPointerException.class,
		() -> this.instance.pushState((State) null));
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
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	    @Override
	    public void keyDown(final int keyCode) {
		// Empty
	    }

	    @Override
	    public void keyUp(final int keyCode) {
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
    public void canAddStateToStackByInstanceWhenJFrameIsNull() {
	this.instance.setJFrame(null);
	this.instance.pushState(this.emptyState);

	assertEquals(this.emptyState, this.instance.getCurrentState());
    }

    @Test
    public void canAddMultipleStatesToStack() {
	this.instance.pushState(this.emptyState);

	final State state = new State() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void init() {
		// Empty
	    }

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	    @Override
	    public void keyDown(final int keyCode) {
		// Empty
	    }

	    @Override
	    public void keyUp(final int keyCode) {
		// Empty
	    }

	};

	this.instance.pushState(state);

	assertEquals(state, this.instance.getCurrentState());
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
    public void throwsNoSuchElementExceptionWhenEmptyStackIsPopped() {
	assertThrows(NoSuchElementException.class,
		() -> this.instance.popState());
    }

    @Test
    public void returnsPoppedState() {
	this.instance.pushState(this.emptyState);

	final State state = new State() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void init() {
		// Empty
	    }

	    @Override
	    public void update(final double deltaTime) {
		// Empty
	    }

	    @Override
	    public void render() {
		// Empty
	    }

	    @Override
	    public void keyDown(final int keyCode) {
		// Empty
	    }

	    @Override
	    public void keyUp(final int keyCode) {
		// Empty
	    }

	};
	this.instance.pushState(state);

	assertEquals(state, this.instance.popState());
	assertEquals(this.emptyState, this.instance.popState());
    }

    @Test
    public void canPopStateWithNullJFrame() {
	this.instance.setJFrame(null);
	this.instance.pushState(this.emptyState);

	assertEquals(this.emptyState, this.instance.popState());
    }

    @Test
    public void canSetJFrame() {
	final JFrame dummyJFrame = new JFrame();
	this.instance.setJFrame(dummyJFrame);
    }

    @Test
    public void canUpdateEmptyStateMachine() {
	this.instance.update(1.0);
    }

    @Test
    public void canUpdateStateMachine() {
	this.instance.pushState(this.emptyState);
	this.instance.update(1.0);
    }

    @Test
    public void canRenderEmptyStateMachine() {
	this.instance.render();
    }

    @Test
    public void canRenderStateMachine() {
	this.instance.pushState(this.emptyState);
	this.instance.render();
    }
}
