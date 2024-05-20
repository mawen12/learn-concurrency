package com.mawen.learn.basic.concurrency;

/**
 * The {@link Runnable} interface defines a single method, run, meant to contain the code executed in the thread.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/runthread.html">runthread</a>
 * @since 2024/5/20
 */
public class HelloRunnable implements Runnable {

	@Override
	public void run() {
		System.out.println("Hello from a thread");
	}

	public static void main(String[] args) {
		new Thread(new HelloRunnable()).start();
	}
}
