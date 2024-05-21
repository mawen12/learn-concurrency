package com.mawen.learn.basic.concurrency;

/**
 * {@code volatile} can guarantee reads and writes are atomic for all variables (including long and double variables).
 * atomic actions cannot be interleaved, so that can be used without fear of thread interference.
 * any write to a {@code volatile} variable establishes a happens-before relationship with subsequent reads of that same variable.
 * this means that changes to a {@code volatile} variable are always visible to other threads.
 *
 * <p>
 * Using simple atomic variable access is more efficient than accessing these variables through synchronized code,
 * but requires more care by the programmer to avoid memory consistency errors.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/atomic.html">atomic</a>
 * @since 2024/5/21
 */
public class VolatileCounter {

	private volatile int c;

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
