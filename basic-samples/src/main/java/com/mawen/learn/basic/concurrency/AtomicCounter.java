package com.mawen.learn.basic.concurrency;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Use {@link AtomicInteger} replace {@code synchronization} allow us to prevent thread interference without resorting to synchronization.
 * and avoid the liveness impact of unnecessary synchronization.
 *
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/atomicvars.html">atomicvars</a>
 * @since 2024/5/22
 */
public class AtomicCounter {

	private AtomicInteger c = new AtomicInteger(0);

	public void increment() {
		c.incrementAndGet();
	}

	public void decrement() {
		c.decrementAndGet();
	}

	public int value() {
		return c.get();
	}
}
