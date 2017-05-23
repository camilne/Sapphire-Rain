package com.sr.game;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public abstract class State extends JPanel {

    public abstract void init();

    public abstract void update();

    public abstract void render();

}
