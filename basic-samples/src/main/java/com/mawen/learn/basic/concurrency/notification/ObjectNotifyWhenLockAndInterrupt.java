package com.mawen.learn.basic.concurrency.notification;

import java.time.LocalDateTime;

/**
 * If a thread is both notified and interrupted while waiting, it will do flowing process:
 * <ul>
 * <li>1.return normally form wait, while still having a pending interrupt</li>
 * <li>2.return from wait by throwing an {@link InterruptedException}</li>
 * </ul>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectNotifyWhenLockAndInterrupt {

	public synchronized void callWait() throws InterruptedException {
		System.out.println("Execute callWait..." + LocalDateTime.now());
		this.wait();
		Thread.sleep(10L); // wait interrupted
		System.out.println("Execute callWait end..." + LocalDateTime.now());
	}

	public synchronized void callNotifyAndInterrupt(final Thread another) throws InterruptedException {
		System.out.println("Execute callNotifyAndInterrupt...");
		this.notify();
		another.interrupt();
		System.out.println("Execute callNotifyAndInterrupt end...");
	}

	public static void main(String[] args) throws InterruptedException {
		ObjectNotifyWhenLockAndInterrupt object = new ObjectNotifyWhenLockAndInterrupt();

		Thread t1 = new Thread(() -> {
			try {
				object.callWait();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				object.callNotifyAndInterrupt(t1);
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		t1.start();
		Thread.sleep(10L);
		t2.start();

		Thread.sleep(10000L);
	}
}
