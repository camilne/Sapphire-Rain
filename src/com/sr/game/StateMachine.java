package com.sr.game;

import java.util.Stack;

import javax.swing.JFrame;

public class StateMachine {

    private final Stack<State> stateStack;
    private final JFrame mainWindow;

    public StateMachine(JFrame mainWindow) {
	this.stateStack = new Stack<>();
	this.mainWindow = mainWindow;
    }

    public void update() {
	if (getCurrentState() != null) {
	    getCurrentState().update();
	}
    }

    public void render() {
	if (getCurrentState() != null) {
	    getCurrentState().render();
	}
    }

    public void pushState(State state) {
	if (state == null) {
	    throw new NullPointerException("State is null");
	}

	if (getCurrentState() != null) {
	    this.mainWindow.remove(state);
	}

	this.stateStack.push(state);
	getCurrentState().init();

	this.mainWindow.add(state);
    }

    public State popState() {
	State resultState = this.stateStack.pop();

	this.mainWindow.remove(resultState);

	if (getCurrentState() != null) {
	    this.mainWindow.add(getCurrentState());
	}

	return resultState;
    }

    public State getCurrentState() {
	if (this.stateStack.empty()) {
	    return null;
	}

	return this.stateStack.peek();
    }

}
