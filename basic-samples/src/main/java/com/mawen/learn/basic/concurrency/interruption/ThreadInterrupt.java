package com.mawen.learn.basic.concurrency.interruption;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/22
 */
public class ThreadInterrupt {

	public void callSleep() throws InterruptedException {
		Thread.currentThread().sleep(1000L);
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t1 = new Thread(() -> {
			try {
				new ThreadInterrupt().callSleep();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		t1.start();

		Thread.sleep(10L);

		t1.interrupt();
	}
}
