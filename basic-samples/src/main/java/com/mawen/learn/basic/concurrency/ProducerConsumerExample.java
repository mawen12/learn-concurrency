package com.mawen.learn.basic.concurrency;

/**
 * Use Guarded blocks to create a Producer-Consumer application
 *
 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/concurrency/guardmeth.html">guardmeth</a>
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2024/5/21
 */
public class ProducerConsumerExample {

	public static void main(String[] args) {
		Drop drop = new Drop();

		new Thread(new Producer(drop)).start();
		new Thread(new Consumer(drop)).start();
	}
}
