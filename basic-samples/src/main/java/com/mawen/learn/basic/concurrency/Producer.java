package com.mawen.learn.basic.concurrency;

import java.util.Random;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/21
 */
public class Producer implements Runnable{

	private Drop drop;

	public Producer(Drop drop) {
		this.drop = drop;
	}

	@Override
	public void run() {
		String[] importantInfo = {"Mares eat oats", "Does eat oats", "Little lambs eat ivy", "A kid will eat ivy too"};

		Random random = new Random();

		for (int i = 0; i < importantInfo.length; i++) {
			drop.put(importantInfo[i]);

			try {
				Thread.sleep(random.nextInt(5000));
			}
			catch (InterruptedException e) {}
		}

		drop.put("DONE");
	}
}
