package com.mawen.learn.basic.concurrency;

/**
 * Use Immutable Objects in concurrent applications.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/imstrat.html">imstrat</a>
 * @since 2024/5/21
 */
public class ImmutableRGB {

	final private int red;
	final private int green;
	final private int blue;
	final private String name;

	private void check(int red, int green, int blue) {
		if (red < 0 || red > 255
				|| green < 0 || green > 255
				|| blue < 0 || blue > 255) {
			throw new IllegalArgumentException();
		}
	}

	public ImmutableRGB(int red, int green, int blue, String name) {
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.name = name;
	}

	public int getRGB() {
		return (red << 16 | green << 8 | blue);
	}

	public String getName() {
		return name;
	}

	public ImmutableRGB invert() {
		return new ImmutableRGB(
				255 - red,
				255 - green,
				255 - blue,
				"Inverse of " + name);
	}
}
