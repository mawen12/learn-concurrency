package com.mawen.learn.basic.concurrency;

/**
 * The thread itself implements {@link Runnable}, though its run method does nothing.
 * An application can subclass {@link Thread}, providing its own implementations of run.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html">runthread</a>
 * @since 2024/5/20
 */
public class HelloThread extends Thread {

	@Override
	public void run() {
		System.out.println("Hello from a thread!");
	}

	public static void main(String[] args) {
		new HelloThread().start();
	}
}
