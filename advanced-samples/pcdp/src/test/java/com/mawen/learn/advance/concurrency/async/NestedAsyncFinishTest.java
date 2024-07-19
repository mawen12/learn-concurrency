package com.mawen.learn.advance.concurrency.async;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.mawen.learn.advance.concurrency.PCDP.*;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/26
 */
public class NestedAsyncFinishTest {

	@AfterEach
	public void tearDown() {
	}

	@Test
	public void nestedFinishTest() {
		final int dataLength = 8;
		kernelBody(dataLength);
	}

	protected void kernelBody(final int dataLength) {
		finish(() -> {
			fubar(dataLength, 0, 1, 1, 0);
		});
	}

	private static void fubar(final int dataLength, final int node, final int received, final int index, final int depth) {
		// Base case
		if (index >= dataLength) {
			return;
		}
		// Normal case
		finish(() -> {
			async(() -> {
				fubar(dataLength, node + 1, received, index * 2, depth + 1);
			});
			fubar(dataLength, node, received, index * 2 + 1, depth + 1);
		});
	}

}
