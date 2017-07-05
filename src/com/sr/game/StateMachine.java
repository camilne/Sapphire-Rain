package com.sr.game;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.NoSuchElementException;

import javax.swing.JFrame;

public class StateMachine {
    // Holds the stack of states
    private final ArrayDeque<State> stateStack;
    // Holds the bindings between a state name and the state instance
    private final HashMap<String, State> stateMap;
    // The main window of the application. Used to revalidate the window when
    // state changes
    private JFrame mainWindow;

    /**
     * Creates a new state machine instance
     * 
     * @param mainWindow
     *            The JFrame to alter when state changes
     */
    public StateMachine(final JFrame mainWindow) {
	this.stateStack = new ArrayDeque<>();
	this.stateMap = new HashMap<>();
	this.mainWindow = mainWindow;
    }

    /**
     * Updates the current state
     */
    public void update() {
	if (getCurrentState() != null) {
	    getCurrentState().update();
	}
    }

    /**
     * Renders the current state
     */
    public void render() {
	if (getCurrentState() != null) {
	    getCurrentState().render();
	}
    }

    /**
     * Registers the state with the state machine under the given name. A state
     * type can be registered multiple times under different names, but not
     * under the same name multiple times.
     * 
     * @param stateName
     *            The name which the state will be known by in the lookup table
     * @param state
     *            An instance of the state to add
     * @throws NullPointerException
     *             if the stateName or the state is null
     */
    public void registerState(final String stateName, final State state)
	    throws NullPointerException {
	// stateName nor state can be null
	if (stateName == null) {
	    throw new NullPointerException("Registering state name is null");
	}

	if (state == null) {
	    throw new NullPointerException(
		    "Registering state is null with name: " + stateName);
	}

	// Check if state has already been registered
	if (this.stateMap.containsKey(stateName)) {
	    throw new IllegalArgumentException("State with name: " + stateName
		    + " is already registered");
	}

	// Register this state
	this.stateMap.put(stateName, state);
    }

    /**
     * Pushes the state represented by the stateName to the top of the stack to
     * be the current state.
     * 
     * @param stateName
     *            The name of the state when it was registered
     * @throws IllegalArgumentException
     *             if the state has not been registered
     */
    public void pushState(final String stateName)
	    throws IllegalArgumentException {
	if (!this.stateMap.containsKey(stateName)) {
	    throw new IllegalArgumentException("State name (" + stateName
		    + ") does not exist in registered states");
	}

	// Adds the new state onto the state stack using the name lookup
	pushState(this.stateMap.get(stateName));
    }

    /**
     * Pushes the state to the top of the stack to be the current state. This
     * override is useful if you want to add a temporary state that doesn't need
     * to be registered with the state machine. This method also initializes the
     * state and modifies the JFrame to use the new state.
     * 
     * @param state
     *            An instance of the state to push
     * @throws NullPointerException
     *             if the state is null
     */
    public void pushState(final State state) throws NullPointerException {
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

	// Adds the new state to the stack (making it current)
	this.stateStack.push(state);
    }

    /**
     * Pops the current state off the stack and modifies the JFrame to use the
     * new state.
     * 
     * @return The current state
     * @throws NoSuchElementException
     *             if there are no states on the stack
     */
    public State popState() throws NoSuchElementException {
	// The old state that is being popped
	final State resultState = this.stateStack.pop();

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

    /**
     * Returns an instance of the current state. Does not remove the state from
     * the stack.
     * 
     * @return An instance of the current state. If the state stack is empty,
     *         then null is returned.
     */
    public State getCurrentState() {
	// Returns null if there is nothing on the stack
	if (this.stateStack.isEmpty()) {
	    return null;
	}

	// Returns the top of the stack to get the current state (does not pop
	// the state)
	return this.stateStack.peek();
    }

    /**
     * Sets the JFrame to modify for this StateMachine instance.
     * 
     * @param newFrame
     *            The JFrame to modify
     */
    public void setJFrame(final JFrame newFrame) {
	this.mainWindow = newFrame;
    }

}
