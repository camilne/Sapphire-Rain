package com.sr.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StateTest {

    State state;

    @BeforeEach
    public void setUp() throws Exception {
	this.state = new State() {

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
    public void canInitializeEmptyState() {
	this.state.init();
    }

    @Test
    public void canUpdateEmptyState() {
	this.state.update(1.0);
    }

    @Test
    public void canRenderEmptyState() {
	this.state.render();
    }

}
