package com.sr.game;

import java.util.ArrayDeque;
import java.util.HashMap;

import javax.swing.JFrame;

public class StateMachine {

    // Lock for multi-threading synchronization of this object
    private static final Object key = new Object();

    // Holds the singleton instance
    private static volatile StateMachine instance;

    // Holds the stack of states
    private final ArrayDeque<State> stateStack;
    // Holds the bindings between a state name and the state instance
    private final HashMap<String, State> stateMap;
    // The main window of the application. Used to revalidate the window when
    // state changes
    private JFrame mainWindow;

    private StateMachine(JFrame mainWindow) {
	this.stateStack = new ArrayDeque<>();
	this.stateMap = new HashMap<>();
	this.mainWindow = mainWindow;

	// Register the states under these names for later access
	registerState("main-menu", new MainMenu());
	registerState("game", new GameScreen());
    }

    public void update() {
	// Updates the current state
	if (getCurrentState() != null) {
	    getCurrentState().update();
	}
    }

    public void render() {
	// Renders the current state
	if (getCurrentState() != null) {
	    getCurrentState().render();
	}
    }

    public void registerState(final String stateName, final State state) {
	// stateName nor state can be null
	if (stateName == null) {
	    throw new NullPointerException("Registering state name is null");
	}

	if (state == null) {
	    throw new NullPointerException(
		    "Registering state is null with name: " + stateName);
	}

	// Register this state. Notice that a state type can be registered
	// multiple times under different names, but not under the same name
	// multiple times
	this.stateMap.put(stateName, state);
    }

    public void pushState(final String stateName) {
	if (!this.stateMap.containsKey(stateName)) {
	    throw new IllegalArgumentException("State name (" + stateName
		    + ") does not exist in registered states");
	}

	// Adds the new state onto the state stack using the name lookup
	pushState(this.stateMap.get(stateName));
    }

    public void pushState(final State state) {
	if (state == null) {
	    throw new NullPointerException("State is null");
	}

	// Initializes the new state
	state.init();

	if (this.mainWindow != null) {
	    if (getCurrentState() != null) {
		// Removes the old state from the JFrame if there is one
		this.mainWindow.remove(getCurrentState());
	    }

	    // Adds the new state to the JFrame
	    this.mainWindow.add(state);
	    // Refreshes the JFrame to recognize the new state
	    this.mainWindow.revalidate();
	    this.mainWindow.repaint();
	}

	// Addes the new state to the stack (making it current)
	this.stateStack.push(state);
    }

    public State popState() {
	// The old state that is being popped
	State resultState = this.stateStack.pop();

	if (this.mainWindow != null) {
	    // Removes the old state from the JFrame
	    this.mainWindow.remove(resultState);

	    if (getCurrentState() != null) {
		// Adds the next state (now current) to the JFrame
		this.mainWindow.add(getCurrentState());
	    }

	    // Refreshes the JFrame to recognize the new state
	    this.mainWindow.revalidate();
	    this.mainWindow.repaint();
	}

	// Returns the popped state
	return resultState;
    }

    public State getCurrentState() {
	// Returns null if there is nothing on the stack
	if (this.stateStack.isEmpty()) {
	    return null;
	}

	// Returns the top of the stack to get the current state (does not pop
	// the state)
	return this.stateStack.peek();
    }

    public void setJFrame(final JFrame newFrame) {
	this.mainWindow = newFrame;
    }

    /**
     * Double-locking singleton pattern. Allows you to get a single, shared
     * instance of this object across multiple threads
     * 
     * @return The state machine instance
     */
    public static StateMachine getInstance() {
	// Check if the instance is already initialized
	if (instance != null) {
	    return instance;
	}

	// Lock this block to the current thread to instantiate the instance
	synchronized (key) {
	    // Check if the instance has been initialized since the last check
	    if (instance == null) {
		instance = new StateMachine(null);
	    }

	    return instance;
	}
    }

}
