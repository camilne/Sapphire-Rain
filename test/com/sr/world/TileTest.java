package com.sr.world;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.sr.asset.TextureAtlas;

public class TileTest {

    private static final String TILE_NAME = "test";
    private static final Tile.Type TILE_TYPE = Tile.Type.EMPTY;

    private Tile instance;

    @BeforeEach
    public void setUp() throws Exception {
	this.instance = new Tile(TILE_NAME, TILE_TYPE);
    }

    @Test
    public void canRenderTile() {
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

	final BufferedImage dummyImage = new BufferedImage(1, 1,
		BufferedImage.TYPE_INT_RGB);
	final TextureAtlas dummyAtlas = new TextureAtlas(dummyImage);
	final Rectangle dummyArea = new Rectangle(0, 0, 1, 1);
	dummyAtlas.registerTexture(TILE_NAME, dummyArea);

	this.instance.render(dummyGraphics, dummyAtlas, 0, 0);
    }

    @Test
    public void canGetNameOfTile() {
	assertEquals(TILE_NAME, this.instance.getName());
    }

    @Test
    public void canGetTypeOfTile() {
	assertEquals(TILE_TYPE, this.instance.getType());
    }

}
