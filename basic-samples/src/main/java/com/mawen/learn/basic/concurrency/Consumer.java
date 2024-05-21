package com.mawen.learn.basic.concurrency;

import java.util.Random;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/21
 */
public class Consumer implements Runnable{

	private Drop drop;

	public Consumer(Drop drop) {
		this.drop = drop;
	}

	@Override
	public void run() {
		Random random = new Random();
		for (String message = drop.take(); !message.equals("DONE"); message = drop.take()) {
			System.out.format("MESSAGE RECEIVED: %s%n", message);

			try {
				Thread.sleep(random.nextInt(5000));
			}
			catch (InterruptedException e) {}
		}
	}
}
