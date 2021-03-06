package com.sr.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;
import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sr.asset.TextureAtlas;

public class PlayerTest {

    private static final int IMAGE_WIDTH = 400;
    private static final int IMAGE_HEIGHT = 500;
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    private static final double EPSILON = 0.001;

    private Player instance;

    @BeforeEach
    public void setUp() throws Exception {
	final BufferedImage dummyImage = new BufferedImage(IMAGE_WIDTH,
		IMAGE_HEIGHT, IMAGE_TYPE);
	final TextureAtlas dummyAtlas = new TextureAtlas(dummyImage);
	this.instance = new Player(0.0, 0.0, dummyAtlas);
    }

    @Test
    public void canUpdatePlayer() {
	final LinkedList<Rectangle> dummyColliders = new LinkedList<>();
	final double deltaTime = 0.0;
	this.instance.update(deltaTime, dummyColliders);

	assertEquals(0.0, this.instance.getX(), EPSILON);
	assertEquals(0.0, this.instance.getY(), EPSILON);
    }

    @Test
    public void canRenderPlayer() {
	final Graphics dummyGraphics = new Graphics() {

	    @Override
	    public Graphics create() {
		return null;

	    }

	    @Override
	    public void translate(final int x, final int y) {
		// Empty
	    }

	    @Override
	    public Color getColor() {
		return null;

	    }

	    @Override
	    public void setColor(final Color c) {
		// Empty
	    }

	    @Override
	    public void setPaintMode() {
		// Empty
	    }

	    @Override
	    public void setXORMode(final Color c1) {
		// Empty
	    }

	    @Override
	    public Font getFont() {
		return null;

	    }

	    @Override
	    public void setFont(final Font font) {
		// Empty
	    }

	    @Override
	    public FontMetrics getFontMetrics(final Font f) {
		return null;

	    }

	    @Override
	    public Rectangle getClipBounds() {
		return null;

	    }

	    @Override
	    public void clipRect(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public void setClip(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public Shape getClip() {
		return null;

	    }

	    @Override
	    public void setClip(final Shape clip) {
		// Empty
	    }

	    @Override
	    public void copyArea(final int x, final int y, final int width,
		    final int height, final int dx, final int dy) {
		// Empty
	    }

	    @Override
	    public void drawLine(final int x1, final int y1, final int x2,
		    final int y2) {
		// Empty
	    }

	    @Override
	    public void fillRect(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public void clearRect(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public void drawRoundRect(final int x, final int y,
		    final int width, final int height, final int arcWidth,
		    final int arcHeight) {
		// Empty
	    }

	    @Override
	    public void fillRoundRect(final int x, final int y,
		    final int width, final int height, final int arcWidth,
		    final int arcHeight) {
		// Empty
	    }

	    @Override
	    public void drawOval(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public void fillOval(final int x, final int y, final int width,
		    final int height) {
		// Empty
	    }

	    @Override
	    public void drawArc(final int x, final int y, final int width,
		    final int height, final int startAngle, final int arcAngle) {
		// Empty
	    }

	    @Override
	    public void fillArc(final int x, final int y, final int width,
		    final int height, final int startAngle, final int arcAngle) {
		// Empty
	    }

	    @Override
	    public void drawPolyline(final int[] xPoints, final int[] yPoints,
		    final int nPoints) {
		// Empty
	    }

	    @Override
	    public void drawPolygon(final int[] xPoints, final int[] yPoints,
		    final int nPoints) {
		// Empty
	    }

	    @Override
	    public void fillPolygon(final int[] xPoints, final int[] yPoints,
		    final int nPoints) {
		// Empty
	    }

	    @Override
	    public void drawString(final String str, final int x, final int y) {
		// Empty
	    }

	    @Override
	    public void drawString(final AttributedCharacterIterator iterator,
		    final int x, final int y) {
		// Empty
	    }

	    @Override
	    public boolean drawImage(final Image img, final int x, final int y,
		    final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public boolean drawImage(final Image img, final int x, final int y,
		    final int width, final int height,
		    final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public boolean drawImage(final Image img, final int x, final int y,
		    final Color bgcolor, final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public boolean drawImage(final Image img, final int x, final int y,
		    final int width, final int height, final Color bgcolor,
		    final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public boolean drawImage(final Image img, final int dx1,
		    final int dy1, final int dx2, final int dy2, final int sx1,
		    final int sy1, final int sx2, final int sy2,
		    final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public boolean drawImage(final Image img, final int dx1,
		    final int dy1, final int dx2, final int dy2, final int sx1,
		    final int sy1, final int sx2, final int sy2,
		    final Color bgcolor, final ImageObserver observer) {
		return false;

	    }

	    @Override
	    public void dispose() {
		// Empty
	    }

	};

	this.instance.render(dummyGraphics);
    }

}
