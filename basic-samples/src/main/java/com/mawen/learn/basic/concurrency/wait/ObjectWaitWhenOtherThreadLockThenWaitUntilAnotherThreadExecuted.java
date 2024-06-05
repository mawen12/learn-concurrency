package com.mawen.learn.basic.concurrency.wait;

import java.time.LocalDateTime;

/**
 * When thread t1 call {@link Object#wait(long)}, thread t2 can lock the monitor, when t1 wait end, it will into wait set.
 * when thread t2 executed and unlock the monitor, t1 can lock the monitor then execute.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/22
 */
public class ObjectWaitWhenOtherThreadLockThenWaitUntilAnotherThreadExecuted {

	public synchronized void callWait1() throws InterruptedException {
		System.out.println("Execute wait1...." + LocalDateTime.now());
		this.wait(1000L);
		System.out.println("Execute wait1 end...." + LocalDateTime.now());
	}

	public synchronized void callWait2() throws InterruptedException {
		System.out.println("Execute wait2....");
		Thread.sleep(2000L);
		System.out.println("Execute wait2 end....");
	}

	public static void main(String[] args) throws InterruptedException {

		ObjectWaitWhenOtherThreadLockThenWaitUntilAnotherThreadExecuted object = new ObjectWaitWhenOtherThreadLockThenWaitUntilAnotherThreadExecuted();

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
