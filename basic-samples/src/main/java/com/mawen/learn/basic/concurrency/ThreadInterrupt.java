package com.mawen.learn.basic.concurrency;

/**
 * An {@code interrupt} is an indication to a thread that it should stop what it is doing and do something else.
 * Many methods that throw {@link InterruptedException}, such as {@link Thread#sleep(long)},
 * are designed to cancel their current operation and return immediately when an interrupt is received.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/interrupt.html">interrupt</a>
 * @since 2024/5/21
 */
public class ThreadInterrupt {

	public static void main(String[] args) {
		String[] importantInfo = {"Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"};

		for (int i = 0; i < importantInfo.length; i++) {
			// Pause for 4 seconds
			try {
				Thread.sleep(4000);
			}
			catch (InterruptedException e) {
				// We've been interrupted: no more messages.
				return;
			}

			// Print a message
			System.out.println(importantInfo[i]);
		}
	}
}
