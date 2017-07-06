package com.sr.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sr.asset.TextureAtlas;
import com.sr.main.Main;
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
	this.world.input();

	this.world.update(deltaTime, this.player.getCX(), this.player.getCY());

	// Translate world to center on player
	final double x = this.player.getRelativeBoundingBox().getX();
	final double width = this.player.getRelativeBoundingBox().getWidth();
	final double y = this.player.getRelativeBoundingBox().getY();
	final double height = this.player.getRelativeBoundingBox().getHeight();
	this.world.setTranslation(-x - width / 2, -y - height / 2);
    }

    @Override
    public void render() {
	repaint();
    }

    @Override
    public void paintComponent(final Graphics g) {
	super.paintComponent(g);

	// Render black background
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

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
