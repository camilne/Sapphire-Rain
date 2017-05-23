package com.sr.main;

import javax.swing.JFrame;

import com.sr.game.GameScreen;

public class Main {

    private final String title = "Sapphire Rain";
    private final int width = 1280;
    private final int height = 720;

    private Main() {
	final JFrame frame = new JFrame();
	frame.setTitle(this.title);
	frame.setSize(this.width, this.height);
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	final GameScreen game = new GameScreen();
	game.setSize(this.width, this.height);
	frame.add(game);

	frame.setVisible(true);

	Thread gameThread = new Thread(() -> {
	    game.init();

	    while (true) {
		game.update();
		game.repaint();
	    }
	});
	gameThread.setDaemon(true);
	gameThread.start();
    }

    @SuppressWarnings("unused")
    public static void main(String[] args) {
	new Main();
    }

}
