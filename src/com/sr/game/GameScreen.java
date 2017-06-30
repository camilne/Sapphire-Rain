package com.sr.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sr.asset.TextureAtlas;
import com.sr.world.Player;
import com.sr.world.World;

public class GameScreen extends State {

    private static final long serialVersionUID = 1L;

    private World world;
    private Player player;

    @Override
    public void init() {
	try {
	    this.world = new World();

	    // Create the player
	    final String playerTextureLocation = "./resources/player.png";
	    final BufferedImage playerTexture = ImageIO.read(new File(
		    playerTextureLocation));
	    final TextureAtlas playerAtlas = new TextureAtlas(playerTexture);
	    this.player = new Player(10, 0, playerAtlas);
	    this.world.addEntity(this.player);
	} catch (final IOException e) {
	    e.printStackTrace();
	    System.exit(1);
	}
    }

    @Override
    public void update(final double deltaTime) {
	this.world.update(deltaTime);
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

    @Override
    public void keyDown(final int keyCode) {
	this.player.keyDown(keyCode);
    }

    @Override
    public void keyUp(final int keyCode) {
	this.player.keyUp(keyCode);
    }

}
