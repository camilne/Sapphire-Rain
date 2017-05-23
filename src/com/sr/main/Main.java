package com.sr.main;

import javax.swing.JFrame;

import com.sr.game.GameScreen;
import com.sr.game.StateMachine;

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
	frame.setVisible(true);

	Thread gameThread = new Thread(() -> {
	    final GameScreen game = new GameScreen();
	    game.setSize(this.width, this.height);

	    StateMachine stateMachine = new StateMachine(frame);
	    stateMachine.pushState(game);

	    while (true) {
		stateMachine.update();
		stateMachine.render();
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
