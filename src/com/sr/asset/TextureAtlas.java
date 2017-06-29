package com.sr.asset;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class TextureAtlas {

    // Holds the full texture for this atlas.
    private BufferedImage texture;
    // Holds the individual textures that will be used by the program.
    private HashMap<String, BufferedImage> atlas;

    /**
     * This TextureAtlas is used to split an image into smaller sub-textures and
     * allow the program to reference them by name. A reference texture is used
     * to create the TextureAtlas and textures can then be registered and
     * retrieved by name.
     * 
     * @param texture
     *            the reference texture
     */
    public TextureAtlas(final BufferedImage texture) {
	this.texture = texture;
	this.atlas = new HashMap<>();
    }

    /**
     * Registers a sub-texture with the texture atlas. If a texture with the
     * same name has already been registered, the texture is overwritten.
     * 
     * @param name
     *            the name by which to reference the texture
     * @param area
     *            the area from which to sample the atlas texture for the new
     *            texture
     * @throws NullPointerException
     *             if name or area is <code>null</code>.
     * @throws IllegalArgumentException
     *             if the area is completely outside the texture.
     */
    public void registerTexture(final String name, final Rectangle area)
	    throws NullPointerException, IllegalArgumentException {
	if (name == null) {
	    throw new NullPointerException(
		    "TextureAtlas registering name cannot be null");
	}

	if (area == null) {
	    throw new NullPointerException(
		    "TextureAtlas registering area cannot be null");
	}

	if (this.atlas.containsKey(name)) {
	    // Warn to the console if this name has already been registered.
	    System.out.println("[WARN] - Reassigning texture: " + name);
	}

	// Check bounds of new area to make sure they are within the texture.
	final Rectangle textureArea = new Rectangle(0, 0,
		this.texture.getWidth(), this.texture.getHeight());
	if (!area.intersects(textureArea)) {
	    throw new IllegalArgumentException(
		    "TextureAtlas new texture is completely ouside the texture bounds: "
			    + area.toString());
	}

	// Create sub-texture and add it to the map.
	final BufferedImage newTexture = this.texture.getSubimage(area.x,
		area.y, area.width, area.height);
	this.atlas.put(name, newTexture);
    }

    /**
     * Registers a repeated amount of textures that are the same size. The
     * naming scheme is "prefix + i", where i is in [0, amount). The method
     * begins at the first rectangle, and iterates to the right of the reference
     * texture and wraps to align with the first rectangle when it hits the edge
     * of the reference texture.
     * 
     * @param prefix
     *            the prefix of the naming scheme
     * @param first
     *            the first location and size of the texture
     * @param amount
     *            the amount of textures to load in
     */
    public void registerRepeated(final String prefix, final Rectangle first,
	    final int amount) {
	// Get a non-null prefix
	final String nonNullPrefix;
	if (prefix == null) {
	    System.out.println(
		    "[WARN] - registerRepeated: prefix is null, assigning default");
	    nonNullPrefix = "";
	} else {
	    nonNullPrefix = prefix;
	}

	// Get a non-null first rectangle
	final Rectangle nonNullFirst;
	if (first == null) {
	    System.out.println(
		    "[WARN] - registerRepeated: first is null, assigning default");
	    nonNullFirst = new Rectangle(0, 0, getWidth() / amount,
		    getHeight());
	} else {
	    nonNullFirst = first;
	}

	if (amount <= 0) {
	    throw new IllegalArgumentException(
		    "registerRepeated: amount cannot be less than or equal to zero: "
			    + amount);
	}

	// Holds the current location of the texture
	final Rectangle current = new Rectangle(nonNullFirst);

	for (int i = 0; i < amount; i++) {
	    registerTexture(nonNullPrefix + String.valueOf(i), current);

	    current.x += current.width;
	    // Tiling has gone off texture and needs to wrap
	    if (current.x + current.width > getWidth()) {
		current.x = nonNullFirst.x;
		current.y += current.height;
	    }
	}
    }

    /**
     * Returns the sub-texture that was registered with this name. If
     * <code>null</code> is passed in as name, then the entire reference texture
     * is returned.
     * 
     * @param name
     *            the name of the registered texture, or if name is
     *            <code>null</code>, then the reference texture is returned
     * @return the requested texture
     * @throws IllegalArgumentException
     *             if the name has not been registered with the TextureAtlas
     */
    public BufferedImage getTexture(final String name)
	    throws IllegalArgumentException {
	// Return whole reference texture if name is null
	if (name == null) {
	    return this.texture;
	}

	// Check if key is registered
	if (!this.atlas.containsKey(name)) {
	    throw new IllegalArgumentException(
		    "TextureAtlas does not have name registered: " + name);
	}

	return this.atlas.get(name);
    }

    /**
     * Returns the width of the reference texture
     * 
     * @return the width of the reference texture in pixels
     */
    public int getWidth() {
	return this.texture.getWidth();
    }

    /**
     * Returns the height of the reference texture
     * 
     * @return the height of the reference texture in pixels
     */
    public int getHeight() {
	return this.texture.getHeight();
    }

}
