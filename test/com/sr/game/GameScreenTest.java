package com.sr.game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameScreenTest {

    private GameScreen instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.instance = new GameScreen();
    }

    @Test
    public void canInitializeGameScreen() {
	this.instance.init();
    }

    @Test
    public void canUpdateGameScreen() {
	this.instance.update();
    }

    @Test
    public void canRenderGameScreen() {
	this.instance.render();
    }
}
