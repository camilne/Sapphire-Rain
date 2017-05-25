package com.sr.game;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainMenu extends State {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
	// Set the layout of the main menu to be a grid
	setLayout(new GridBagLayout());
	final GridBagConstraints constraints = new GridBagConstraints();
	constraints.fill = GridBagConstraints.HORIZONTAL;

	// Creates a title for the main menu
	final JLabel labelTitle = new JLabel("Sapphire Rain");
	labelTitle.setFont(new Font("Consolas", Font.ITALIC, 30));
	// Set the title of the game to be in the top middle
	constraints.gridx = 0;
	constraints.gridy = 0;
	add(labelTitle, constraints);

	// Creates a button to start the game
	final JButton buttonStartGame = new JButton("Start Game");
	buttonStartGame.addActionListener((ActionEvent e) -> {
	    StateMachine.getInstance().pushState("game");
	});
	// Set the start button to be below the title
	constraints.gridx = 0;
	constraints.gridy = 1;
	add(buttonStartGame, constraints);
    }

    @Override
    public void update() {
	// Empty
    }

    @Override
    public void render() {
	repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
	super.paintComponent(g);
	// Empty
    }

}
