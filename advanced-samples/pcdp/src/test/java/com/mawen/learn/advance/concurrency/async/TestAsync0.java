package com.mawen.learn.advance.concurrency.async;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.mawen.learn.advance.concurrency.PCDP.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/26
 */
public class TestAsync0 {

	@AfterEach
	public void tearDown() {}

	@Test
	public void testMethod0() {
		final boolean result = new TestAsync0().run0();
		assertEquals(true, result);
	}

	public boolean run0() {
		finish(() -> {
			async(() -> {
				int acc = 0;
			});
		});
		return true;
	}

	@Test
	public void testMethod1() {
		final boolean result = new TestAsync0().run1();
		assertEquals(true, result);
	}

	public boolean run1() {
		finish(() -> {
			async(() -> {
				int acc = 0;
				for (int i = 0; i < 100; i++) {
					acc += i;
				}
			});
		});
		return true;
	}

	@Test
	public void testMethod2() {
		final boolean result = new TestAsync0().run2();
		assertEquals(true, result);
	}

	public boolean run2() {
		final int acc = 1;
		finish(() -> {
			async(() -> {
				int tmp = acc + 1;
			});
		});

		return acc == 1;
	}

	@Test
	public void testMethod4() {
		final boolean result = new TestAsync0().run4();
		assertEquals(true, result);
	}

	public boolean run4() {
		final int[] acc = new int[1];

		finish(() -> {
			async(() -> {
				acc[0] = 1;
			});
		});

		return acc[0] == 1;
	}


	@Test
	public void testMethod5a() {

		final boolean[] result = {false};
		try {
			result[0] = new TestAsync0().run5a();
			assertFalse(result[0]);
		}
		catch (final NullPointerException e) {
			result[0] = true;
			assertTrue(result[0]);
		}
		assertEquals(true, result[0]);
	}

	public boolean run5a() {
		finish(() -> {
			async(() -> {
				final Object obj = null;
				nullPointerException5a(obj);
			});
		});

		return true;
	}

	protected void nullPointerException5a(final Object obj) {
		obj.toString();
	}

	@Test
	public void testMethod5b() {

		final boolean result = new TestAsync0().run5b();
		assertEquals(true, result);
	}

	public boolean run5b() {

		try {
			finish(() -> {
				nullPointerException5b(null);
			});

			assertTrue(false);
		}
		catch (final NullPointerException e) {
			assertTrue(true);
		}

		return true;
	}

	protected void nullPointerException5b(final Object obj) {
		async(obj::toString);
	}

	@Test
	public void testMethod5c() {

		final boolean result = new TestAsync0().run5c();
		assertEquals(true, result);
	}

	public boolean run5c() {

		try {
			finish(() -> {
				final String[] strings = {"1", "a", "2", "b", "3", "c"};
				nullPointerException5c(strings);
			});

			// should not reach here
			assertTrue(false);
		}
		catch (final NullPointerException e) {
			assertTrue(true);
		}

		return true;
	}

	protected void nullPointerException5c(final String[] strings) {
		for (String str : strings) {
			async(() -> Integer.parseInt(str));
		}
	}

	
}
