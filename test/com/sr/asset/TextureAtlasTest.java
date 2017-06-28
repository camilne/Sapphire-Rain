package com.sr.asset;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TextureAtlasTest {

    private final static int WIDTH = 512;
    private final static int HEIGHT = 256;
    private final static int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    private TextureAtlas instance;
    private BufferedImage emptyImage;

    @BeforeEach
    public void setUp() throws Exception {
	this.emptyImage = new BufferedImage(WIDTH, HEIGHT, IMAGE_TYPE);
	this.instance = new TextureAtlas(this.emptyImage);
    }

    @Test
    public void canRegisterValidTexture() {
	final String name = "test";
	final Rectangle area = new Rectangle(0, 0, 128, 128);

	this.instance.registerTexture(name, area);
    }

    @Test
    public void canRegisterMultipleValidTextures() {
	final String name1 = "test";
	final Rectangle area1 = new Rectangle(0, 0, 128, 128);

	this.instance.registerTexture(name1, area1);

	final String name2 = "test2";
	final Rectangle area2 = new Rectangle(128, 128, 128, 128);

	this.instance.registerTexture(name2, area2);
    }

    @Test
    public void throwsNullPointerExceptionIfNameIsNull() {
	final String name = null;
	final Rectangle area = new Rectangle(0, 0, 64, 64);

	assertThrows(NullPointerException.class,
		() -> this.instance.registerTexture(name, area));
    }

    @Test
    public void throwsNullPointerExceptionIfAreaIsNull() {
	final String name = "test";
	final Rectangle area = null;

	assertThrows(NullPointerException.class,
		() -> this.instance.registerTexture(name, area));
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenRegisteringBoundsAreOutsideTexture() {
	final String name = "test";
	final Rectangle area = new Rectangle(-128, 0, 64, 64);

	assertThrows(IllegalArgumentException.class,
		() -> this.instance.registerTexture(name, area));
    }

    @Test
    public void canGetValidTexture() {
	final String name = "test";
	final Rectangle area = new Rectangle(50, 25, 10, 10);
	this.instance.registerTexture(name, area);

	assertNotEquals(null, this.instance.getTexture(name));
    }

    @Test
    public void canGetReferenceTextureWhenNameIsNull() {
	final String name = null;

	assertEquals(this.emptyImage, this.instance.getTexture(name));
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenNameHasNotBeenRegistered() {
	final String name = "non-existent";

	assertThrows(IllegalArgumentException.class,
		() -> this.instance.getTexture(name));
    }

}
