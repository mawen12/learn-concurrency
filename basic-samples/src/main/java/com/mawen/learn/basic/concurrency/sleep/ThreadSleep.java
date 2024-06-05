package com.mawen.learn.basic.concurrency.sleep;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/22
 */
public class ThreadSleep {

	private boolean done = false;

	private String name;

	public void doSomething() throws InterruptedException {
		while (!this.done) {
			Thread.sleep(1000L);
		}
		name = "abc";
		System.out.println("t1 read done it to true");
	}

	public void isDone() {
		this.done = true;
		System.out.println("t2 set done to true");
	}

	public static void main(String[] args) throws InterruptedException {
		ThreadSleep obj = new ThreadSleep();

		Thread t1 = new Thread(() -> {
			try {
				obj.doSomething();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		Thread t2 = new Thread(obj::isDone);

		t1.start();
		Thread.sleep(10L);
		t2.start();

		t1.join();

		System.out.println(obj.name);
	}
}
