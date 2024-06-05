package com.mawen.learn.basic.concurrency.wait;

/**
 * When t1 call {@link Object#wait()}, it will be added to wait set, and wait set only contain one element is t1,
 * when t2 call {@link Object#notify()}, t1 will be selected for removal from the wait set.
 * when t2 unlock the monitor, t1 will lock the monitor, and continue to execute the code from the wait position.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/specs/jls/se8/html/jls-17.html#jls-17.2">Wait Sets and Notification</a>
 * @since 2024/5/22
 */
public class ObjectWaitThenBeNotifiedCanExecuted {

	public synchronized void callWait() throws InterruptedException {
		System.out.println("Execute callWait....");
		this.wait();
		System.out.println("Execute callWait end....");
	}

	public synchronized void callNotify() {
		System.out.println("Execute callNotify....");
		this.notify();
		System.out.println("Execute callNotify end....");
	}

	public static void main(String[] args) throws InterruptedException {

		ObjectWaitThenBeNotifiedCanExecuted object = new ObjectWaitThenBeNotifiedCanExecuted();

		Thread t1 = new Thread(() -> {
			try {
				object.callWait();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		Thread t2 = new Thread(object::callNotify);

		t1.start();
		Thread.sleep(10L);
		t2.start();

		Thread.sleep(100000L);
	}
}
