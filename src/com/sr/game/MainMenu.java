package com.sr.game;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class MainMenu extends State {

    private static final long serialVersionUID = 1L;

    @Override
    public void init() {
	setLayout(new GridBagLayout());
	final GridBagConstraints constraints = new GridBagConstraints();
	constraints.fill = GridBagConstraints.HORIZONTAL;

	final JLabel labelTitle = new JLabel("Sapphire Rain");
	labelTitle.setFont(new Font("Consolas", Font.ITALIC, 30));
	constraints.gridx = 0;
	constraints.gridy = 0;
	add(labelTitle, constraints);

	final JButton buttonStartGame = new JButton("Start Game");
	buttonStartGame.addActionListener((ActionEvent e) -> {
	    StateMachine.getInstance().pushState("game");
	});
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
