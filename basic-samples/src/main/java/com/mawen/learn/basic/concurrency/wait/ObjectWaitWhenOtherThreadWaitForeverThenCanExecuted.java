package com.mawen.learn.basic.concurrency.wait;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/22
 */
public class ObjectWaitWhenOtherThreadWaitForeverThenCanExecuted {

	public synchronized void callWait1() throws InterruptedException {
		System.out.println("Execute callWait1...");
		this.wait(1000L);
		System.out.println("Execute callWait1 end...");
	}

	public synchronized void callWait2() throws InterruptedException {
		System.out.println("Execute callWait2...");
		this.wait();
		System.out.println("Execute callWait2 end...");
	}

	public static void main(String[] args) throws InterruptedException {
		ObjectWaitWhenOtherThreadWaitForeverThenCanExecuted object = new ObjectWaitWhenOtherThreadWaitForeverThenCanExecuted();

		Thread t1 = new Thread(() -> {
			try {
				object.callWait1();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		Thread t2 = new Thread(() -> {
			try {
				object.callWait2();
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
