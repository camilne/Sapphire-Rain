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
    public void canInitializeEmptyState() {
	this.state.init();
    }

    @Test
    public void canUpdateEmptyState() {
	this.state.update();
    }

    @Test
    public void canRenderEmptyState() {
	this.state.render();
    }

}
