package com.sr.game;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class State extends JPanel {

    /**
     * Called once when the state is made to be the active state. Should hold
     * initialization logic for the state.
     */
    public abstract void init();

    /**
     * Called at the game refresh rate before render. Should hold updating logic
     * for the state.
     * 
     * @param deltaTime
     *            the time in milliseconds since the last update
     */
    public abstract void update(final double deltaTime);

    /**
     * Called at the game refresh rate after update. Should hold rendering code
     * for the state.
     */
    public abstract void render();

}
