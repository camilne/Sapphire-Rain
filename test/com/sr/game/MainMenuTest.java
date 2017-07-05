package com.sr.game;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MainMenuTest {

    private MainMenu instance;

    @BeforeEach
    public void setUp() throws Exception {
	final JFrame dummyJFrame = new JFrame();
	final StateMachine dummyStateMachine = new StateMachine(dummyJFrame);
	final State dummyState = new State() {

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

	dummyStateMachine.registerState("game", dummyState);
	this.instance = new MainMenu(dummyStateMachine);
    }

    @Test
    public void canInitializeMainMenu() {
	this.instance.init();
    }

    @Test
    public void canUpdateMainMenu() {
	this.instance.update();
    }

    @Test
    public void canRenderMainMenu() {
	this.instance.render();
    }
}
