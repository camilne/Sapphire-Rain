package com.sr.main;

import javax.swing.JFrame;

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
	frame.setResizable(false);
	frame.setVisible(true);

	Thread gameThread = new Thread(() -> {
	    StateMachine stateMachine = StateMachine.getInstance();
	    stateMachine.setJFrame(frame);

	    stateMachine.pushState("main-menu");

	    while (true) {
		stateMachine.update();
		stateMachine.render();

		try {
		    Thread.sleep(1000 / 60);
		} catch (Exception e) {
		    e.printStackTrace();
		}
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
