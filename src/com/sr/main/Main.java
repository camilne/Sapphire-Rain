package com.sr.main;

import javax.swing.JFrame;

import com.sr.game.GameScreen;
import com.sr.game.MainMenu;
import com.sr.game.StateMachine;

public class Main {

    // The title of the application
    private final String title = "Sapphire Rain";
    // The width of the window
    public static final int WIDTH = 1280;
    // The height of the window
    public static final int HEIGHT = 720;
    // Is in debug mode
    public static final boolean DEBUG = false;

    private Main() {
	// Create main JFrame for the application
	final JFrame frame = new JFrame();
	frame.setTitle(this.title);
	frame.setSize(WIDTH, HEIGHT);
	// Make window appear in the center of the screen
	frame.setLocationRelativeTo(null);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.setResizable(false);
	// Show the window
	frame.setVisible(true);

	// Thread for running the application
	final Thread gameThread = new Thread(() -> {
	    // Create a state machine to hold the state of the application
		final StateMachine stateMachine = new StateMachine(frame);
		// Register initial states
		stateMachine.registerState("main-menu", new MainMenu(
			stateMachine));
		stateMachine.registerState("game", new GameScreen());

		// Make the main menu the current state
		stateMachine.pushState("main-menu");

		// Setup delta-time
		long lastTime = System.nanoTime();
		long nowTime = System.nanoTime();

		// Continue until the application closes
		while (true) {
		    // Calculate delta time (in seconds)
		    final double deltaTime = (nowTime - lastTime) / 1_000_000_000.0;
		    lastTime = nowTime;
		    nowTime = System.nanoTime();

		    // Update and render the current state
		    stateMachine.update(deltaTime);
		    stateMachine.render();

		    // System.out.println("FPS: " + (1.0 / deltaTime));

		    // Slow the refresh rate to 60Hz
		    try {
			final double targetDelta = 1000.0 / 60.0;
			Thread.sleep((int) (targetDelta - deltaTime));
		    } catch (final Exception e) {
			e.printStackTrace();
		    }
		}
	    });
	// Allow the application to stop when the window closes
	gameThread.setDaemon(true);
	// Start the runnable on a new thread
	gameThread.start();
    }

    @SuppressWarnings("unused")
    public static void main(final String[] args) {
	new Main();
    }

}
