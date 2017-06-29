package com.sr.game;

import java.awt.Graphics;
import java.io.IOException;

import com.sr.world.World;

public class GameScreen extends State {

    private static final long serialVersionUID = 1L;

    private World world;

    @Override
    public void init() {
	try {
	    this.world = new World();
	} catch (final IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void update() {
	this.world.update();
    }

    @Override
    public void render() {
	repaint();
    }

    @Override
    public void paintComponent(final Graphics g) {
	super.paintComponent(g);

	this.world.render(g);
    }

}
