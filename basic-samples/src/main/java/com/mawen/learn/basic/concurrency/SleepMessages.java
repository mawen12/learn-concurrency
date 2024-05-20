package com.mawen.learn.basic.concurrency;

/**
 * {@link Thread#sleep(long)} causes the current thread to suspend execution for a specified period.
 * Two overloaded versions of sleep are provided: one that specifies the sleep time to the millisecond;
 * one that specifies the sleep time to the nanosecond.
 *
 * These sleep times are not guaranteed to be precise, because they are limited by the facilities provided by the underlying OS.
 *
 * {@link InterruptedException} that sleep throws when another thread interrupts the current thread while sleep is active.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/sleep.html">sleep</a>
 * @since 2024/5/20
 */
public class SleepMessages {

	public static void main(String[] args) throws InterruptedException {

		String[] importantInfo = {"Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"};

		for (int i = 0; i < importantInfo.length; i++) {
			// Pause for 4 seconds
			Thread.sleep(4000);
			// Print a message
			System.out.println(importantInfo[i]);
		}
	}
}
