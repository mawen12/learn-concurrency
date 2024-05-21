package com.mawen.learn.basic.concurrency;

/**
 * Then call {@link Object#wait()} or {@link Object#notifyAll()} or {@link Object#notify()} method,
 * the thread must own the intrinsic lock, otherwise an {@link IllegalMonitorStateException} is thrown.
 * Invoking {@link Object#wait()} inside a synchronized method is a simple way to acquire the intrinsic lock.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html">guardmeth</a>
 * @since 2024/5/21
 */
public class ObjectWait {

	public void forLoop() {
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {

		}
		System.out.println("DONE");
	}

	public void waitForObject() throws InterruptedException {
		this.wait();
	}

	public void notifyAllObject() {
		this.notifyAll();
	}

	public synchronized void safeNotifyALl() {
		System.out.println("Execute safeNotifyALl...");
		this.notifyAll();
		System.out.println("Execute safeNotifyALl done...");
	}

	public static void main(String[] args) {
		ObjectWait objectWait = new ObjectWait();

		new Thread(new Runnable() {
			@Override
			public void run() {
				objectWait.forLoop();
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
//				try {
//					objectWait.waitForObject();
//				}
//				catch (InterruptedException e) {
//					throw new RuntimeException(e);
//				}
//				objectWait.notifyAllObject();

				objectWait.safeNotifyALl();
			}
		}).start();
	}
}
