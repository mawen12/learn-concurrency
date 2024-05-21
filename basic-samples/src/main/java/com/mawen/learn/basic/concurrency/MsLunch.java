package com.mawen.learn.basic.concurrency;

/**
 * Synchronized statements are also useful for improving concurrency with fine-grained synchronization.
 * class {@link MsLunch} has two instance fields, c1 and c2, that are never used together.
 * All updates of these fields must be synchronized, but there's  no reason to prevent an update of c1 from
 * being interleaved with an update of c2, and doing so reduces concurrency by creating unnecessary blocking.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/locksync.html">locksync</a>
 * @since 2024/5/21
 */
public class MsLunch {

	private long c1;
	private long c2;
	private Object lock1 = new Object();
	private Object lock2 = new Object();

	public void lock1() {
		synchronized (lock1) {
			c1++;
		}
	}

	public void lock2() {
		synchronized (lock2) {
			c2++;
		}
	}
}
