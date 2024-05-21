package com.mawen.learn.basic.concurrency;

/**
 * {@link SampleThreads} consists of two threads, The first is the main thread that every Java Application has.
 * The main thread creates a new thread from the {@link Runnable} object, {@link MessageLoop}, and waits for it to finish.
 * If the {@link MessageLoop} thread takes too long to finish, the main thread interrupts it.
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/simple.html">simple</a>
 * @since 2024/5/21
 */
public class SampleThreads {

	/**
	 * Display a message, preceded by the name of the current thread
	 */
	static void threadMessage(String message) {
		String threadName = Thread.currentThread().getName();
		System.out.format("%s: %s%n", threadName, message);
	}

	private static class MessageLoop implements Runnable {
		@Override
		public void run() {
			String[] importantInfo = {"Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"};

			try {
				for (int i = 0; i < importantInfo.length; i++) {
					// Pause for 4 seconds
					Thread.sleep(4000);
					// Print a message
					threadMessage(importantInfo[i]);
				}
			}
			catch (InterruptedException e) {
				threadMessage("I wasn't done!");
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {

		// Delay, in milliseconds before we interrupt MessageLoop thread (default one hour).
		long patience = 1000 * 60 * 60;

		// If command line argument present, gives patience in seconds.
		if (args.length > 0) {
			try {
				patience = Long.parseLong(args[0]) * 1000;
			}
			catch (NumberFormatException e) {
				System.err.println("Argument must be an integer.");
				System.exit(1);
			}
		}

		threadMessage("Starting MessageLoop thread");
		long startTime = System.currentTimeMillis();
		Thread t = new Thread(new MessageLoop());
		t.start();

		threadMessage("Waiting for MessageLoop thread to finish");
		// loop until MessageLoop thread exists
		while (t.isAlive()) {
			threadMessage("Still waiting");
			// Wait maximum of 1 second for MessageLoop thread
			t.join(1000);
			if ((System.currentTimeMillis() - startTime) > patience) {
				threadMessage("Tired of waiting!");
				t.interrupt();
				// Shouldn't be long now, wait indefinitely
				t.join();
			}
		}
		threadMessage("Finally!");
	}
}
