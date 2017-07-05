package com.sr.game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

import com.sr.coverage.CoverageIgnore;

@SuppressWarnings("serial")
public abstract class State extends JPanel implements KeyListener {

    public State() {
	addKeyListener(this);
    }

    @CoverageIgnore
    @Override
    public void addNotify() {
	super.addNotify();
	requestFocus();
    }

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
     *            the time in seconds since the last update
     */
    public abstract void update(final double deltaTime);

    /**
     * Called at the game refresh rate after update. Should hold rendering code
     * for the state.
     */
    public abstract void render();

    public abstract void keyDown(final int keyCode);

    public abstract void keyUp(final int keyCode);

    @CoverageIgnore
    @Override
    public void keyTyped(final KeyEvent e) {
	// Empty
    }

    @CoverageIgnore
    @Override
    public void keyPressed(final KeyEvent e) {
	keyDown(e.getKeyCode());
    }

    @CoverageIgnore
    @Override
    public void keyReleased(final KeyEvent e) {
	keyUp(e.getKeyCode());
    }

}
