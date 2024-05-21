package com.mawen.learn.basic.concurrency;

/**
 * If a {@link Counter} object is referenced from multiple threads, interference between threads may prevent this from happening as excepted.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/interfere.html">interfere</a>
 * @since 2024/5/21
 */
public class Counter {

	private int c;

	public void increment() {
		c++;
	}

	public void decrement() {
		c--;
	}

	public int getValue() {
		return c;
	}

}
