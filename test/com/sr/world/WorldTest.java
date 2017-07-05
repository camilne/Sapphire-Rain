package com.sr.world;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.text.AttributedCharacterIterator;

import org.junit.jupiter.api.Test;

public class WorldTest {

    private static final double EPSILON = 1e-4;

    @SuppressWarnings("static-method")
    @Test
    public void canCreateNewDefaultWorld() throws IOException {
	final World instance = new World();

	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canAddEntityToWorld() throws IOException {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	final boolean addResult = instance.addEntity(entity);

	assertTrue(addResult, "Did not add entity to world successfully");
	assertEquals(1, instance.getEntityCount());
	assertEquals(0.0, instance.getX(), EPSILON);
	assertEquals(0.0, instance.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void cannotAddDuplicateEntityToWorld() throws IOException {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);
	final boolean addResult = instance.addEntity(entity);

	assertFalse(addResult, "Should not have added to world but did");
	assertEquals(1, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canRemoveEntityFromWorld() throws IOException {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	instance.addEntity(entity);

	final boolean removeResult = instance.removeEntity(entity);

	assertTrue(removeResult,
		"Should have removed entity from world successfully");
	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canSafelyRemoveNonExistentEntityFromWorld() throws IOException {
	final World instance = new World();
	final Entity entity = new Entity() {

	    @Override
	    public void update() {
		// Empty
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};

	final boolean removeResult = instance.removeEntity(entity);

	assertFalse(removeResult,
		"Should not have removed entity from world successfully");
	assertEquals(0, instance.getEntityCount());
    }

    @SuppressWarnings("static-method")
    @Test
    public void canTranslateWorldWithPositiveArbitraryOffsets()
	    throws IOException {
	final World instance = new World();
	final double x1 = 1052.352340;
	final double y1 = 25082.23058;

	instance.translate(x1, y1);

	assertEquals(x1, instance.getX(), EPSILON);
	assertEquals(y1, instance.getY(), EPSILON);

	final double x2 = 12094.0289;
	final double y2 = 48579.28973;

	instance.translate(x2, y2);

	assertEquals(x1 + x2, instance.getX(), EPSILON);
	assertEquals(y1 + y2, instance.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canTranslateWorldWithNegativeArbitraryOffsets()
	    throws IOException {
	final World instance = new World();
	final double x1 = -2232.12352;
	final double y1 = -2583.345938;

	instance.translate(x1, y1);

	assertEquals(x1, instance.getX(), EPSILON);
	assertEquals(y1, instance.getY(), EPSILON);

	final double x2 = -2325.2894;
	final double y2 = -87981.4380;

	instance.translate(x2, y2);

	assertEquals(x1 + x2, instance.getX(), EPSILON);
	assertEquals(y1 + y2, instance.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canUpdateEntitiesContainedInWorld() throws IOException {
	final World instance = new World();
	final double offsetx = 10.0;
	final double offsety = 2.0;

	final Entity entity1 = new Entity() {

	    @Override
	    public void update() {
		this.x += offsetx;
		this.y += offsety;
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};
	instance.addEntity(entity1);

	final Entity entity2 = new Entity() {

	    @Override
	    public void update() {
		this.x -= offsetx;
		this.y -= offsety;
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};
	instance.addEntity(entity2);

	instance.update();

	assertEquals(offsetx, entity1.getX(), EPSILON);
	assertEquals(offsety, entity1.getY(), EPSILON);

	assertEquals(-offsetx, entity2.getX(), EPSILON);
	assertEquals(-offsety, entity2.getY(), EPSILON);
    }

    @SuppressWarnings("static-method")
    @Test
    public void canRenderEntitiesContainedInWorld() throws IOException {
	final World instance = new World();
	final double offsetx = 10.0;
	final double offsety = 2.0;

	final Entity entity1 = new Entity() {

	    @Override
	    public void update() {
		this.x += offsetx;
		this.y += offsety;
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};
	instance.addEntity(entity1);

	final Entity entity2 = new Entity() {

	    @Override
	    public void update() {
		this.x -= offsetx;
		this.y -= offsety;
	    }

	    @Override
	    public void render(final Graphics g) {
		// Empty
	    }

	};
	instance.addEntity(entity2);

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
	    public void drawRoundRect(final int x, final int y, final int width,
		    final int height, final int arcWidth, final int arcHeight) {
		// Empty
	    }

	    @Override
	    public void fillRoundRect(final int x, final int y, final int width,
		    final int height, final int arcWidth, final int arcHeight) {
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
		    final int height, final int startAngle,
		    final int arcAngle) {
		// Empty
	    }

	    @Override
	    public void fillArc(final int x, final int y, final int width,
		    final int height, final int startAngle,
		    final int arcAngle) {
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
	instance.render(dummyGraphics);
    }
}
