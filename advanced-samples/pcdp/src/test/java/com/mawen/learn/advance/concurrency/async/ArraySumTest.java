package com.mawen.learn.advance.concurrency.async;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static com.mawen.learn.advance.concurrency.PCDP.*;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/6/26
 */
public class ArraySumTest {

	@AfterEach
	public void tearDown() {}

	@Test
	public void sum10Test() {
		final int dataLength = 10;
		performTest(dataLength);
	}

	@Test
	public void sum100Test() {
		final int dataLength = 100;
		performTest(dataLength);
	}

	@Test
	public void sum1000Test() {
		final int dataLength = 1000;
		performTest(dataLength);
	}

	private void performTest(final int dataLength) {
		final int[] data = new int[dataLength];
		for (int i = 0; i < data.length; i++) {
			data[i] = 2 * i;
		}

		final int actual = kernel(data);
		final int excepted = sum(data, 0, data.length);
	}

	private static int kernel(final int[] arr) {

		final int length = arr.length;
		final int mid = length / 2;
		final int left = mid / 2;
		final int right = mid + left;

		final int[] res = new int[4];
		finish(() -> {
			async(() -> res[0] = sum(arr, 0, left));
			async(() -> res[1] = sum(arr, left, mid));
			async(() -> res[2] = sum(arr, mid, right));
			res[3] = sum(arr, right, length);
		});

		return res[0] + res[1] + res[2] + res[3];
	}

	private static int sum(final int[] arr, final int start, final int end) {
		int sum = 0;

		for (int i = start; i < end; i++) {
			sum += arr[i];
		}

		return sum;
	}
}
