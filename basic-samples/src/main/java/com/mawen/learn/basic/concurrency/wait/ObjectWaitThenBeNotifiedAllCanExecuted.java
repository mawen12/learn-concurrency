package com.mawen.learn.basic.concurrency.wait;

/**
 * When t1 call {@link Object#wait()}, it will be added to wait set, then t2, t3 executed, t3 will not be added to
 * wait set contain two element: t1, t3,
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/22
 */
public class ObjectWaitThenBeNotifiedAllCanExecuted {

	public synchronized void callWait() throws InterruptedException {
		System.out.println("Execute t1 callWait....");
		this.wait();
		System.out.println("Execute t1 callWait end....");
	}

	public synchronized void doExecute() {
		System.out.println("Execute t3 doExecute....");
	}

	public synchronized void callNotifyAll() throws InterruptedException {
		System.out.println("Execute t2 callNotify....");
		Thread.sleep(100L); // wait t3
		this.notifyAll();
		System.out.println("Execute t2 callNotify end....");
	}

	public static void main(String[] args) throws InterruptedException {

		ObjectWaitThenBeNotifiedAllCanExecuted object = new ObjectWaitThenBeNotifiedAllCanExecuted();

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
				object.callNotifyAll();
			}
			catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});

		Thread t3 = new Thread(object::doExecute);

		t1.start();
		Thread.sleep(10L);
		t2.start();
		t3.start();

		Thread.sleep(100000L);
	}
}
