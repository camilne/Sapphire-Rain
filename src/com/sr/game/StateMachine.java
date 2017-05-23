package com.sr.game;

import java.util.ArrayDeque;
import java.util.HashMap;

import javax.swing.JFrame;

public class StateMachine {

    private static final Object key = new Object();

    private static volatile StateMachine instance;

    private final ArrayDeque<State> stateStack;
    private final HashMap<String, State> stateMap;
    private JFrame mainWindow;

    private StateMachine(JFrame mainWindow) {
	this.stateStack = new ArrayDeque<>();
	this.stateMap = new HashMap<>();
	this.mainWindow = mainWindow;

	registerState("main-menu", new MainMenu());
	registerState("game", new GameScreen());
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

    public void registerState(final String stateName, final State state) {
	if (stateName == null) {
	    throw new NullPointerException("Registering state name is null");
	}

	if (state == null) {
	    throw new NullPointerException(
		    "Registering state is null with name: " + stateName);
	}

	this.stateMap.put(stateName, state);
    }

    public void pushState(final String stateName) {
	if (!this.stateMap.containsKey(stateName)) {
	    throw new IllegalArgumentException("State name (" + stateName
		    + ") does not exist in registered states");
	}

	pushState(this.stateMap.get(stateName));
    }

    public void pushState(final State state) {
	if (state == null) {
	    throw new NullPointerException("State is null");
	}

	state.init();

	if (this.mainWindow != null) {
	    if (getCurrentState() != null) {
		this.mainWindow.remove(getCurrentState());
	    }

	    this.mainWindow.add(state);
	    this.mainWindow.revalidate();
	    this.mainWindow.repaint();
	}

	this.stateStack.push(state);
    }

    public State popState() {
	State resultState = this.stateStack.pop();

	if (this.mainWindow != null) {
	    this.mainWindow.remove(resultState);

	    if (getCurrentState() != null) {
		this.mainWindow.add(getCurrentState());
	    }

	    this.mainWindow.revalidate();
	    this.mainWindow.repaint();
	}

	return resultState;
    }

    public State getCurrentState() {
	if (this.stateStack.isEmpty()) {
	    return null;
	}

	return this.stateStack.peek();
    }

    public void setJFrame(final JFrame newFrame) {
	this.mainWindow = newFrame;
    }

    public static StateMachine getInstance() {
	if (instance != null) {
	    return instance;
	}

	synchronized (key) {
	    if (instance == null) {
		instance = new StateMachine(null);
	    }

	    return instance;
	}
    }

}
