package com.mawen.learn.basic.concurrency;

/**
 * It is not possible for two invocations of synchronized methods on the same object to interleave.
 * When one thread is executing method for an object, all other threads that invoke synchronized methods
 * for the same object lock(suspend execution) util the first thread is done with the object.
 * when a synchronized method exists, it automatically establishes a happens-before relationship with
 * any subsequent invocation of a synchronized method for the same object. This guarantees that changes to
 * the state of the object are visible to all threads.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/syncmeth.html">syncmeth</a>
 * @since 2024/5/21
 */
public class SynchronizedCounter {

	private int c;

	public synchronized void increment() {
		c++;
	}

	public synchronized void decrement() {
		c--;
	}

	public synchronized int getValue() {
		return c;
	}
}
