package com.mawen.learn.basic.concurrency.wait;

/**
 * When Thread t has lock the monitor and call {@link Object#wait()}, then another thread call Thread t {@link Thread#interrupt()},
 * Thread t will throw {@link InterruptedException}, and Thread t's interruption status is set to false.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectWaitWhenInterruptThenThrowInterruptedException {

	public synchronized void callWait() throws InterruptedException {
		System.out.println("into callWait");
		this.wait();
		System.out.println("over callWait");
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(() -> {
			try {
				new ObjectWaitWhenInterruptThenThrowInterruptedException().callWait();
			}
			catch (InterruptedException e) {
				System.err.println("Catch InterruptedException " + e);
			}
		});

		t.start();

		t.interrupt();

		Thread.sleep(100L);

		System.out.println("Thread t is interrupted: " + t.isInterrupted());
		System.out.println("Thread t is alive: " + t.isAlive());
	}
}
